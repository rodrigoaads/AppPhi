package com.rodrigoads.appphi.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.rodrigoads.appphi.model.StatementItem
import com.rodrigoads.appphi.network.ApiService
import com.rodrigoads.appphi.network.response.toStatementItem

class MyStatementItemPagingSource(
    private val apiService: ApiService
) : PagingSource<Int, StatementItem>() {

    @Suppress("TooGenericExceptionCaught")
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, StatementItem> {
        return try {
            val offset = params.key ?: 0
            val response = apiService.getStatement(LIMIT, offset)

            LoadResult.Page(
                data = response.items.map { it.toStatementItem() },
                prevKey = null,
                nextKey = if (response.items.isNotEmpty()) {
                    offset + NEXT_OFFSET
                } else null
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, StatementItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(LIMIT) ?: anchorPage?.nextKey?.minus(LIMIT)
        }
    }

    companion object {
        const val LIMIT = 10
        const val NEXT_OFFSET = 1
    }
}