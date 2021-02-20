package com.timeweb.checkdomain.presentation.cart

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.timeweb.checkdomain.domain.check.model.DomainForm
import com.timeweb.checkdomain.presentation.delegate.GsonPreference
import com.timeweb.checkdomain.presentation.delegate.StringPreference
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CartDataStore @Inject constructor(@ApplicationContext context: Context, gson: Gson) {

    companion object {
        const val CART_STORE_KEY = "cartDataStore"
    }

    private val sharedPreferences =
        context.getSharedPreferences(CART_STORE_KEY, Context.MODE_PRIVATE)

    var test: String? by StringPreference(
        sharedPreferences
    )

    var cartList: List<DomainForm>? by GsonPreference(
        sharedPreferences, gson,
        object : TypeToken<List<DomainForm>>() {}.type
    )
}