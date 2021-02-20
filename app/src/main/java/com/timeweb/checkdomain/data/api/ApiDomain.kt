package com.timeweb.checkdomain.data.api

import com.timeweb.checkdomain.data.check.model.DomainStatusRemote
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiDomain {

    @GET("v2/whois/{domain}")
    fun checkDomain(
        @Path("domain") domain: String,
        @Query("with_answer") withAnswer: Boolean = true
    ): Single<DomainStatusRemote>
}