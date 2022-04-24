package com.example.core.domain.usecases

import com.example.core.UserManager

class LoginUsecase(val userRepository: UserManager) {

    operator fun invoke(userName: String, password: String) =
        userRepository.login(userName, password)
}