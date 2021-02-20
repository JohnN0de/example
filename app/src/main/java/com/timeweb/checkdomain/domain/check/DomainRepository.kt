package com.timeweb.checkdomain.domain.check

import com.timeweb.checkdomain.domain.check.model.DomainStatus
import io.reactivex.rxjava3.core.Single

interface DomainRepository {

    fun checkDomain(domain: String): Single<DomainStatus>

}