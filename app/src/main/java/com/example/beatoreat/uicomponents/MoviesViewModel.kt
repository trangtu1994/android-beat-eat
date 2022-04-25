package com.example.beatoreat.uicomponents

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.beatoreat.database.AppDatabaseManager
import com.example.beatoreat.framworks.stage.movie.LocalMovieDatasource
import com.example.beatoreat.network.NetworkResult
import com.example.beatoreat.framworks.stage.movie.RemoteMovieDatasource
import com.example.core.data.models.movies.MutableMovie
import com.example.core.domain.filters.MovieConfig
import com.example.core.domain.interactors.MovieInteractor
import com.example.core.domain.sorters.MovieSorter
import com.example.core.utils.ALogger
import com.example.core.utils.AppLogConstants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MoviesViewModel: ViewModel() {

    private val movieInteractor: MovieInteractor

    val moviesResult: MutableLiveData<NetworkResult<List<MutableMovie>>> = MutableLiveData()
    val networkLoading: MutableLiveData<Boolean> = MutableLiveData(false)

    private val pageConfig: MovieConfig = MovieConfig(1, false, MovieSorter.ReleaseDate)

    init {
        val localMovieDatasource = LocalMovieDatasource(AppDatabaseManager.instace!!.movieDao()!!)
        movieInteractor = MovieInteractor(RemoteMovieDatasource(), localMovieDatasource)
        initLocalMovies()
        popularMovie()
    }

    private fun initLocalMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            val networkResult: NetworkResult<List<MutableMovie>> = try {
                val results = movieInteractor.loadCachedUsecase(pageConfig)
                NetworkResult.ResponseResult(results, NetworkResult.ResutlState.Add)
            } catch (e: Exception) {
                ALogger.eLog("${AppLogConstants.S_MOVIE} ==> ${e.localizedMessage}")
                NetworkResult.ErrorResult(e.localizedMessage)
            }
            withContext(Dispatchers.Main) {
                networkLoading.value = true
                moviesResult.value = networkResult
            }
        }
    }

    fun popularMovie() {
        networkLoading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            val networkResult: NetworkResult<List<MutableMovie>> = try {
                val results = movieInteractor.loadRemotePopularUsecae(pageConfig)
                NetworkResult.ResponseResult(results, NetworkResult.ResutlState.Add)
            } catch (e: Exception) {
                ALogger.eLog("${AppLogConstants.S_MOVIE} ==> ${e.localizedMessage}")
                NetworkResult.ErrorResult(e.localizedMessage)
            }
            withContext(Dispatchers.Main) {
                networkLoading.value = false
                moviesResult.value = networkResult
            }
            if (networkResult is NetworkResult.ResponseResult) {
                localCacheMovies(networkResult.value)
            }
        }
    }

    fun localCacheMovies(movies: List<MutableMovie>) {
        if (movies.isEmpty()) { return }
        viewModelScope.launch(Dispatchers.IO) {
            try {
                movieInteractor.saveLocalUseCase(movies)
            } catch (e: Exception) {
                ALogger.eLog("${AppLogConstants.S_MOVIE} ==> ${e.localizedMessage}")
            }
            ALogger.dLog("${AppLogConstants.S_MOVIE} ==> Cached all movies")
        }
    }

    fun saveMovies() { // save movies to Database
        if (moviesResult.value is NetworkResult<List<MutableMovie>>) {
            val allValue = (moviesResult.value as NetworkResult.ResponseResult<List<MutableMovie>>).value
            localCacheMovies(allValue)
        }
    }

    fun toggleSorter(sorter: MovieSorter = MovieSorter.Name) {
        if (moviesResult.value is NetworkResult.ResponseResult<List<MutableMovie>>) {
            pageConfig.sorter = sorter
            viewModelScope.launch {
                val sortValues = movieInteractor.sortUsecase(pageConfig)
                moviesResult.value =  NetworkResult.ResponseResult(sortValues, NetworkResult.ResutlState.Add)
            }
        }
    }

    fun toggleFavorite() {
        if (moviesResult.value is NetworkResult.ResponseResult<List<MutableMovie>>) {
            pageConfig.isFavorite = !pageConfig.isFavorite
            val filterValues = movieInteractor.favoriteFilterUsecase(pageConfig)
            moviesResult.value = NetworkResult.ResponseResult(filterValues, NetworkResult.ResutlState.Add)
        }
    }

    fun log(message: String) {
        Log.i("NETWORK", message)
    }
}