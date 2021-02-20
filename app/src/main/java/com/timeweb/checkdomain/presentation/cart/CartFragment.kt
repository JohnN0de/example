package com.timeweb.checkdomain.presentation.cart

import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.navGraphViewModels
import com.timeweb.checkdomain.R
import com.timeweb.checkdomain.domain.check.model.DomainForm
import com.timeweb.checkdomain.domain.model.onSuccess
import com.timeweb.checkdomain.presentation.base.BaseFragment
import com.timeweb.checkdomain.presentation.base.adapter.BaseRecyclerAdapter
import com.timeweb.checkdomain.presentation.check.item.CartListItem
import com.timeweb.checkdomain.presentation.extension.getColor
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_cart.*

@AndroidEntryPoint
class CartFragment : BaseFragment<CartViewModel>() {

    override fun fragmentLayout(): Int = R.layout.fragment_cart

    override val viewModel: CartViewModel by viewModels()

    private val adapter = BaseRecyclerAdapter()

    private val sharedCartViewModel: SharedCartViewModel by navGraphViewModels(R.id.main_graph)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUi()
        initObservers()
    }

    private fun initUi() {
        initToolbar(toolbar)
        contentList.adapter = adapter
        buyBtn.setOnClickListener(
            Navigation.createNavigateOnClickListener(
                CartFragmentDirections.actionCartFragmentToProfileFragment()
            )
        )
    }

    private fun initObservers() {
        sharedCartViewModel.cartListResource.observe(viewLifecycleOwner) { resource ->
            resource.onSuccess {
                toolbar.title = getString(R.string.cart_title_form, it.count())
                setCart(it)
                adapter.replaceElements(it.map { CartListItem(it) })
                adapter.notifyDataSetChanged()
            }
        }
    }

    private fun setCart(it: List<DomainForm>) {
        cartLayout.isVisible = it.count() > 0
        sumCartText.text =
            getString(R.string.rubs_form, it.sumBy { it.price.toInt() }.toDouble())
    }

    override fun onResume() {
        super.onResume()

        requireActivity().window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)

        requireActivity().window.decorView.systemUiVisibility =
            requireActivity().window.decorView.systemUiVisibility or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        requireActivity().window.statusBarColor = getColor(R.color.colorPrimaryDark)
    }

    override fun onPause() {
        super.onPause()

        requireActivity().window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
    }

}