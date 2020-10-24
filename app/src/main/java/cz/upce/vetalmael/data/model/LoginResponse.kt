package cz.upce.vetalmael.data.model

data class LoginResponse(
    val token: String,
    val user: User
)