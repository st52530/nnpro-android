package cz.upce.vetalmael.data.model

data class Animal(
    val idAnimal: Int,
    val name: String,
    val owner: User
)