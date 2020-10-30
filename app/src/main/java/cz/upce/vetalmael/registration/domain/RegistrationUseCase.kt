package cz.upce.vetalmael.registration.domain

interface RegistrationUseCase {

    suspend fun register(
        username: String,
        fullName: String,
        email: String,
        password: String
    )
}