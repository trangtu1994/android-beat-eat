package com.example.beatoreat.model

import com.example.core.data.datasources.SignInCredentialDatasource

class Credential(val username: String,
                 val pwd: String,
                 val environment: Environment = Environment.Staging,
                 val expectedType: CredentialType = CredentialType.Success): SignInCredentialDatasource.ICredential {


    override val identifier: String
        get() = username
    override val key: String
        get() = pwd

}