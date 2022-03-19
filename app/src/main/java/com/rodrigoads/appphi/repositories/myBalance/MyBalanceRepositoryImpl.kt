package com.rodrigoads.appphi.repositories.myBalance

import com.rodrigoads.appphi.R
import com.rodrigoads.appphi.model.MyBalance
import com.rodrigoads.appphi.network.ApiService
import com.rodrigoads.appphi.network.response.ResultStatus
import com.rodrigoads.appphi.network.response.toMyBalance
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MyBalanceRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : MyBalanceRepository {
    @Suppress("TooGenericExceptionCaught")
    override suspend fun getMyBalance(): Flow<ResultStatus<MyBalance?>> {
        return flow {
            emit(ResultStatus.Loading)
            try {
                val response = apiService.getBalance()
                if (response.isSuccessful) {
                    emit(ResultStatus.Success(data = response.body()?.toMyBalance()))
                }
            } catch (e: Exception) {
                emit(ResultStatus.Error(e))
            }
        }
    }
}
