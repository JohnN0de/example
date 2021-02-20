package com.timeweb.checkdomain.presentation.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.timeweb.checkdomain.presentation.extension.hideKeyboard


abstract class BaseFragment<VM : BaseViewModel> : Fragment() {

    @LayoutRes
    protected abstract fun fragmentLayout(): Int

    var toolbarView: Toolbar? = null

    abstract val viewModel: VM

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(fragmentLayout(), container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolbarView?.let {
            initToolbar(it)
        }
    }

    override fun onPause() {
        super.onPause()
        hideKeyboard()
    }

    fun initToolbar(toolbar: Toolbar) {
        toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

}
