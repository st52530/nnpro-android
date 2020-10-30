package cz.upce.vetalmael.registration.domain

import cz.upce.vetalmael.api.VetAlmaelApi
import cz.upce.vetalmael.data.source.login.LoginRepository
import cz.upce.vetalmael.registration.domain.RegistrationUseCase

class RegistrationUseCaseImpl(
    private val api: VetAlmaelApi,
    private val loginRepository: LoginRepository
) : RegistrationUseCase {

    override suspend fun register(
        username: String,
        fullName: String,
        email: String,
        password: String
    ) {
        TODO("Not yet implemented")
    }
}