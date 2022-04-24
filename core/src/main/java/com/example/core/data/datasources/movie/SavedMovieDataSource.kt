package com.example.core.data.datasources

import com.example.core.data.models.ILocalMovie
import com.example.core.data.models.Movie
import com.example.core.data.models.movies.MutableMovie

interface SavedMovieDataSource {

    fun getLocalMovie(movie: MutableMovie) : ILocalMovie

    suspend fun listAll() : List<ILocalMovie>?

    suspend fun insert(movie: ILocalMovie)

    suspend fun getById(id: Int) : ILocalMovie?

    suspend fun remove(movie: ILocalMovie) {}

    suspend fun update(movie: ILocalMovie) {}

    suspend fun insertAll(movies: List<ILocalMovie>) {
        movies.forEach {
            insert(it)
        }
    }


    /// convert from Movie to ILocalMovie
    fun convertMovie(movie: MutableMovie): ILocalMovie
}