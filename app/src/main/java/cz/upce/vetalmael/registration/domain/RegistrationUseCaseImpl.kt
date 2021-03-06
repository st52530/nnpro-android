package cz.upce.vetalmael.registration.domain

import cz.upce.vetalmael.api.VetAlmaelApi
import cz.upce.vetalmael.data.model.dto.SignUpBody
import cz.upce.vetalmael.data.source.login.LoginRepository

class RegistrationUseCaseImpl(
    private val api: VetAlmaelApi,
    private val loginRepository: LoginRepository
) : RegistrationUseCase {

    override suspend fun register(
        username: String,
        fullName: String,
        email: String,
        password: String,
        address: String,
        phoneNumber: String
    ) {
        val body = SignUpBody(
            username = username,
            fullName = fullName,
            email = email,
            password = password,
            address = address,
            phoneNumber = phoneNumber
        )
        api.signUp(body)

        // Log in after registration to continue straight into the app.
        loginRepository.login(username, password)
    }
}