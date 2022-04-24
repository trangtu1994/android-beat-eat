package com.example.beatoreat.model

enum class Environment {

    Staging, Prod;

    val title: String =
        this.toString()
}