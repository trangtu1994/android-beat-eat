package com.example.core.data.managers

import com.example.core.data.datasources.MovieDataSource
import com.example.core.data.datasources.movie.LocalMovieDataSource
import com.example.core.data.models.ILocalMovie
import com.example.core.data.models.Movie
import com.example.core.data.models.movies.MutableMovie
import com.example.core.domain.filters.MovieConfig
import com.example.core.domain.sorters.MovieSorter
import com.example.core.utils.AppLogConstants
import com.example.core.utils.ALogger
import java.lang.Exception
import java.net.UnknownHostException
import java.util.*

class MovieManager( // MovieRepository
    private val movieDataSource: MovieDataSource,
    private val localDataSource: LocalMovieDataSource? = null
) {

    private var mutableMovies = mutableListOf<MutableMovie>()

    suspend fun getPopularMovie(config: MovieConfig): List<MutableMovie> {
        val networkResult: MutableList<MutableMovie> = mutableListOf()
        try {
            val listMovies = movieDataSource.getPopularMovie(config.page).results
            val listMutableMovies = listMovies.map { getMutableMovie(it) }
            retainMutableMovies(listMutableMovies, config.page == 1)
            val filtered = applyConfigToList(config)
            networkResult.addAll(filtered)
        } catch (e: UnknownHostException) {
            // do not reach host or connection is disable

                ALogger.eLog("${AppLogConstants.S_MOVIE} ==> ${e.localizedMessage}")
            val cachedBefore = getLocalPopularMovies(config)
            cachedBefore.let {
                networkResult.addAll(it)
            }
            if (localDataSource == null) {
                throw e
            }
        } catch (e: Exception) {
            ALogger.eLog("${AppLogConstants.S_MOVIE} ==> ${e.localizedMessage}")
            if (networkResult.isEmpty()) { // Rooms Exception
                throw e
            }
        }
        return networkResult
    }

    suspend fun getLocalPopularMovies(listConfig: MovieConfig) : List<MutableMovie> {
        val values = loadCacheMovies()
        values?.let { retainMutableMovies(values, listConfig.page == 1) }
        return applyConfigToList(listConfig)
    }

    fun applyConfigToList(listConfig: MovieConfig) : List<MutableMovie> {
        val currentItems = mutableMovies
        val filtersList = favoriteFilter(currentItems, listConfig.isFavorite)
        return sortMovies(filtersList, listConfig.sorter)
    }

    private fun retainMutableMovies(items: List<MutableMovie>, isClear: Boolean = false) {
        if (isClear) {
            mutableMovies.clear()
        }
        mutableMovies.addAll(items)
    }

    private suspend fun loadCacheMovies() : List<MutableMovie>? {
        var result = localDataSource?.listAll()?.map { localToMutableMovie(it) }
        if (result != null && result.isNotEmpty()) {
            result = sortMovies(result, MovieSorter.Name)
        }
        return result
    }

    private fun sortMovies(movies: List<MutableMovie>, sorter: MovieSorter): List<MutableMovie> {
        return when (sorter) {
            MovieSorter.ReleaseDate -> {movies.sortedWith( compareByDescending<MutableMovie> { it.release_date }.thenBy { it.title  } )}
            MovieSorter.Favorite -> {movies.sortedWith( compareBy({!it.isFavorite} , {it.title}))}
            else -> {movies.sortedBy { it.title }}
        }
    }

    private fun favoriteFilter(
        mutableMovies: List<MutableMovie>,
        isOnlyFavorite: Boolean
    ): List<MutableMovie> =
         mutableMovies.filter { if (isOnlyFavorite) it.isFavorite else true }

    suspend fun saveMovies(movies: List<MutableMovie>) {
        if (localDataSource == null) {
            return
        }
        movies.forEach { it ->
            val dbInstance = localDataSource.getById(it.id)
            if (dbInstance != null) {
                dbInstance.apply {
                    title = it.title
                    overview = it.overview
                    posterPath = it.poster_path
                    releaseDate = it.release_date.time
                    isFavorite = it.isFavorite
                }.also {
                    localDataSource.update(it)
                }
            } else {
                localDataSource.convertMovie(it).let {local ->
                    localDataSource.insert(local)
                }
            }
        }
    }

    private fun remoteToMutableMovie(movie: Movie): MutableMovie =
        movie.run {
            MutableMovie(
                id,
                title,
                poster_path,
                overview,
                release_date,
                vote_count,
                false
            )
        }

    private fun localToMutableMovie(localMovie: ILocalMovie): MutableMovie =
        localMovie.run {
            MutableMovie(
                id,
                title,
                posterPath,
                overview,
                Date(releaseDate),
                voteCount,
                isFavorite
            )
        }

   private suspend fun getMutableMovie(remoteMovie: Movie) : MutableMovie {
        val mutableMovie = remoteToMutableMovie(remoteMovie)
        if (localDataSource == null) {
            return mutableMovie
        }
        return localDataSource.getById(remoteMovie.id)?.let { localMovie ->
            mutableMovie.apply {
                isFavorite = localMovie.isFavorite
            }
        } ?: mutableMovie
    }

}


//https://api.themoviedb.org/3/configuration?api_key=330fcf36d91365e7c09a699327063417
