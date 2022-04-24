package com.example.core.domain.usecases.movies

import com.example.core.data.managers.MovieManager
import com.example.core.data.models.movies.MutableMovie

class MovieSortingUsecase(val manager: MovieManager) {

    suspend operator fun invoke(movies: List<MutableMovie>, sorter: MovieManager.MovieSorter = MovieManager.MovieSorter.Name)
    : List<MutableMovie> = mutableListOf()
//        manager.sortMovies(movies, sorter)
    //TODO: move movies into Manager


}