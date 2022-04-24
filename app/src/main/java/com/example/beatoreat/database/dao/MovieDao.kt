package com.example.beatoreat.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.beatoreat.database.entities.MovieDto

@Dao
interface MovieDao {

    @Query("SELECT * FROM movies")
    fun listAll() : List<MovieDto>

    @Query("SELECT * FROM movies WHERE id=:dtoId")
    fun listById(dtoId: Int) : List<MovieDto>

    @Update
    fun update(dto: MovieDto)

    @Insert
    fun insert(dto: MovieDto)

}