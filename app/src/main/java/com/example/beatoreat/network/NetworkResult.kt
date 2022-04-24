package com.example.beatoreat.network

sealed class NetworkResult<out T> {

    open class UnitedResult() : NetworkResult<Nothing>()

    open class ResponseResult<T>(val value: T, val state: ResutlState = ResutlState.Add): NetworkResult<T>() {
        override val description: String
            get() = "Content value $value"
    }

    open class ErrorResult(val message: String) : NetworkResult<Nothing>(){
        override val description: String
            get() = "This is error"
    }

    open val description: String
    get() = "description"

    enum class ResutlState {
        Add, Update
    }
}