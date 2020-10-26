package cz.upce.vetalmael.data.source.reservation

import cz.upce.vetalmael.base.repository.ErasableRepository
import cz.upce.vetalmael.data.model.Animal
import cz.upce.vetalmael.data.model.Reservation

interface ReservationRepository : ErasableRepository {

    suspend fun getReservations(): List<Reservation>

    suspend fun getReservation(id: Int): Reservation
}