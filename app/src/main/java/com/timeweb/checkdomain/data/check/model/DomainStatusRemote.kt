package com.timeweb.checkdomain.data.check.model

import com.google.gson.annotations.SerializedName
import com.timeweb.checkdomain.domain.DomainMappable
import com.timeweb.checkdomain.domain.check.model.DomainStatus

data class DomainStatusRemote(
    @SerializedName("answer") val answer: String?,
    @SerializedName("extended") val extended: Any?,
    @SerializedName("free") val free: Boolean?,
    @SerializedName("from_fallback") val fromFallback: Boolean?,
    @SerializedName("premium") val premium: Boolean?,
    @SerializedName("requested") val requested: Boolean?,
    @SerializedName("server") val server : String?,
): DomainMappable<DomainStatus> {
    override fun asDomain(): DomainStatus = DomainStatus(
        answer = answer,
        extended = extended,
        free = free ?: false,
        fromFallback = fromFallback ?: false,
        premium = premium ?: false,
        requested = requested ?: false,
        server = server
    )

}
