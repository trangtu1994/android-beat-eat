package com.example.beatoreat.uicomponents

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.beatoreat.database.AppDatabaseManager
import com.example.beatoreat.framworks.stage.movie.LocalMovieDatasource
import com.example.beatoreat.network.NetworkResult
import com.example.beatoreat.stagingframework.datasource.RemoteMovieDatasource
import com.example.core.data.managers.MovieManager
import com.example.core.data.models.movies.MutableMovie
import com.example.core.domain.interactors.MovieInteractor
import com.example.core.utils.ALogger
import com.example.core.utils.AppLogConstants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MoviesViewModel: ViewModel() {

    var sortFavorite: Boolean = false
    var filterFavorite: Boolean = false

    private val movieInteractor: MovieInteractor

    val moviesResult: MutableLiveData<NetworkResult<List<MutableMovie>>> = MutableLiveData()
    val networkLoading: MutableLiveData<Boolean> = MutableLiveData(false)

    init {
        val localMovieDatasource = LocalMovieDatasource(AppDatabaseManager.instace!!.movieDao()!!)
        movieInteractor = MovieInteractor(RemoteMovieDatasource(), localMovieDatasource)
        initLocalMovies()
        popularMovie()
    }

    private fun initLocalMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            val networkResult: NetworkResult<List<MutableMovie>> = try {
                val results = movieInteractor.loadCachedUsecase()
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

    fun popularMovie(page: Int = 1) {
        networkLoading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            val networkResult: NetworkResult<List<MutableMovie>> = try {
                val results = movieInteractor.loadRemotePopularUsecae(page)
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

    fun saveMovies() {
        if (moviesResult.value is NetworkResult<List<MutableMovie>>) {
            val allValue = (moviesResult.value as NetworkResult.ResponseResult<List<MutableMovie>>).value
            localCacheMovies(allValue)
        }
    }


    fun toggleSorter() {
        if (moviesResult.value is NetworkResult.ResponseResult<List<MutableMovie>>) {
            sortFavorite = !sortFavorite
            val allValue = (moviesResult.value as NetworkResult.ResponseResult<List<MutableMovie>>).value
            val sorter = if (sortFavorite) MovieManager.MovieSorter.Favorite else MovieManager.MovieSorter.Name
            viewModelScope.launch {
                val sortValues = movieInteractor.sortUsecase.invoke(allValue, sorter)
                moviesResult.value =  NetworkResult.ResponseResult(sortValues, NetworkResult.ResutlState.Add)
            }
        }
    }

    fun toggleFavorite() {
        if (moviesResult.value is NetworkResult.ResponseResult<List<MutableMovie>>) {
            filterFavorite = !filterFavorite
            val allValue = (moviesResult.value as NetworkResult.ResponseResult<List<MutableMovie>>).value
            val result = movieInteractor.favoriteFilterUsecase(allValue, filterFavorite)
            moviesResult.value = NetworkResult.ResponseResult(result, NetworkResult.ResutlState.Add)
        }
    }

    fun log(message: String) {
        Log.i("NETWORK", message)
    }
}