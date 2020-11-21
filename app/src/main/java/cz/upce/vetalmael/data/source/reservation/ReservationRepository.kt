package cz.upce.vetalmael.data.source.reservation

import cz.upce.vetalmael.base.repository.ErasableRepository
import cz.upce.vetalmael.data.model.Reservation
import java.util.*

interface ReservationRepository : ErasableRepository {

    suspend fun getReservations(force: Boolean): List<Reservation>

    suspend fun getReservation(id: Int): Reservation

    suspend fun addReservation(clinicId: Int, date: Date)

    suspend fun deleteReservation(reservationId: Int)
}