package cz.upce.vetalmael.data.model.dto

import cz.upce.vetalmael.data.model.User

data class LoginResponse(
    val token: String,
    val user: User
)