package com.example.core.data.datasources.movie

import com.example.core.data.models.ILocalMovie
import com.example.core.data.models.movies.MutableMovie

interface LocalMovieDataSource {

    fun getLocalMovie(movie: MutableMovie) : ILocalMovie

    suspend fun listAll() : List<ILocalMovie>?

    suspend fun insert(movie: ILocalMovie)

    suspend fun getById(id: Int) : ILocalMovie?

    suspend fun update(movie: ILocalMovie) {}

    suspend fun insertAll(movies: List<ILocalMovie>) {
        movies.forEach {
            insert(it)
        }
    }

    suspend fun remove(movie: ILocalMovie) {}

    /// convert from Movie to ILocalMovie
    fun convertMovie(movie: MutableMovie): ILocalMovie
}