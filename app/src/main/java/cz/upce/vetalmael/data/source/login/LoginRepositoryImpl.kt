package cz.upce.vetalmael.data.source.login

import cz.upce.vetalmael.api.VetAlmaelApi
import cz.upce.vetalmael.data.exception.UserRoleUnsupportedException
import cz.upce.vetalmael.data.model.LoginRequest
import cz.upce.vetalmael.data.model.User
import cz.upce.vetalmael.data.model.UserRole
import cz.upce.vetalmael.data.source.application.ApplicationRepository
import timber.log.Timber

class LoginRepositoryImpl(
    private val applicationRepository: ApplicationRepository,
    private val api: VetAlmaelApi
) : LoginRepository {

    override suspend fun login(username: String, password: String): User {
        val body = LoginRequest(
            username, password
        )
        val response = api.login(body)

        if (response.user.roles != UserRole.CLIENT) {
            throw UserRoleUnsupportedException(response.user.roles)
        }

        applicationRepository.setAccessToken(response.token)
        applicationRepository.setCurrentUser(response.user)
        Timber.d("Logged in successfully as ${response.user}")

        return response.user
    }
}