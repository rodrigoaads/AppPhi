package com.rodrigoads.appphi.model

data class MyStatementItemDetail(
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
