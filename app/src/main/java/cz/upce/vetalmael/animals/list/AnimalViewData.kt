package cz.upce.vetalmael.animals.list

import cz.upce.vetalmael.core.view.recyclerview.Identifiable

data class AnimalViewData(
    override val id: String,
    val name: String
) : Identifiable