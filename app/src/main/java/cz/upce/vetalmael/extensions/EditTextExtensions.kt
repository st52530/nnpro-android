package cz.upce.vetalmael.extensions

import android.widget.EditText

fun EditText.safeSetText(newText: String) {
    if (text.toString() == newText) {
        return
    }

    setText(newText)
}