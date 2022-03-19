package com.rodrigoads.appphi.network

import com.rodrigoads.appphi.network.response.MyBalanceResponse
import com.rodrigoads.appphi.network.response.MyStatementDetailsResponse
import com.rodrigoads.appphi.network.response.MyStatementResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("myBalance")
    suspend fun getBalance(): Response<MyBalanceResponse>

    @GET("myStatement/{limit}/{offset}")
    suspend fun getStatement(
        @Path("limit") limit: Int,
        @Path("offset") offset: Int,
    ): MyStatementResponse

    @GET("myStatement/detail/{id}/")
    suspend fun getStatementItemDetail(
        @Path("id") id: String,
    ): Response<MyStatementDetailsResponse>

}