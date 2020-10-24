package cz.upce.vetalmael.extensions

import android.view.View
import android.view.ViewGroup
import androidx.annotation.Px
import androidx.core.view.*
import cz.upce.vetalmael.R

fun View.setVisibleOrGone(visible: Boolean) {
    visibility = if (visible) View.VISIBLE else View.GONE
}

fun View.setVisibleOrInvisible(visible: Boolean) {
    visibility = if (visible) View.VISIBLE else View.INVISIBLE
}

inline fun <reified T : ViewGroup.MarginLayoutParams> View.updateMargins(
    @Px left: Int = marginLeft,
    @Px top: Int = marginTop,
    @Px right: Int = marginRight,
    @Px bottom: Int = marginBottom
) {
    updateLayoutParams<T> {
        setMargins(left, top, right, bottom)
    }
}
