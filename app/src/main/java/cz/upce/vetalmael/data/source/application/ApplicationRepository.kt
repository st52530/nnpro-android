package cz.upce.vetalmael.data.source.application

import cz.upce.vetalmael.base.repository.ErasableRepository
import cz.upce.vetalmael.data.model.User

interface ApplicationRepository : ErasableRepository {

    fun getAccessToken(): String?

    fun setAccessToken(token: String?)

    fun getCurrentUser(): User?

    fun setCurrentUser(user: User?)
}