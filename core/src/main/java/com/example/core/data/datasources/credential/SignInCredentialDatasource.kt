package com.example.core.data.datasources


interface SignInCredentialDatasource {

    fun getAll() : List<ICredential>

    fun getBy(id: Int) : ICredential

    fun getByIdentifier(identifier: String): ICredential?

    fun save(credential: ICredential)

    fun saveAll(credentials: List<ICredential>)


    fun remove(credential: ICredential)

    interface ICredential{
        val identifier: String
        val key: String
    }
}