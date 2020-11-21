package cz.upce.vetalmael.data.model

import java.util.*

data class Message(
    val idMessage: Int,
    val text: String,
    val date: Date,
    val sender: User,
    val animal: Animal
)