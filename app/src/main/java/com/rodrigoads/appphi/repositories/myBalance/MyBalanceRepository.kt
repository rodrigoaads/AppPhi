package com.rodrigoads.appphi.repositories.myBalance

import com.rodrigoads.appphi.model.MyBalance
import com.rodrigoads.appphi.network.response.ResultStatus
import kotlinx.coroutines.flow.Flow

interface MyBalanceRepository {
    suspend fun getMyBalance(): Flow<ResultStatus<MyBalance?>>
}