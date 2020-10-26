package cz.upce.vetalmael.animals

import cz.upce.vetalmael.core.view.recyclerview.Identifiable

data class ReservationViewData(
    override val id: String,
    val date: String,
    val time: String
) : Identifiable