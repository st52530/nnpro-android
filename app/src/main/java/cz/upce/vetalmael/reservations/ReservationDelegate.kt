package cz.upce.vetalmael.reservations

import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateLayoutContainer
import cz.upce.vetalmael.R
import cz.upce.vetalmael.animals.ReservationViewData
import kotlinx.android.synthetic.main.item_reservation.*

fun reservationDelegate(
    onItemClicked: (ReservationViewData) -> Unit
) = adapterDelegateLayoutContainer<
        ReservationViewData, ReservationViewData>(R.layout.item_reservation) {
    itemView.setOnClickListener {
        onItemClicked(item)
    }

    bind {
        dateTextView.text = getString(R.string.reservation_title, item.date)
        timeTextView.text = item.time
    }
}