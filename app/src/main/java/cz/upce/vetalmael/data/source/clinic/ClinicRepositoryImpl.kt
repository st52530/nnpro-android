package cz.upce.vetalmael.data.source.clinic

import cz.upce.vetalmael.api.VetAlmaelApi
import cz.upce.vetalmael.data.model.Clinic
import cz.upce.vetalmael.data.source.application.ApplicationRepository
import org.koin.core.KoinComponent
import org.koin.core.inject

class ClinicRepositoryImpl(
    private val api: VetAlmaelApi
) : ClinicRepository, KoinComponent {

    // Use setter injection to prevent weird dependency circles.
    private val applicationRepository: ApplicationRepository by inject()

    private var cache: List<Clinic> = emptyList()

    override suspend fun getClinics(force: Boolean): List<Clinic> {
        if (!force && cache.isNotEmpty()) {
            return cache
        }

        val clinics = api.getClinics()
        cache = clinics

        return clinics.toList()
    }

    override fun erase() {
        cache = emptyList()
    }
}