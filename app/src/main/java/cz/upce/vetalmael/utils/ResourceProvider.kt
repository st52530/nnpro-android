package cz.upce.vetalmael.utils

import android.graphics.drawable.Drawable
import androidx.annotation.*

interface ResourceProvider {

    fun getString(@StringRes resId: Int): String

    fun getString(@StringRes resId: Int, vararg formatArgs: Any?): String

    @ColorInt
    fun getColor(@ColorRes resId: Int): Int

    fun getDimen(@DimenRes resId: Int): Float

    fun getDrawable(@DrawableRes resId: Int): Drawable
}