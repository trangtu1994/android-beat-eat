package com.example.core.domain.usecases.localcredential

import com.example.core.data.managers.LocalCredentialManager

class SignInUsecase(val manager: LocalCredentialManager) {

    suspend operator fun invoke(userName: String, password: String) =
        manager.getCredential(userName, password)
}