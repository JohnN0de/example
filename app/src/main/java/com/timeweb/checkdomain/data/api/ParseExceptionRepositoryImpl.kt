package com.timeweb.checkdomain.data.api

import android.content.Context
import com.google.gson.Gson
import com.timeweb.checkdomain.R
import com.timeweb.checkdomain.data.net.HttpBodyException
import com.timeweb.checkdomain.domain.api.ParseExceptionRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.util.concurrent.TimeoutException
import javax.inject.Inject

class ParseExceptionRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val gson: Gson
) : ParseExceptionRepository {

    override fun parseException(throwable: Throwable): String {
        var message = context.getString(R.string.unknown_error)
        val result = parseNetworkException(throwable)
        if (result != 0) {
            message = context.getString(result)
        } else {
            parseExceptionFromBody(throwable)?.let { res ->
                message = context.getString(res)
            }

        }
        return message
    }

    private fun parseExceptionFromBody(throwable: Throwable): Int? {
        try {
            (throwable as HttpException).response()?.errorBody()?.string()?.let {
                gson.fromJson(it, HttpBodyException::class.java)?.code?.let { error ->
                    return when (error) {
                        "bad_domain_name" -> R.string.bad_domain_name
                        else -> null
                    }
                }
            }
        } catch (e: Exception) {

        }
        return null
    }


    private fun parseNetworkException(throwable: Throwable): Int {
        var messageResId = 0
        if (checkNoInternet(throwable)) {
            messageResId = R.string.no_internet
        } else if (checkNetworkException(throwable)) {
            messageResId = R.string.server_error
        }
        return messageResId
    }


    private fun checkNoInternet(throwable: Throwable): Boolean {
        return throwable is UnknownHostException ||
                throwable is SocketTimeoutException ||
                throwable is ConnectException
    }

    private fun checkNetworkException(throwable: Throwable): Boolean {
        return throwable is UnknownHostException ||
                throwable is TimeoutException ||
                throwable is SocketException ||
                throwable is SocketTimeoutException
    }
}