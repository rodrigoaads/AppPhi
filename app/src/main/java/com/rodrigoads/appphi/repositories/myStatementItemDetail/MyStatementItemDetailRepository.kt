package com.rodrigoads.appphi.repositories.myStatementItemDetail

import com.rodrigoads.appphi.model.MyStatementItemDetail
import com.rodrigoads.appphi.network.response.ResultStatus
import kotlinx.coroutines.flow.Flow

interface MyStatementItemDetailRepository {
    suspend fun getStatementItemDetail(id: String): Flow<ResultStatus<MyStatementItemDetail?>>
}