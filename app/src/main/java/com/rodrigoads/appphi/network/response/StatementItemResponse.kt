package com.rodrigoads.appphi.network.response

import com.rodrigoads.appphi.model.StatementItem


data class StatementItemResponse(
    val createdAt: String,
    val id: String,
    val amount: Int,
    val to: String?,
    val from: String?,
    val bankName: String?,
    val description: String,
    val tType: String,
)

fun StatementItemResponse.toStatementItem(): StatementItem {
    return StatementItem(
        createdAt = this.createdAt,
        id = this.id,
        amount = this.amount,
        to = this.to ?: "",
        description = this.description,
        tType = this.tType,
        bankName = this.bankName ?: "",
        from = this.from ?: ""
    )
}
