package cz.upce.vetalmael.extensions

import com.google.android.material.textfield.TextInputLayout

fun TextInputLayout.safeSetError(error: CharSequence?) {
    if (this.error.toString() != error.toString()) {
        this.error = error
    }
}