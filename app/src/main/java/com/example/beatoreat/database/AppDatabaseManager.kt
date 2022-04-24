package com.example.beatoreat.database

import android.content.Context
import androidx.room.Room
import com.example.beatoreat.database.dao.MovieDao
import com.example.beatoreat.database.dao.UserDao

class AppDatabaseManager {

    private var database : BeatEatDatabase

    constructor(context: Context) {
        database = Room.databaseBuilder(context,
                        BeatEatDatabase::class.java, "be_dtabase").build()
    }

    companion object {
        var instace : AppDatabaseManager? = null


        fun build(context: Context) {
            instace = AppDatabaseManager(context)
        }
    }

    fun userDao(): UserDao? =
        instace?.database?.userDao()


    fun movieDao(): MovieDao? =
        instace?.database?.movieDao()

}