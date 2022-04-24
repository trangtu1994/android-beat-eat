package com.example.core.domain.usecases.localcredential

import com.example.core.data.datasources.SignInCredentialDatasource
import com.example.core.data.managers.LocalCredentialManager

class SaveUsecase(val manager: LocalCredentialManager) {

    suspend operator fun invoke(credential: SignInCredentialDatasource.ICredential) =
        manager.saveCredential(credential)
}