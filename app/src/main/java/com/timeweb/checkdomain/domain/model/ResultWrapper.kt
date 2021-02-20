package com.timeweb.checkdomain.domain.model

sealed class ResultWrapper<out T> {
    data class Success<out T>(val data: T?) : ResultWrapper<T>()
    data class Failure<out T>(val message: String? = null, val data: T? = null) : ResultWrapper<T>()
}

inline fun <T> ResultWrapper<T>.onSuccess(block: (T?) -> Unit): ResultWrapper<T> {
    if (this is ResultWrapper.Success) block(data)
    return this
}

inline fun <T> ResultWrapper<T>.onFailure(block: (message: String?, errorData: T?) -> Unit): ResultWrapper<T> {
    if (this is ResultWrapper.Failure) block(message, data)
    return this
}


sealed class ResultWrapper2<out T, out R> {
    data class Success<out T>(val data: T) : ResultWrapper2<T, Nothing>()
    data class Failure<out R>(val message: String? = null, val data: R? = null) : ResultWrapper2<Nothing, R>()
}

inline fun <T, R> ResultWrapper2<T, R>.onSuccess2(block: (T) -> Unit): ResultWrapper2<T, R> {
    if (this is ResultWrapper2.Success) block(data)
    return this
}

inline fun <T, R> ResultWrapper2<T, R>.onFailure2(block: (message: String?, errorData: R?) -> Unit): ResultWrapper2<T, R> {
    if (this is ResultWrapper2.Failure) block(message, data)
    return this
}