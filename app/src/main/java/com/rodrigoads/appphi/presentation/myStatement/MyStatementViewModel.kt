package com.rodrigoads.appphi.presentation.myStatement

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.rodrigoads.appphi.model.StatementItem
import com.rodrigoads.appphi.repositories.myStatementItems.MyStatementItemsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@HiltViewModel
class MyStatementViewModel @Inject constructor(
    private val myStatementItemsRepository: MyStatementItemsRepository
) : ViewModel() {

    val statementPagingData = MutableLiveData<PagingData<StatementItem>>()

    suspend fun myStatement() {
        val request = Pager(config = getPagingConfig()) {
            myStatementItemsRepository.getMyStatementDetails()
        }.flow
            .cachedIn(viewModelScope)

        request.collect {
            statementPagingData.postValue(it)
        }
    }

    private fun getPagingConfig(): PagingConfig {
        return PagingConfig(
            pageSize = 10
        )
    }
}