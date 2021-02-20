package com.timeweb.checkdomain.data.net

interface NetworkStateSource {
    fun isNetworkAvailable(): Boolean
}
