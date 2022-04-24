package com.example.core.domain.usecases.localcredential

import com.example.core.data.datasources.SignInCredentialDatasource
import com.example.core.data.managers.LocalCredentialManager

class SaveListUsersUsecase(val manager: LocalCredentialManager) {

    suspend operator fun invoke(users: List<SignInCredentialDatasource.ICredential>) {
        manager.saveAllCredential(users)
    }

}