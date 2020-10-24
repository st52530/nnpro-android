package cz.upce.vetalmael.data.source.login

import cz.upce.vetalmael.data.model.User

interface LoginRepository {

    suspend fun login(username: String, password: String): User
}