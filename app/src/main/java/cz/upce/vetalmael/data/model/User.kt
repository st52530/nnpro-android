package cz.upce.vetalmael.data.model

data class User(
    val idUser: Int,
    val email: String,
    val username: String,
    val fullName: String,
    val roles: UserRole,
    val address: String?,
    val phoneNumber: String?
)