package cz.upce.vetalmael.extensions

import androidx.fragment.app.Fragment

fun Fragment.hideKeyboard() {
    activity?.hideKeyboard()
}