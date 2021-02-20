package com.timeweb.checkdomain.data.net.interceptors

import com.timeweb.checkdomain.data.net.NetworkStateSource
import com.timeweb.checkdomain.data.net.NoInternetException
import com.timeweb.checkdomain.data.net.interceptors.NoInternetInterceptorRepository
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class NoInternetInterceptorRepositoryImpl @Inject constructor(
    private val networkSource: NetworkStateSource
) : NoInternetInterceptorRepository {
    override fun intercept(chain: Interceptor.Chain): Response {
        if (!networkSource.isNetworkAvailable()) {
            throw NoInternetException()
        }
        return chain.proceed(chain.request())
    }
}
