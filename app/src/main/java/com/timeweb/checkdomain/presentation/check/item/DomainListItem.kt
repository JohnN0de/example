package com.timeweb.checkdomain.presentation.check.item

import android.view.View
import androidx.core.view.isVisible
import com.timeweb.checkdomain.R
import com.timeweb.checkdomain.domain.check.model.DomainForm
import com.timeweb.checkdomain.domain.check.model.DomainStatus
import com.timeweb.checkdomain.presentation.base.adapter.BaseListItem
import com.timeweb.checkdomain.utils.TextUtils
import kotlinx.android.synthetic.main.layout_item_domain_busy.view.domainText
import kotlinx.android.synthetic.main.layout_item_domain_free.view.*

class DomainListItem(
    private val data: DomainStatus,
    private val form: DomainForm,
    private val onChangeCartState: ((added: Boolean, DomainForm) -> Unit)? = null,
    private var inCart: Boolean = false
) : BaseListItem(if (data.free) R.layout.layout_item_domain_free else R.layout.layout_item_domain_busy) {

    private var view: View? = null

    override fun renderView(view: View, positionInAdapter: Int) {
        this.view = view
        with(view) {
            domainText.text = form.domain
            if (data.free) {
                cartView.onChangeState = {
                    onChangeCartState?.invoke(it, form)
                }
                priceText.text = context.getString(
                    R.string.rubs_form,
                    form.price
                ) //TODO: Создать Utils для форматирования прайса по шаболну 1,400.00 ₽
                yearText.text = TextUtils.generateYearText(context, form.year)
                yearText.isVisible = form.year > 0
                cartView.isSelected = inCart
            }
        }
    }

    fun setInCart(b: Boolean) {
        inCart = b
        view?.cartView?.isSelected = b
    }

    override fun getItem(): DomainStatus = data
    fun getForm(): DomainForm = form

}