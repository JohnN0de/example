package com.timeweb.checkdomain.data.net

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import com.timeweb.checkdomain.data.net.NetworkStateSource
import javax.inject.Inject

class NetworkStateDataSource @Inject constructor(
    private val context: Context
) : NetworkStateSource {

    override fun isNetworkAvailable(): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo: NetworkInfo? = connectivityManager.activeNetworkInfo
        return !(activeNetworkInfo == null || !activeNetworkInfo.isConnected)
    }
}