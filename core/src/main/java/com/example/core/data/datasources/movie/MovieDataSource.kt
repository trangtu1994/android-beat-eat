package com.example.core.data.datasources

import com.example.core.data.models.Movie
import com.example.core.data.models.Pagination

interface MovieDataSource {

    suspend fun getPopularMovie(page: Int): Pagination<Movie>

    fun clear() {
        loadedMovies.clear()
    }

    var loadedMovies: MutableList<Movie>

    fun retainMovies(movies: List<Movie>, clear: Boolean) {
        if (clear) {
            clear()
        }
        this.loadedMovies.addAll(movies)
    }
}