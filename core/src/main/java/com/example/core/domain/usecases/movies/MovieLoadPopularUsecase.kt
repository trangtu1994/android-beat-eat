package com.example.core.domain.usecases

import com.example.core.data.managers.MovieManager
import com.example.core.data.models.Movie
import com.example.core.data.models.movies.MutableMovie
import com.example.core.domain.filters.MovieConfig

class MovieLoadPopularUsecase(val movieManager: MovieManager) {

    suspend operator fun invoke(pageConfig: MovieConfig): List<MutableMovie> =
        movieManager.getPopularMovie(pageConfig)

}