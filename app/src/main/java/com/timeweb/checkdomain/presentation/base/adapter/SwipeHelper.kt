package com.timeweb.checkdomain.presentation.base.adapter

import android.view.View

interface Swipeable {
    fun canSwipe(): Boolean = false

    fun getSwipeableViews(): List<View> = emptyList()
}
