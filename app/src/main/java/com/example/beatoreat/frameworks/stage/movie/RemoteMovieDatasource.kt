package com.example.beatoreat.framworks.stage.movie

import com.example.beatoreat.network.RetroBuilder
import com.example.beatoreat.service.IMovieServices
import com.example.core.data.datasources.MovieDataSource
import com.example.core.data.models.Movie
import com.example.core.data.models.Pagination

class RemoteMovieDatasource: MovieDataSource {

    override suspend fun getPopularMovie(page: Int): Pagination<Movie> {
        val pageResult = RetroBuilder.makeRetro().create(IMovieServices::class.java)
            .popularMovie(lang = "en", page = page)
        val movieResult = pageResult.results
        retainMovies(movieResult, page == 1)
        return pageResult
    }

    override var loadedMovies: MutableList<Movie> = mutableListOf()

}