package cz.upce.vetalmael.data.model

data class Operation(
    val idOperation: Int,
    val name: String,
    val price: Double,
    val type: String,
    val description: String,
    val length: String,
    val note: String,
    val targetAnimals: String
)