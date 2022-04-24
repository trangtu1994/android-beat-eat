package com.example.core.domain.usecases

import com.example.core.User

class ListUserUsecase(val userRepository: com.example.core.UserManager) {

    operator fun invoke() : List<User> =
        userRepository.allUsers()

}