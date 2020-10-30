package cz.upce.vetalmael.data.model

data class SignUpBody(
    val username: String,
    val fullName: String,
    val email: String,
    val password: String
)