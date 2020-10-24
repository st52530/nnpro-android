package cz.upce.vetalmael.core.view.recyclerview

import androidx.recyclerview.widget.DiffUtil
import com.hannesdorfmann.adapterdelegates4.AdapterDelegate
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter

open class DiffUtilAdapter<T>(
    diffCallback: DiffUtil.ItemCallback<T>,
    vararg delegates: AdapterDelegate<List<T>>
) : AsyncListDifferDelegationAdapter<T>(diffCallback, *delegates)