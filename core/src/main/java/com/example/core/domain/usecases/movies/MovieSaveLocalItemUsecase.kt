package com.example.core.domain.usecases.localcredential

import com.example.core.data.managers.MovieManager
import com.example.core.data.models.movies.MutableMovie

class MovieSaveLocalItemUsecase(val manager: MovieManager) {

    suspend operator fun invoke(movies: List<MutableMovie>) =
        manager.saveMovies(movies)
}