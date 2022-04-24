package com.example.core.data.models.movies

import java.util.*


// using in view and ViewModel
// return by manager
class MutableMovie (
    val id: Int,
    val title: String,
    val poster_path: String,
    val overview: String,
    val release_date: Date,
    val vote_count: Long,
    var isFavorite: Boolean = false
)