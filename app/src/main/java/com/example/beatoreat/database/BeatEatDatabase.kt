package com.example.beatoreat.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.beatoreat.database.dao.MovieDao
import com.example.beatoreat.database.dao.UserDao
import com.example.beatoreat.database.entities.MovieDto
import com.example.beatoreat.database.entities.UserDto

@Database(entities = [UserDto::class, MovieDto::class], version = 2)
public abstract class BeatEatDatabase: RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun movieDao(): MovieDao
}

// version note
// need to remove if need increase version (migration)
// 1: initial, add UserDto
// 2: add MovieDto