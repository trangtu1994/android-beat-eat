package com.example.core.data.managers

import com.example.core.data.datasources.MovieDataSource
import com.example.core.data.datasources.SavedMovieDataSource
import com.example.core.data.models.ILocalMovie
import com.example.core.data.models.Movie
import com.example.core.data.models.movies.MutableMovie
import com.example.core.utils.AppLogConstants
import com.example.core.utils.ALogger
import java.lang.Exception
import java.net.UnknownHostException
import java.util.*

class MovieManager( // MovieRepository
    private val movieDataSource: MovieDataSource,
    private val localDataSource: SavedMovieDataSource? = null
) {

    private var mutableMovies = mutableListOf<MutableMovie>()
    private var remoteMovies = mutableListOf<Movie>()

    private fun retainItems(items: List<MutableMovie>, isClear: Boolean = false) {
        if (isClear) {
            mutableMovies.clear()
        }
        mutableMovies.addAll(items)
    }

    suspend fun getPopularMovie(page: Int): List<MutableMovie> {
        val networkResult: MutableList<MutableMovie> = mutableListOf()
        try {
            val listMovies = movieDataSource.getPopularMovie(page).results
            val listMutableMovies = sortMovies(listMovies.map { getMutableMovie(it) }, MovieSorter.Name)
            networkResult.addAll(listMutableMovies)
        } catch (e: UnknownHostException) {
            // do not reach host or connection is disable
            ALogger.eLog("${AppLogConstants.S_MOVIE} ==> ${e.localizedMessage}")
            val cachedBefore = loadCacheMovies()
            cachedBefore?.let {
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

    suspend fun getLocalPopularMovie() : List<MutableMovie>? {
        val values = loadCacheMovies()
        values?.let { retainItems(values) }
        return values
    }

    private suspend fun loadCacheMovies() : List<MutableMovie>? {
        var result = localDataSource?.listAll()?.map { makeMutableMovie(it) }
        if (result != null && result.isNotEmpty()) {
            result = sortMovies(result, MovieSorter.Name)
        }
        return result
    }

    private fun sortMovies(movies: List<MutableMovie>, sorter: MovieSorter): List<MutableMovie> {
        return when (sorter) {
            MovieSorter.Release -> {mutableMovies.sortedBy { it.release_date }}
            MovieSorter.Favorite -> {mutableMovies.sortedWith( compareBy({!it.isFavorite} , {it.title}))}
            else -> {mutableMovies.sortedBy { it.title }}
        }
    }

    fun sortMovies(sorter: MovieSorter = MovieSorter.Name) : List<MutableMovie>
        = sortMovies(mutableMovies, sorter)


    fun favoriteFilter(isOnlyFavorite: Boolean) =
        mutableMovies.filter { if (isOnlyFavorite) it.isFavorite else false }

    private suspend fun cacheMovies(movies: List<MutableMovie>) {
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

    suspend fun saveMovies(movies: List<MutableMovie>) {
        cacheMovies(movies)
    }

    private fun makeMutableMovie(movie: Movie): MutableMovie =
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

    private fun makeMutableMovie(localMovie: ILocalMovie): MutableMovie =
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

    private suspend fun getMutableMovie(movie: Movie) : MutableMovie {
        if (localDataSource == null) {
            return makeMutableMovie(movie)
        }
        return localDataSource.getById(movie.id)?.let { localMovie ->
            makeMutableMovie(movie).apply {
                isFavorite = localMovie.isFavorite
            }
        } ?: makeMutableMovie(movie)
    }

    enum class MovieSorter {
        Name, Release, Favorite
    }
}


//https://api.themoviedb.org/3/configuration?api_key=330fcf36d91365e7c09a699327063417
