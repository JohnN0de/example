package com.timeweb.checkdomain.utils

import android.content.Context
import com.timeweb.checkdomain.R

object TextUtils {

    fun generateYearText(context: Context, y: Int): String =
        context.getString(
            when {
                y > 4 -> R.string.year_many_form
                y > 1 -> R.string.year_few_form
                else -> R.string.year_single_form
            }, y
        )
}