package cz.upce.vetalmael.data.model

import java.util.*

data class Consumable(
    val idConsumable: Int,
    val name: String,
    val code: String,
    val price: Double,
    val nameAddition: String,
    val groupType: String,
    val prescriptionDesignation: String,
    val unitOfMeasure: String,
    val producer: String,
    val counteryOfOrigin: String,
    val dateOfExpiration: Date,
    val dateOfChange: Date
)