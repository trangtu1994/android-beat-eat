package com.example.core.domain.usecases

import com.example.core.data.managers.MovieManager
import com.example.core.data.models.Movie
import com.example.core.data.models.movies.MutableMovie

class MovieLoadPopularUsecase(val movieManager: MovieManager) {

    suspend operator fun invoke(page: Int = 1): List<MutableMovie> =
        movieManager.getPopularMovie(page)
}