package cz.upce.vetalmael.data.model

data class Diagnosis(
    val idDiagnosis: Int,
    val name: String,
    val type: String,
    val targetAnimals: String,
    val symptoms: String,
    val incubationPeriod: String,
    val treatment: String,
    val prevention: String
)