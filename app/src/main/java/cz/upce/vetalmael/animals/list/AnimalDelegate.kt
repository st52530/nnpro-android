package cz.upce.vetalmael.animals.list

import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateLayoutContainer
import cz.upce.vetalmael.R
import kotlinx.android.synthetic.main.item_animal.*

fun animalDelegate(
    onMessagesClicked: (AnimalViewData) -> Unit,
    onReportsClicked: (AnimalViewData) -> Unit,
    onDeleteClicked: (AnimalViewData) -> Unit
) = adapterDelegateLayoutContainer<
        AnimalViewData, AnimalViewData>(R.layout.item_animal) {
    messagesButton.setOnClickListener {
        onMessagesClicked(item)
    }
    reportsButton.setOnClickListener {
        onReportsClicked(item)
    }
    deleteButton.setOnClickListener {
        onDeleteClicked(item)
    }

    bind {
        nameTextView.text = item.name
    }
}