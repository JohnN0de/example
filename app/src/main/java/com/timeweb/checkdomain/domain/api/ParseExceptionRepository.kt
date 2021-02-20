package com.timeweb.checkdomain.domain.api

interface ParseExceptionRepository {
    fun parseException(throwable: Throwable): String
}
