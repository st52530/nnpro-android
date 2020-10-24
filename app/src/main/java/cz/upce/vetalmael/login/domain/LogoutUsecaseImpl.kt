package cz.upce.vetalmael.login.domain

import cz.upce.vetalmael.base.repository.ErasableRepository

class LogoutUsecaseImpl(
    private val erasableRepositories: List<ErasableRepository>
) : LogoutUsecase {

    override suspend fun logout() {
        erasableRepositories.forEach { repository ->
            repository.erase()
        }
    }
}