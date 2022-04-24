package com.example.core

import com.example.core.data.models.UserType

class UserManager (private val soure: UserDataSoure?) {

    //UserRepository

    fun allUsers() : List<User> =
        soure?.loadAllUsers() ?: mutableListOf()

    fun login(username: String, pwd: String): User =
        soure?.loginUser(username, pwd) ?: User("", type = UserType.Undefined)

}