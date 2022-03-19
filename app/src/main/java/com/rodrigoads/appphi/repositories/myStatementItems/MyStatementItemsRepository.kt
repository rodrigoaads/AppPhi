package com.rodrigoads.appphi.repositories.myStatementItems

import androidx.paging.PagingSource
import com.rodrigoads.appphi.model.StatementItem

interface MyStatementItemsRepository {
    fun getMyStatementDetails(): PagingSource<Int, StatementItem>
}