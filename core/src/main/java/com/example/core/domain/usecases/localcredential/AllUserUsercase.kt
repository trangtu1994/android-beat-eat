package com.example.core.domain.usecases.localcredential

import com.example.core.data.datasources.SignInCredentialDatasource
import com.example.core.data.managers.LocalCredentialManager

class AllLocalUserUsecase(val manager: LocalCredentialManager) {

    suspend operator fun invoke() : List<SignInCredentialDatasource.ICredential> {
        return manager.getAllSavedUsers()
    }

}