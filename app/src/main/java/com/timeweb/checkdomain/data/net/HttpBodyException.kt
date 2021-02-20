package com.timeweb.checkdomain.data.net

import com.google.gson.annotations.SerializedName

data class HttpBodyException(
    @SerializedName("error_code") val code: String?,
    @SerializedName("error_msg") val msg: String?
)