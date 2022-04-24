package com.example.core.domain.usecases.movies

import com.example.core.data.managers.MovieManager
import com.example.core.data.models.movies.MutableMovie

class MovieFilterFavoriteUsecase(val manager: MovieManager) {

    operator fun invoke(movies:List<MutableMovie>, onlyFavorite: Boolean) : List<MutableMovie> =
        manager.favoriteFilter(movies, onlyFavorite)
}