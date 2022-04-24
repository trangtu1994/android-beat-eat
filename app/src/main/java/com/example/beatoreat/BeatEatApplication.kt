package com.example.beatoreat

import android.app.Application
import com.example.beatoreat.database.AppDatabaseManager

class BeatEatApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        initDependancies()
    }

    fun initDependancies() {
        val db = AppDatabaseManager.build(this)
    }
}