package cz.upce.vetalmael.core.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.annotation.AttrRes
import cz.upce.vetalmael.R
import kotlinx.android.synthetic.main.view_layout_content_loading.view.*

class ContentLoadingLayout : FrameLayout {

    companion object {
        /**
         * Position of the state in [viewFlipper].
         */
        private const val LOADING_POSITION = 0
        private const val ERROR_POSITION = 1
        private const val DATA_POSITION = 2
    }

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, @AttrRes defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    init {
        View.inflate(context, R.layout.view_layout_content_loading, this)
    }

    override fun addView(child: View?, index: Int, params: ViewGroup.LayoutParams?) {
        if (child?.id == R.id.viewFlipper) {
            super.addView(child, index, params)
            return
        }

        // Add states to the view flipper!
        viewFlipper.addView(child, index, params)
    }

    fun showData() {
        viewFlipper.displayedChild = DATA_POSITION
    }

    fun showLoading() {
        viewFlipper.displayedChild = LOADING_POSITION
    }

    fun showError() {
        viewFlipper.displayedChild = ERROR_POSITION
    }

    fun setTryAgainListener(listener: () -> Unit) {
        tryAgainButton.setOnClickListener {
            listener()
        }
    }
}