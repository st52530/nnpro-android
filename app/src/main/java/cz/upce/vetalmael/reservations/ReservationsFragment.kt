package cz.upce.vetalmael.reservations

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import cz.upce.vetalmael.R
import cz.upce.vetalmael.core.view.recyclerview.DiffUtilAdapter
import cz.upce.vetalmael.core.view.recyclerview.IdentifiableDiffUtilAdapter
import cz.upce.vetalmael.data.source.reservation.ReservationRepository
import cz.upce.vetalmael.extensions.setVisibleOrGone
import kotlinx.android.synthetic.main.fragment_animals.*
import kotlinx.android.synthetic.main.include_empty_state.*
import java.text.SimpleDateFormat

class ReservationsFragment(
    private val reservationRepository: ReservationRepository
) : Fragment(R.layout.fragment_reservations) {

    private val adapter: DiffUtilAdapter<ReservationViewData> by lazy {
        IdentifiableDiffUtilAdapter(
            reservationDelegate(::onReservationClicked)
        )
    }

    private val dateFormat = SimpleDateFormat("dd. MM. yyyy")

    private val timeFormat = SimpleDateFormat("hh:mm")

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView.adapter = adapter

        emptyStateTextView.setText(R.string.empty_reservation_list)

        contentLoadinglayout.setTryAgainListener {
            loadReservations(true)
        }

        loadReservations()
    }

    private fun loadReservations(force: Boolean = false) {
        lifecycleScope.launchWhenCreated {
            contentLoadinglayout.showLoading()
            try {
                val reservations = reservationRepository.getReservations(force).map { reservation ->
                    ReservationViewData(
                        reservation.idReservation.toString(),
                        dateFormat.format(reservation.date),
                        timeFormat.format(reservation.date)
                    )
                }
                adapter.items = reservations
                emptyStateLayout.setVisibleOrGone(reservations.isEmpty())
                contentLoadinglayout.showData()
            } catch (exception: Exception) {
                contentLoadinglayout.showError()
            }
        }
    }

    private fun onReservationClicked(reservation: ReservationViewData) {
        // TODO: Handle animal click.
    }
}