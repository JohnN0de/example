package com.timeweb.checkdomain.presentation.profile

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.navGraphViewModels
import com.timeweb.checkdomain.R
import com.timeweb.checkdomain.domain.check.model.DomainForm
import com.timeweb.checkdomain.domain.model.onSuccess
import com.timeweb.checkdomain.presentation.base.BaseFragment
import com.timeweb.checkdomain.presentation.cart.SharedCartViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_profile.*

@AndroidEntryPoint
class ProfileFragment : BaseFragment<ProfileViewModel>() {

    override fun fragmentLayout(): Int = R.layout.fragment_profile
    override val viewModel: ProfileViewModel by viewModels()

    private val sharedCartViewModel: SharedCartViewModel by navGraphViewModels(R.id.main_graph)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUi()
        initObservers()
    }

    private fun initUi() {
        initToolbar(toolbar)
    }

    private fun initObservers() {
        sharedCartViewModel.cartListResource.observe(viewLifecycleOwner) { resource ->
            resource.onSuccess {
                setCart(it)
            }
        }
    }

    private fun setCart(it: List<DomainForm>) {
        cartLayout.isVisible = it.count() > 0
        sumCartText.text =
            getString(R.string.rubs_form, it.sumBy { it.price.toInt() }.toDouble())
    }

}