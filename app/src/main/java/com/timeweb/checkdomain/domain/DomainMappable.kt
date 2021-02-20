package com.timeweb.checkdomain.domain

interface DomainMappable<T> {
    fun asDomain(): T
}