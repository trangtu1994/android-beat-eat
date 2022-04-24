package com.example.beatoreat.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserDto (

    @PrimaryKey (autoGenerate = true)
    var id: Int,
    @ColumnInfo var name: String,
    @ColumnInfo var password: String)