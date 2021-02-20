package com.timeweb.checkdomain.domain.check

import com.timeweb.checkdomain.domain.check.model.DomainStatus
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

interface DomainUseCase {

    fun checkDomain(domain: String): Single<DomainStatus>
}

class DomainUseCaseImpl @Inject constructor(
    private val domainRepository: DomainRepository
) : DomainUseCase {

    override fun checkDomain(domain: String): Single<DomainStatus> =
        domainRepository.checkDomain(domain)

}