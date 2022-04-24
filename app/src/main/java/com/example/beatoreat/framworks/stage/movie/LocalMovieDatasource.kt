package com.example.beatoreat.framworks.stage.movie

import com.example.beatoreat.database.dao.MovieDao
import com.example.beatoreat.database.entities.MovieDto
import com.example.core.data.datasources.SavedMovieDataSource
import com.example.core.data.models.ILocalMovie
import com.example.core.data.models.Movie
import com.example.core.data.models.movies.MutableMovie

class LocalMovieDatasource(val dao: MovieDao): SavedMovieDataSource {

    override fun getLocalMovie(movie: MutableMovie): MovieDto {
        TODO("Not yet implemented")
    }

    override suspend fun listAll(): List<MovieDto> =
        dao.listAll()

    override suspend fun insert(movie: ILocalMovie) {
        if (movie is MovieDto) {
            dao.insert(movie)
        }
    }

    override suspend fun update(movie: ILocalMovie) {
        if (movie is MovieDto) {
            dao.update(movie)
        }
    }

    override suspend fun getById(id: Int): MovieDto? {
        val result = dao.listById(id)
        if (result.isNotEmpty()) {
            return result.first()
        }
        return null
    }

    override fun convertMovie(movie: MutableMovie): MovieDto =
        movie.let { MovieDto(it.id, it.title, it.poster_path, it.overview, it.release_date.time, it.vote_count, false) }

}