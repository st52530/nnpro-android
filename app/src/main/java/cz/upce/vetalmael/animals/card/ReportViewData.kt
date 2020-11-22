package cz.upce.vetalmael.animals.card

import cz.upce.vetalmael.core.view.recyclerview.Identifiable

data class ReportViewData(
    override val id: String,
    val textDescription: String,
    val textDiagnosis: String,
    val textRecommendation: String,
    val reportState: String,
    val date: String,
    val animal: String,
    val diagnosis: String,
    val operation: String,
    val medicines: List<String>,
    val consumables: List<String>
) : Identifiable