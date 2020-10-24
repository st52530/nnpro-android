package cz.upce.vetalmael.core.view.recyclerview

import com.hannesdorfmann.adapterdelegates4.AdapterDelegate

class IdentifiableDiffUtilAdapter<T : Identifiable>(
    vararg delegates: AdapterDelegate<List<T>>
) : DiffUtilAdapter<T>(IdentifiableItemCallback<T>(), *delegates)