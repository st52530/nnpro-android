package cz.upce.vetalmael.data.source.reservation

import cz.upce.vetalmael.api.VetAlmaelApi
import cz.upce.vetalmael.data.model.Reservation

class ReservationRepositoryImpl(
    private val api: VetAlmaelApi
) : ReservationRepository {

    private var cache: List<Reservation> = emptyList()

    override suspend fun getReservations(): List<Reservation> {
        if (cache.isNotEmpty()) {
            return cache
        }

        val reservations = api.getReservations()
        cache = reservations

        return reservations.toList()
    }

    override suspend fun getReservation(id: Int): Reservation {
        val predicate: (Reservation) -> Boolean = { it.idReservation == id }
        return cache.find(predicate) ?: api.getReservations().find(predicate)!!
    }

    override fun erase() {
        cache = emptyList()
    }
}