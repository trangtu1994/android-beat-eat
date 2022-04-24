package com.example.beatoreat.stagingframework.datasource

import com.example.beatoreat.network.RetroBuilder
import com.example.beatoreat.service.IMovieServices
import com.example.core.data.datasources.MovieDataSource
import com.example.core.data.models.Movie
import com.example.core.data.models.Pagination

class RemoteMovieDatasource: MovieDataSource {

    override suspend fun getPopularMovie(page: Int): Pagination<Movie> =
        RetroBuilder.makeRetro().create(IMovieServices::class.java).popularMovie(lang = "en", page = page)

}