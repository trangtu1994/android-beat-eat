package com.example.beatoreat.network

class NetworkConstant {

    companion object {
        const val HOST = ""
        const val POSTER_HOST = "https://image.tmdb.org/t/p/"
        const val POSTER_SIZE = "w342"

        fun getPosterBase() : String =
            "$POSTER_HOST$POSTER_SIZE"
    }
}