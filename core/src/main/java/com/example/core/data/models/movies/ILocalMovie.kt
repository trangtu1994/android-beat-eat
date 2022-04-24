package com.example.core.data.models

import java.util.*


//Localy handle, database return or app input
interface ILocalMovie {

    val id: Int
    var title: String
    var posterPath: String
    var overview: String
    var releaseDate: Long
    var voteCount: Long
    var isFavorite: Boolean

}