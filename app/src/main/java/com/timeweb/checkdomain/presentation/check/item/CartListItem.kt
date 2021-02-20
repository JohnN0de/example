package com.timeweb.checkdomain.presentation.check.item

import android.view.View
import androidx.core.view.isVisible
import com.timeweb.checkdomain.R
import com.timeweb.checkdomain.domain.check.model.DomainForm
import com.timeweb.checkdomain.presentation.base.adapter.BaseListItem
import com.timeweb.checkdomain.utils.TextUtils
import kotlinx.android.synthetic.main.layout_item_domain_busy.view.domainText
import kotlinx.android.synthetic.main.layout_item_domain_free.view.*

class CartListItem(
    private val data: DomainForm,
) : BaseListItem(R.layout.layout_item_domain_free) {


    override fun renderView(view: View, positionInAdapter: Int) {
        with(view) {
            domainText.text = data.domain
            cartView.isVisible = false
            priceText.text = context.getString(
                R.string.rubs_form,
                data.price
            ) //TODO: Создать Utils для форматирования прайса по шаболну 1,400.00 ₽
            yearText.text = TextUtils.generateYearText(context, data.year)
            yearText.isVisible = data.year > 0
        }
    }

    override fun getItem(): DomainForm = data

}