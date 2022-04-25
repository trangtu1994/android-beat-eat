package com.example.core.data.models.movies

import java.util.*


// using in view and ViewModel
// return by manager
class MutableMovie (
    val id: Int,
    var title: String,
    var poster_path: String,
    var overview: String,
    var release_date: Date,
    var vote_count: Long,
    var isFavorite: Boolean = false
)