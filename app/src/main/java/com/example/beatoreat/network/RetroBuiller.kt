package com.example.beatoreat.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class RetroBuilder {

    companion object {

        const val BASE_URL = "https://api.themoviedb.org/3/"
        const val PRIVATE_KEY = "330fcf36d91365e7c09a699327063417"

        fun makeRetro() : Retrofit {
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY

            val client = OkHttpClient.Builder()
                .addInterceptor(logging)
                .build()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
        }

        fun makeService() {
            val retrofit = makeRetro()
        }
    }

}