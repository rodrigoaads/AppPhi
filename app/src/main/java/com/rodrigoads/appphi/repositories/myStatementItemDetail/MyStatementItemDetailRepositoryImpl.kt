package com.rodrigoads.appphi.repositories.myStatementItemDetail

import com.rodrigoads.appphi.model.MyStatementItemDetail
import com.rodrigoads.appphi.network.ApiService
import com.rodrigoads.appphi.network.response.ResultStatus
import com.rodrigoads.appphi.network.response.toMyStatementItemDetail
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MyStatementItemDetailRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : MyStatementItemDetailRepository {

    @Suppress("TooGenericExceptionCaught")
    override suspend fun getStatementItemDetail(id: String): Flow<ResultStatus<MyStatementItemDetail?>> {
        return flow {
            emit(ResultStatus.Loading)
            try {
                val response = apiService.getStatementItemDetail(id)
                if (response.isSuccessful) {
                    emit(ResultStatus.Success(data = response.body()?.toMyStatementItemDetail()))
                }
            } catch (e: Exception) {
                emit(ResultStatus.Error(throwable = e))
            }
        }
    }

}