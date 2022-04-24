package com.example.core

import com.example.core.data.models.UserType

data class User (val name: String = "",
                 val type: UserType = UserType.Undefined)