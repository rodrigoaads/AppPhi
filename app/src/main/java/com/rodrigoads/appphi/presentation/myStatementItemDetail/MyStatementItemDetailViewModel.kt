package com.rodrigoads.appphi.presentation.myStatementItemDetail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rodrigoads.appphi.model.MyStatementItemDetail
import com.rodrigoads.appphi.network.response.ResultStatus
import com.rodrigoads.appphi.repositories.myStatementItemDetail.MyStatementItemDetailRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyStatementItemDetailViewModel @Inject constructor(
    private val myStatementItemDetailRepository: MyStatementItemDetailRepository
) : ViewModel() {

    val myStatementItemDetail = MutableLiveData<MyStatementItemDetail?>()
    val loadMyStatementItemDetailRequest = MutableLiveData(true)
    val myStatementItemDetailErrorRequest = MutableLiveData<Boolean>()

    fun myStatementDetail(id: String, retry: Boolean = false) {
        if (retry) {
            loadMyStatementItemDetailRequest.postValue(retry)
        }
        viewModelScope.launch {
            myStatementItemDetailRepository.getStatementItemDetail(id).collect { resultStatus ->
                when (resultStatus) {
                    is ResultStatus.Success -> {
                        myStatementItemDetail.postValue(resultStatus.data)
                        loadMyStatementItemDetailRequest.postValue(false)
                    }
                    is ResultStatus.Error -> {
                        myStatementItemDetailErrorRequest.postValue(true)
                        loadMyStatementItemDetailRequest.postValue(false)
                    }
                    is ResultStatus.Loading -> {
                        loadMyStatementItemDetailRequest.postValue(true)
                    }
                }
            }
        }
    }
}