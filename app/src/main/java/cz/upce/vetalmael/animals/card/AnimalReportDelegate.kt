package cz.upce.vetalmael.animals.card

import android.content.Context
import android.graphics.Typeface
import android.widget.TextView
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
        operationTextView.text = getString(R.string.report_operation, item.operation)

        medicinesLayout.removeAllViews()
        if (item.medicines.isEmpty()) {
            medicinesLayout.addView(context.createTextView("Nebyly použity žádné léky"))
        } else {
            medicinesLayout.addView(context.createTextView("Využité léky:", true))
            item.medicines.forEach { medicine ->
                medicinesLayout.addView(context.createTextView(medicine))
            }
        }

        consumablesLayout.removeAllViews()
        if (item.medicines.isEmpty()) {
            medicinesLayout.addView(context.createTextView("Nebyl použit žádný spotřební materiál"))
        } else {
            medicinesLayout.addView(context.createTextView("Využitý spotřební materiál:", true))
            item.medicines.forEach { consumable ->
                medicinesLayout.addView(context.createTextView(consumable))
            }
        }
    }
}

private fun Context.createTextView(text: String, bold: Boolean = false): TextView {
    return TextView(this, null, R.style.TextAppearance_VetAlmael_Subtitle1).apply {
        this.text = text
        if (bold) {
            setTypeface(null, Typeface.BOLD)
        }
    }
}