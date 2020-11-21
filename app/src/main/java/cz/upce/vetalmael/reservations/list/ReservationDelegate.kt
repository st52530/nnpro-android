package cz.upce.vetalmael.reservations.list

import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateLayoutContainer
import cz.upce.vetalmael.R
import kotlinx.android.synthetic.main.item_reservation.*

fun reservationDelegate(
    onItemClicked: (ReservationViewData) -> Unit
) = adapterDelegateLayoutContainer<
        ReservationViewData, ReservationViewData>(R.layout.item_reservation) {
    itemView.setOnClickListener {
        onItemClicked(item)
    }

    bind {
        clinicTextView.text = item.clinic
        dateTextView.text = getString(R.string.reservation_title, item.date)
        timeTextView.text = item.time
    }
}