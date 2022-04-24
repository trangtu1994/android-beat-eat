package com.example.core.data.managers

import com.example.core.data.datasources.SignInCredentialDatasource
import com.example.core.data.datasources.SignInCredentialDatasource.ICredential

class LocalCredentialManager(private val datasource: SignInCredentialDatasource) {

    fun getCredential(name: String, password: String): ICredential? {
        val credential = datasource.getByIdentifier(name)
        val isValid = credential?.let { it.key == password } ?: false
        if (isValid) {
            return credential
        }
        return null
    }

    suspend fun saveCredential(credential: ICredential) {
        datasource.save(credential = credential)
    }

    suspend fun getAllSavedUsers() : List<ICredential> =
        datasource.getAll()

    suspend fun saveAllCredential(credentials: List<ICredential>) {
        datasource.saveAll(credentials)
    }

}