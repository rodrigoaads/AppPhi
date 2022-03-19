package com.rodrigoads.appphi.network.response

import com.rodrigoads.appphi.model.MyStatementItemDetail

data class MyStatementDetailsResponse(
    val amount: Int,
    val id: String,
    val authentication: String,
    val tType: String,
    val createdAt: String,
    val to: String?,
    val from: String?,
    val bankName: String?,
    val description: String
)

fun MyStatementDetailsResponse.toMyStatementItemDetail(): MyStatementItemDetail {
    return MyStatementItemDetail(
        amount = this.amount,
        id = this.id,
        authentication = this.authentication,
        tType = this.tType,
        createdAt = this.createdAt,
        to = this.to,
        from = this.from,
        bankName = this.bankName,
        description = this.description
    )
}
