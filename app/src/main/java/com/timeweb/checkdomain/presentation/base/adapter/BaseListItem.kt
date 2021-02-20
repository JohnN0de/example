package com.timeweb.checkdomain.presentation.base.adapter

import android.view.View
import androidx.annotation.LayoutRes

abstract class BaseListItem(@LayoutRes open var viewId: Int) {

    abstract fun renderView(
        view: View,
        positionInAdapter: Int = 0
    )

    abstract fun getItem(): Any

    override fun equals(other: Any?): Boolean {
        return super.equals(other)
    }

    open fun canSwipe() = false

    open fun getSwipeableViews(rootView: View): List<View> {
        return emptyList()
    }

    override fun hashCode(): Int {
        return viewId
    }

}
