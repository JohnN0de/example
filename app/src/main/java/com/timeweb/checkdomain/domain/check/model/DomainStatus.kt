package com.timeweb.checkdomain.domain.check.model

data class DomainStatus(
    val answer: String?,
    val extended: Any?,
    val free: Boolean,
    val fromFallback: Boolean,
    val premium: Boolean,
    val requested: Boolean,
    val server : String?,
)
