package cz.upce.vetalmael.reservations

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import cz.upce.vetalmael.R
import cz.upce.vetalmael.animals.ReservationViewData
import cz.upce.vetalmael.core.view.recyclerview.DiffUtilAdapter
import cz.upce.vetalmael.core.view.recyclerview.IdentifiableDiffUtilAdapter
import cz.upce.vetalmael.data.source.reservation.ReservationRepository
import kotlinx.android.synthetic.main.fragment_animals.*

class ReservationsFragment(
    private val reservationRepository: ReservationRepository
) : Fragment(R.layout.fragment_reservations) {

    private val adapter: DiffUtilAdapter<ReservationViewData> by lazy {
        IdentifiableDiffUtilAdapter(
            reservationDelegate(::onReservationClicked)
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView.adapter = adapter

        contentLoadinglayout.setTryAgainListener {
            loadAnimals()
        }

        loadAnimals()
    }

    private fun loadAnimals() {
        lifecycleScope.launchWhenCreated {
            contentLoadinglayout.showLoading()
            try {
                val reservations = reservationRepository.getReservations().map {
                    ReservationViewData(
                        it.idReservation.toString(),
                        "TODO: 20. 12. 2020",
                        "TODO: 10:30"
                    )
                }
                adapter.items = reservations
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