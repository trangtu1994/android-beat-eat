package com.example.core.domain.usecases.movies

import com.example.core.data.managers.MovieManager
import com.example.core.data.models.movies.MutableMovie
import com.example.core.domain.filters.MovieConfig

class MovieLoadCachedUsecase(private val manager: MovieManager) {

    suspend operator fun invoke(pageConfig: MovieConfig) : List<MutableMovie> =
        manager.getLocalPopularMovies(pageConfig)


}