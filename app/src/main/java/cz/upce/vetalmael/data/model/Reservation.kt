package cz.upce.vetalmael.data.model

import java.util.*

data class Reservation(
    val idReservation: Int,
    val date: Date,
    val clinic: Clinic,
    val client: User
)