package com.example.core.data.models

import java.util.*

data class Movie (
    val id: Int,
    val title: String,
    val backdrop_path: String,
    val poster_path: String,
    val overview: String,
    val video: Boolean,
    val original_language: String,
    val original_title: String,
    val release_date: Date,
    val vote_average: Float,
    val vote_count: Long)


//adult : false
//backdrop_path : "/fOy2Jurz9k6RnJnMUMRDAgBwru2.jpg"
//genre_ids
//id : 508947
//original_language : "en"
//original_title : "Turning Red"
//overview : "Thirteen-year-old Mei is experiencing the awkwardness of being a teenager with a twist – when she gets too excited, she transforms into a giant red panda."
//popularity : 5257.366
//poster_path : "/qsdjk9oAKSQMWs0Vt5Pyfh6O4GZ.jpg"
//release_date : "2022-03-10"
//title : "Turning Red"
//video : false
//vote_average : 7.5
//vote_count : 1765