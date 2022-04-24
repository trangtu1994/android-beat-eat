package com.example.beatoreat.service

import android.accounts.Account
import retrofit2.http.GET


interface IAccountService {

    @GET("285c2a4f-9d9b-49f6-ad9d-716e058c9975")
    fun users() : List<Account>

}