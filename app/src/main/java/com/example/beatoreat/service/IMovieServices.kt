package com.example.beatoreat.service

import com.example.core.data.models.Movie
import com.example.core.data.models.Pagination
import com.example.beatoreat.network.RetroBuilder
import retrofit2.http.GET
import retrofit2.http.Query

interface IMovieServices {

    @GET("movie/popular")
    suspend fun popularMovie(@Query("api_key") key: String = RetroBuilder.PRIVATE_KEY,
                             @Query("language") lang: String,
                             @Query("page") page: Int): Pagination<Movie>
}