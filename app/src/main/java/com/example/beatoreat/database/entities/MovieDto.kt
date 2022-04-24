package com.example.beatoreat.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.core.data.models.ILocalMovie
import java.sql.Date
import java.util.*

@Entity(tableName = "movies")
class MovieDto(
    @PrimaryKey
    override val id: Int,
    @ColumnInfo override var title: String,
    @ColumnInfo override var posterPath: String,
    @ColumnInfo override var overview: String,
    @ColumnInfo override var releaseDate: Long,
    @ColumnInfo override var voteCount: Long,
    @ColumnInfo override var isFavorite: Boolean
) : ILocalMovie