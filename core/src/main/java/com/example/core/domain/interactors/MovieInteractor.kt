package com.example.core.domain.interactors

import com.example.core.data.datasources.MovieDataSource
import com.example.core.data.datasources.movie.LocalMovieDataSource
import com.example.core.data.managers.MovieManager
import com.example.core.domain.usecases.MovieLoadPopularUsecase
import com.example.core.domain.usecases.localcredential.MovieSaveLocalItemUsecase
import com.example.core.domain.usecases.movies.MovieFilterFavoriteUsecase
import com.example.core.domain.usecases.movies.MovieLoadCachedUsecase
import com.example.core.domain.usecases.movies.MovieSortingUsecase

class MovieInteractor (remoteMovieDataSource: MovieDataSource,
                        localMovieDataSource: LocalMovieDataSource? = null) {

    private val movieManager: MovieManager = MovieManager(remoteMovieDataSource, localMovieDataSource)

    val loadRemotePopularUsecae: MovieLoadPopularUsecase = MovieLoadPopularUsecase(movieManager)
    val saveLocalUseCase : MovieSaveLocalItemUsecase = MovieSaveLocalItemUsecase(movieManager)
    val loadCachedUsecase : MovieLoadCachedUsecase = MovieLoadCachedUsecase(movieManager)
    val sortUsecase : MovieSortingUsecase = MovieSortingUsecase(movieManager)
    val favoriteFilterUsecase: MovieFilterFavoriteUsecase =  MovieFilterFavoriteUsecase(movieManager)

}