package com.rodrigoads.appphi.model

data class StatementItem(
    val createdAt: String,
    val id: String,
    val amount: Int,
    val to: String,
    val from: String,
    val bankName: String,
    val description: String,
    val tType: String,
)