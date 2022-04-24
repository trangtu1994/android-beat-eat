package com.example.core.domain.usecases.movies

import com.example.core.data.managers.MovieManager
import com.example.core.data.models.movies.MutableMovie

class MovieLoadCachedUsecase(private val manager: MovieManager) {

    suspend operator fun invoke() : List<MutableMovie> {
        return manager.getLocalPopularMovie() ?: mutableListOf()
    }

}