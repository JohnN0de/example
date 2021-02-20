package com.timeweb.checkdomain.presentation.check

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.navGraphViewModels
import com.timeweb.checkdomain.R
import com.timeweb.checkdomain.domain.check.model.DomainForm
import com.timeweb.checkdomain.domain.model.Resource
import com.timeweb.checkdomain.domain.model.onFailure
import com.timeweb.checkdomain.domain.model.onSuccess
import com.timeweb.checkdomain.presentation.base.BaseFragment
import com.timeweb.checkdomain.presentation.base.adapter.BaseRecyclerAdapter
import com.timeweb.checkdomain.presentation.cart.SharedCartViewModel
import com.timeweb.checkdomain.presentation.check.item.DomainListItem
import com.timeweb.checkdomain.presentation.extension.getColor
import com.timeweb.checkdomain.presentation.extension.showInfoToast
import com.timeweb.checkdomain.presentation.extension.showKeyboard
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_check_domain.*

@AndroidEntryPoint
class CheckDomainFragment : BaseFragment<CheckDomainViewModel>() {

    override fun fragmentLayout(): Int = R.layout.fragment_check_domain

    override val viewModel: CheckDomainViewModel by viewModels()

    private val adapterDomainStatus = BaseRecyclerAdapter()
    private val sharedCartViewModel: SharedCartViewModel by navGraphViewModels(R.id.main_graph)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUi()
        initAdapters()
        initObservers()
    }

    private fun initAdapters() {
        viewModel.onChangeCartState = { added, domainForm ->
            if (added)
                sharedCartViewModel.addItemCart(domainForm)
            else {
                sharedCartViewModel.removeItemCart(domainForm)
                showInfoToast(getString(R.string.domain_removed_from_cart))
            }
        }
        searchList.adapter = adapterDomainStatus
    }

    private fun initObservers() {
        viewModel.domainStatusResource.observe(viewLifecycleOwner) { resource ->
            progressBar.isVisible = resource is Resource.Loading
            errorText.isVisible = resource is Resource.Failure
            resource.onSuccess {
                val item =
                    (it as? DomainListItem)?.apply {
                        it.setInCart(sharedCartViewModel.checkInCart(it.getForm()))
                    }
                adapterDomainStatus.replaceElements(listOfNotNull(item))
                adapterDomainStatus.notifyDataSetChanged()
            }.onFailure {
                errorText.text = it ?: getString(R.string.errorTryRepeat)
                if (searchView.query.isNullOrBlank())
                    errorText.text = ""
                adapterDomainStatus.clearElements()
                adapterDomainStatus.notifyDataSetChanged()
            }
        }
        sharedCartViewModel.cartListResource.observe(viewLifecycleOwner) { resource ->
            resource.onSuccess {
                setCart(it)
                viewModel.saveCartList(it)
            }
        }
        viewModel.needUpdateCartList.observe(viewLifecycleOwner) {
            sharedCartViewModel.setCarList(it.toMutableList())
        }
    }

    private fun setCart(it: List<DomainForm>) {
        cartLayout.isVisible = it.count() > 0
        sumCartText.text =
            getString(R.string.rubs_form, it.sumBy { it.price.toInt() }.toDouble())
        buyBtn.text =
            if (it.count() > 1) getString(R.string.navigate_to_cart_form, it.count())
            else getString(R.string.buy)
        buyBtn.setOnClickListener(
            Navigation.createNavigateOnClickListener(
                if (it.count() > 1)
                    CheckDomainFragmentDirections.actionCheckDomainFragmentToCartFragment()
                else
                    CheckDomainFragmentDirections.actionCheckDomainFragmentToProfileFragment()
            )
        )
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

    private fun initUi() {
        checkBtn.setOnClickListener {
            searchView.showKeyboard()
        }

        searchView.setOnQueryTextFocusChangeListener { _, hasFocus ->
            nonePlaceHolder.isVisible = !hasFocus && searchView.query.isEmpty()
        }

        contactUsBtn.setOnClickListener {
            sendEmailIntent(contactUsBtn.text)
        }

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.inputSearchText.onNext(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.inputSearchText.onNext(newText)
                return true
            }
        })
    }

    private fun sendEmailIntent(email: CharSequence) {
        try {
            val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("mailto:$email")
            }
            startActivity(emailIntent)
        } catch (ex: ActivityNotFoundException) {
            Toast.makeText(requireContext(), R.string.noAppAvailable, Toast.LENGTH_SHORT)
                .show()
        }
    }
}