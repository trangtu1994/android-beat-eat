package com.example.beatoreat.frameworks.stage

import com.example.core.User
import com.example.core.UserDataSoure
import com.example.core.data.models.UserType

class StagingUserDatasource: UserDataSoure {

    override fun loadAllUsers(): List<User> =
        mutableListOf()

    override fun loginUser(name: String, pass: String): User? {
        if (name.isEmpty() || pass.isEmpty() || pass != "pizza") {
            return User(type = UserType.Unauthorized)
        }

        return when (name) {
            "remote" -> { User("stage-$name", UserType.Remote)}
            "testing" -> { User("stage-$name", UserType.Testing)}
            "su" -> { User("stage-$name", UserType.Super)}
            else -> { User("stage-$name", UserType.Undefined) }
        }
    }

}