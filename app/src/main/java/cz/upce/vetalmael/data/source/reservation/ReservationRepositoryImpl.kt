package cz.upce.vetalmael.data.source.reservation

import cz.upce.vetalmael.api.VetAlmaelApi
import cz.upce.vetalmael.data.model.Reservation
import cz.upce.vetalmael.data.model.dto.CreateReservationRequest
import cz.upce.vetalmael.data.source.application.ApplicationRepository
import org.koin.core.KoinComponent
import org.koin.core.inject
import java.lang.IllegalStateException
import java.util.*

class ReservationRepositoryImpl(
    private val api: VetAlmaelApi
) : ReservationRepository, KoinComponent {

    // Use setter injection to prevent weird dependency circles.
    private val applicationRepository: ApplicationRepository by inject()

    private var cache: List<Reservation> = emptyList()

    override suspend fun getReservations(force: Boolean): List<Reservation> {
        if (!force && cache.isNotEmpty()) {
            return cache
        }

        val reservations = api.getReservations()
        cache = reservations

        return reservations.toList()
    }

    override suspend fun getReservation(id: Int): Reservation {
        val predicate: (Reservation) -> Boolean = { it.idReservation == id }
        return getReservations(false).find(predicate) ?: getReservations(true).find(predicate)!!
    }

    override suspend fun addReservation(clinicId: Int, date: Date) {
        val userId = applicationRepository.getCurrentUser()?.idUser!!
        val body = CreateReservationRequest(date)
        val reservation = api.createReservation(clinicId, userId, body)

        val newCache = cache.toMutableList()
        newCache.add(reservation)
        cache = newCache.toList()
    }

    override suspend fun deleteReservation(reservationId: Int) {
        if (!api.deleteReservation(reservationId).isSuccessful) {
            throw IllegalStateException("API Error!")
        }

        val newCache = cache.toMutableList()
        newCache.removeAll { it.idReservation == reservationId }
        cache = newCache.toList()
    }

    override fun erase() {
        cache = emptyList()
    }
}