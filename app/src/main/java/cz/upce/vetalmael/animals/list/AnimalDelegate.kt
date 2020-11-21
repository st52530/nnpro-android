package cz.upce.vetalmael.animals.list

import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateLayoutContainer
import cz.upce.vetalmael.R
import kotlinx.android.synthetic.main.item_animal.*

fun animalDelegate(
    onItemClicked: (AnimalViewData) -> Unit
) = adapterDelegateLayoutContainer<
        AnimalViewData, AnimalViewData>(R.layout.item_animal) {
    itemView.setOnClickListener {
        onItemClicked(item)
    }

    bind {
        nameTextView.text = item.name
    }
}