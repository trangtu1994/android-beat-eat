package com.example.core

interface UserDataSoure {

    fun loadAllUsers() : List<User>

    fun loginUser(name: String, pass: String) : User?
}