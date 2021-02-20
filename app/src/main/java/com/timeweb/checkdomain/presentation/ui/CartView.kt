package com.timeweb.checkdomain.presentation.ui

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.timeweb.checkdomain.R
import kotlinx.android.synthetic.main.layout_cart_view.view.*

class CartView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    var onChangeState: ((added: Boolean) -> Unit)? = null

    private var view: View = View.inflate(context, R.layout.layout_cart_view, this)

    init {
        view.cartBtn.setOnClickListener {
            view.isSelected = !view.isSelected
            onChangeState?.invoke(view.isSelected)
        }
    }
}