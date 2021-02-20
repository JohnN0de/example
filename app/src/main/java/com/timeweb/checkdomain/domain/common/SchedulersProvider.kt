package com.timeweb.checkdomain.domain.common

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

interface SchedulersProvider {
    fun io(): Scheduler
    fun computation(): Scheduler
    fun main(): Scheduler
    fun newThread(): Scheduler
}

class SchedulersProviderImpl @Inject constructor() : SchedulersProvider {
    override fun io(): Scheduler = Schedulers.io()

    override fun computation(): Scheduler = Schedulers.computation()

    override fun main(): Scheduler = AndroidSchedulers.mainThread()

    override fun newThread(): Scheduler = Schedulers.newThread()
}
