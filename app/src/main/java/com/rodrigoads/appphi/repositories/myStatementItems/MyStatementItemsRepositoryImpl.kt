package com.rodrigoads.appphi.repositories.myStatementItems

import androidx.paging.PagingSource
import com.rodrigoads.appphi.model.StatementItem
import com.rodrigoads.appphi.network.ApiService
import com.rodrigoads.appphi.paging.MyStatementItemPagingSource
import javax.inject.Inject

class MyStatementItemsRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : MyStatementItemsRepository {
    override fun getMyStatementDetails(): PagingSource<Int, StatementItem> {
        return MyStatementItemPagingSource(apiService)
    }

}