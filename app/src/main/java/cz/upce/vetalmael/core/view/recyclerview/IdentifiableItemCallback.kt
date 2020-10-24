package cz.upce.vetalmael.core.view.recyclerview

import androidx.recyclerview.widget.DiffUtil

class IdentifiableItemCallback<T : Identifiable> : DiffUtil.ItemCallback<T>() {

    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
        return oldItem == newItem
    }

}