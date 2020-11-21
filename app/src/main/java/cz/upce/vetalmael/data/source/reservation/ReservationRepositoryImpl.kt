package cz.upce.vetalmael.data.source.reservation

import cz.upce.vetalmael.api.VetAlmaelApi
import cz.upce.vetalmael.data.model.Reservation

class ReservationRepositoryImpl(
    private val api: VetAlmaelApi
) : ReservationRepository {

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

    override fun erase() {
        cache = emptyList()
    }
}