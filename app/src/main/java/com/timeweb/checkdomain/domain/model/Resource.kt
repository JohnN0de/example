package com.timeweb.checkdomain.domain.model

sealed class Resource<out T> {
    data class Success<out T>(val data: T) : Resource<T>()
    data class Loading<out T>(val loadingTitle: String? = null) : Resource<T>()
    data class Failure<out T>(val message: String? = null) : Resource<T>()
}

inline fun <T> Resource<T>.onSuccess(block: (T) -> Unit): Resource<T> {
    if (this is Resource.Success) block(data)
    return this
}

inline fun <T> Resource<T>.onFailure(block: (String?) -> Unit): Resource<T> {
    if (this is Resource.Failure) block(message)
    return this
}

inline fun <T> Resource<T>.onLoading(block: (String?) -> Unit): Resource<T> {
    if (this is Resource.Loading) block(loadingTitle)
    return this
}
