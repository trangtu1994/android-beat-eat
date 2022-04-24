package com.example.core.domain.interactors

import com.example.core.UserManager
import com.example.core.data.managers.LocalCredentialManager
import com.example.core.domain.usecases.ListUserUsecase
import com.example.core.domain.usecases.LoginUsecase
import com.example.core.domain.usecases.localcredential.AllLocalUserUsecase
import com.example.core.domain.usecases.localcredential.SaveListUsersUsecase
import com.example.core.domain.usecases.localcredential.SaveUsecase
import com.example.core.domain.usecases.localcredential.SignInUsecase

class UserInteractors (userRepository: UserManager,
                        localUserManager: LocalCredentialManager? = null) {

    var loginUseCase: LoginUsecase = LoginUsecase(userRepository)
    val allUsers: ListUserUsecase = ListUserUsecase(userRepository)

    val localLoginUsecase: SignInUsecase? = localUserManager?.let { SignInUsecase(it) }
    val localSaveUsecase: SaveUsecase? = localUserManager?.let { SaveUsecase(it) }
    val localAllUsersUsecase: AllLocalUserUsecase? = localUserManager?.let { AllLocalUserUsecase(it) }
    val saveUsersUsecase: SaveListUsersUsecase? = localUserManager?.let { SaveListUsersUsecase(it) }

}