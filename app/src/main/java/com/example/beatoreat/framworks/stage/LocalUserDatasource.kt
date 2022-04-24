package com.example.beatoreat.framworks.stage

import com.example.beatoreat.database.dao.UserDao
import com.example.beatoreat.database.entities.UserDto
import com.example.beatoreat.model.Credential
import com.example.beatoreat.model.CredentialType
import com.example.core.data.datasources.SignInCredentialDatasource

class LocalUserDatasource(val userDao: UserDao?): SignInCredentialDatasource {

    override fun getAll(): List<Credential> {
        val list = userDao?.getAll()
        if (list == null || list.isEmpty()) {return mutableListOf()}
        val credentials = list.map { Credential(it.name, it.password) }
        return credentials
    }

    override fun getBy(id: Int): Credential {
        TODO("Not yet implemented")
    }

    override fun getByIdentifier(identifier: String): Credential? =
        userDao?.getByName(identifier)?.let { Credential(it.name, it.password, expectedType = CredentialType.Success) }

    override fun save(credential: SignInCredentialDatasource.ICredential) {
        val userDto = UserDto(id = 0,
            name = credential.identifier,
            password = credential.key)
        userDao?.insert(userDto)
    }

    override fun saveAll(credentials: List<SignInCredentialDatasource.ICredential>) {
        val userDtos = credentials.map { UserDto(id = 0,
            name = it.identifier,
            password = it.key) }
        userDao?.insertAll(userDtos)
    }

    override fun remove(credential: SignInCredentialDatasource.ICredential) {
        TODO("Not yet implemented")
    }

}