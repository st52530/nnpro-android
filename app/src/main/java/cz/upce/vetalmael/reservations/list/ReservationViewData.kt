package cz.upce.vetalmael.reservations.list

import cz.upce.vetalmael.core.view.recyclerview.Identifiable

data class ReservationViewData(
    override val id: String,
    val clinic: String,
    val date: String,
    val time: String
) : Identifiable