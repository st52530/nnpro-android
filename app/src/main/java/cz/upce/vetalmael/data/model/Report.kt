package cz.upce.vetalmael.data.model

import java.util.*

data class Report(
    val idReport: Int,
    val textDescription: String,
    val textDiagnosis: String?,
    val textRecommendation: String?,
    val reportState: ReportState,
    val date: Date,
    val animal: Animal,
    val veterinary: User?,
    val diagnosis: Diagnosis?,
    val operation: Operation?,
    val medicines: List<Medicine>,
    val consumables: List<Consumable>
)

enum class ReportState {
    READY,
    DONE
}