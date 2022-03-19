package com.rodrigoads.appphi.network.response

import com.rodrigoads.appphi.model.MyBalance

data class MyBalanceResponse(
    val amount: Int
)

fun MyBalanceResponse.toMyBalance(): MyBalance {
    return MyBalance(
        amount = this.amount
    )
}
