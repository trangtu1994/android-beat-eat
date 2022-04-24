package com.example.core.data.datasources

import android.accounts.Account

interface AccountDataSource {

    suspend fun getAccount() : List<Account>

}