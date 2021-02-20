package com.timeweb.checkdomain.presentation.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import com.timeweb.checkdomain.R
import com.timeweb.checkdomain.presentation.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity() {

    override fun getLayoutRes() = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }

}