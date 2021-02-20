package com.timeweb.checkdomain.data.check

import com.timeweb.checkdomain.data.api.ApiDomain
import com.timeweb.checkdomain.domain.check.DomainRepository
import com.timeweb.checkdomain.domain.check.model.DomainStatus
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class DomainRepositoryImpl @Inject constructor(
    private val api: ApiDomain
) : DomainRepository {

    override fun checkDomain(domain: String): Single<DomainStatus> =
        api.checkDomain(domain)
            .map {
                it.asDomain()
            }

}