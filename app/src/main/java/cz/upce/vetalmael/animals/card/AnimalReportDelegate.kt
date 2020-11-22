package cz.upce.vetalmael.animals.card

import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateLayoutContainer
import cz.upce.vetalmael.R
import kotlinx.android.synthetic.main.item_animal_report.*

fun animalReportDelegate(
    onExportClicked: (ReportViewData) -> Unit
) = adapterDelegateLayoutContainer<
        ReportViewData, ReportViewData>(R.layout.item_animal_report) {
    exportButton.setOnClickListener {
        onExportClicked(item)
    }

    bind {
        reportIdTextView.text = getString(R.string.report_id_text, item.id, item.date)
        stateTextView.text = item.reportState
        animalTextView.text = getString(R.string.report_animal, item.animal)
        descriptionTextView.text = getString(R.string.report_text_description, item.textDescription)
        diagnosisTextView.text = getString(R.string.report_text_diagnosis, item.textDiagnosis)
        recommendationTextView.text =
            getString(R.string.report_text_recommendation, item.textRecommendation)
        diagnosis2TextView.text = getString(R.string.report_diagnosis, item.diagnosis)
        diagnosis2TextView.text = getString(R.string.report_operation, item.operation)

        // TODO: Medicines + consumables
    }
}