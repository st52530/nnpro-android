package cz.upce.vetalmael.animals.card

import android.app.DownloadManager
import android.os.Bundle
import android.os.Environment
import android.view.View
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import cz.upce.vetalmael.BuildConfig
import cz.upce.vetalmael.R
import cz.upce.vetalmael.api.interceptors.AuthenticationInterceptor
import cz.upce.vetalmael.core.view.recyclerview.DiffUtilAdapter
import cz.upce.vetalmael.core.view.recyclerview.IdentifiableDiffUtilAdapter
import cz.upce.vetalmael.data.model.ReportState
import cz.upce.vetalmael.data.source.animal.AnimalRepository
import cz.upce.vetalmael.data.source.application.ApplicationRepository
import cz.upce.vetalmael.extensions.setVisibleOrGone
import kotlinx.android.synthetic.main.fragment_animal_card.*
import kotlinx.android.synthetic.main.include_empty_state.*
import java.text.SimpleDateFormat

class AnimalCardFragment(
    private val animalRepository: AnimalRepository,
    private val applicationRepository: ApplicationRepository
) : Fragment(R.layout.fragment_animal_card) {

    private val adapter: DiffUtilAdapter<ReportViewData> by lazy {
        IdentifiableDiffUtilAdapter(
            animalReportDelegate(::onExportClicked)
        )
    }
    private val dateFormat = SimpleDateFormat("dd. MM. yyyy")

    private val navigationArguments by navArgs<AnimalCardFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView.adapter = adapter

        emptyStateTextView.setText(R.string.empty_animal_card)

        toolbar.setNavigationOnClickListener { findNavController().navigateUp() }

        contentLoadinglayout.setTryAgainListener {
            loadReports()
        }

        loadReports()
    }

    private fun loadReports() {
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            contentLoadinglayout.showLoading()
            try {
                val reports =
                    animalRepository.getReports(navigationArguments.animalId)
                        .sortedByDescending { it.date }
                        .map { report ->
                            val na = "N/A"
                            val reportState = when (report.reportState) {
                                ReportState.READY -> getString(R.string.report_state_waiting)
                                ReportState.DONE -> getString(
                                    R.string.report_state,
                                    report.veterinary?.fullName ?: na
                                )
                            }

                            val diagnosis = report.diagnosis?.name ?: na
                            val operation = report.operation?.name ?: na

                            val consumables = report.consumables.map {
                                it.name
                            }
                            val medicines = report.medicines.map {
                                it.name
                            }

                            ReportViewData(
                                id = report.idReport.toString(),
                                animal = report.animal.name,
                                date = dateFormat.format(report.date),
                                textDescription = report.textDescription,
                                textRecommendation = report.textRecommendation ?: na,
                                textDiagnosis = report.textDiagnosis ?: na,
                                reportState = reportState,
                                consumables = consumables,
                                medicines = medicines,
                                diagnosis = diagnosis,
                                operation = operation
                            )
                        }
                adapter.items = reports
                emptyStateLayout.setVisibleOrGone(reports.isEmpty())
                contentLoadinglayout.showData()
            } catch (exception: Exception) {
                contentLoadinglayout.showError()
            }
        }
    }

    private fun onExportClicked(report: ReportViewData) {
        val reportUrl = "${BuildConfig.API_URL}export/${report.id}"
        val request = DownloadManager.Request(reportUrl.toUri())
        request.setTitle("Záznam o vyšetření")
        request.setDescription("Záznam ze dne ${report.date}.")

        val token = applicationRepository.getAccessToken()
        request.addRequestHeader(
            AuthenticationInterceptor.AUTHENTICATION_TOKEN_NAME,
            "Bearer $token"
        )

        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)

        request.setDestinationInExternalPublicDir(
            Environment.DIRECTORY_DOWNLOADS,
            "report-${report.date}.pdf"
        )

        val manager = getSystemService(requireContext(), DownloadManager::class.java)!!
        manager.enqueue(request)

        Snackbar.make(
            requireView(),
            "Stahuji záznam o vyšetření (zkontrolujte notifikace)",
            Snackbar.LENGTH_LONG
        ).show()
    }
}