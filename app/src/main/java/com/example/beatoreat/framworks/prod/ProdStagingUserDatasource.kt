package com.example.beatoreat.prodframework.datasource

import com.example.core.User
import com.example.core.UserDataSoure
import com.example.core.data.models.UserType

class ProdStagingUserDatasource: UserDataSoure {

    override fun loadAllUsers(): List<User> = mutableListOf()

    override fun loginUser(name: String, pass: String): User {
        val isValid = name.isNotEmpty() && pass.isNotEmpty()
        val userName = if (isValid) name else "Null"
        val type = if (isValid) UserType.Undefined else UserType.Unauthorized
        return User(name = userName, type = type)
    }


}