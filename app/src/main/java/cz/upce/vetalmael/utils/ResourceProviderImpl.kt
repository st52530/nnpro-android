package cz.upce.vetalmael.utils

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat

class ResourceProviderImpl(
    private val context: Context
) : ResourceProvider {

    override fun getString(@StringRes resId: Int): String = context.getString(resId)

    override fun getString(@StringRes resId: Int, vararg formatArgs: Any?): String =
        context.getString(resId, *formatArgs)

    override fun getColor(@ColorRes resId: Int): Int = ContextCompat.getColor(context, resId)

    override fun getDimen(@DimenRes resId: Int): Float = context.resources.getDimension(resId)

    override fun getDrawable(resId: Int): Drawable = ContextCompat.getDrawable(context, resId)!!
}