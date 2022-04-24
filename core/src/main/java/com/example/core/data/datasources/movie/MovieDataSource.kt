package com.example.core.data.datasources

import com.example.core.data.models.Movie
import com.example.core.data.models.Pagination

interface MovieDataSource {

    suspend fun getPopularMovie(page: Int): Pagination<Movie>

}