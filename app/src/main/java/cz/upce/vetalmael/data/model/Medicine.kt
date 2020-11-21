package cz.upce.vetalmael.data.model

import java.util.*

data class Medicine(
    val idMedicine: Int,
    val name: String,
    val code: String,
    val substances: String,
    val targetAnimals: String,
    val form: String,
    val dateOfApproval: Date,
    val numberOfApproval: String,
    val approvalHolder: String,
    val protectionPeriod: String,
    val type: String,
    val packageSize: String
)