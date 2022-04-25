package com.example.beatoreat

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.beatoreat.database.AppDatabaseManager
import com.example.beatoreat.frameworks.stage.LocalUserDatasource
import com.example.beatoreat.model.Credential
import com.example.beatoreat.model.CredentialType
import com.example.beatoreat.frameworks.prod.ProdStagingUserDatasource
import com.example.beatoreat.frameworks.stage.StagingUserDatasource
import com.example.core.UserDataSoure
import com.example.core.UserManager
import com.example.core.data.managers.LocalCredentialManager
import com.example.core.data.models.UserType
import com.example.core.domain.interactors.UserInteractors
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(val environment: String): ViewModel() {

    val loginType: MutableLiveData<CredentialType> = MutableLiveData(CredentialType.Undefined)
    var userTitle : MutableLiveData<String> = MutableLiveData("")

    private lateinit var interactors: UserInteractors

    init {
        initInteractors()
    }

    private fun initInteractors() {
        val dbUserDatasource = LocalUserDatasource(AppDatabaseManager.instace!!.userDao())
        val localCredentialManager = LocalCredentialManager(dbUserDatasource)
        val repository = UserManager(makeDataSource(environment))
        interactors = UserInteractors(repository, localCredentialManager)
    }

    private fun makeDataSource(environment: String): UserDataSoure =
        when(environment) {
            "Prod" -> {
                ProdStagingUserDatasource()
            }
            else -> {
                StagingUserDatasource()
            }
        }

    fun loginUser(username: String, pass: String) {
        val user = interactors.loginUseCase(username, pass)
        loginType.value = if (user.type != UserType.Unauthorized || user.type != UserType.Undefined) CredentialType.Success
                            else CredentialType.Success
        val newName = "${user.name}|${user.type.name}"
        userTitle.value = newName
    }

    fun loadAllUser() {
        viewModelScope.launch(Dispatchers.IO) {
            val allUsers = interactors.localAllUsersUsecase?.invoke()
            val userSize = allUsers?.size ?: 0
            Log.d("DATA-BASE", "load all users ${userSize} ")
            if (userSize == 1) {
                val credential = Credential("tu-mini", "1234")
//                interactors.localSaveUsecase?.invoke(credential)
            }
        }
    }


    private fun saveUsers() {
        val allCredentials = mutableListOf<Credential>().apply {
            for (i in 0..10) {
                add(Credential("tu-minii$i","1234"))
            }
        }
        viewModelScope.launch(Dispatchers.IO) {
            interactors.saveUsersUsecase?.invoke(allCredentials)
        }
    }



}