package com.rodrigoads.appphi.presentation.myBalance

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rodrigoads.appphi.R
import com.rodrigoads.appphi.model.uiState.MyBalanceUiState
import com.rodrigoads.appphi.network.response.ResultStatus
import com.rodrigoads.appphi.repositories.myBalance.MyBalanceRepository
import com.rodrigoads.appphi.repositories.myBalance.MyBalanceVisibilityRepository
import com.rodrigoads.appphi.utils.currencyFormat
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyBalanceViewModel @Inject constructor(
    private val myBalanceRepository: MyBalanceRepository,
    private val myBalanceVisibilityRepository: MyBalanceVisibilityRepository
) : ViewModel() {

    val myBalance = MutableLiveData<MyBalanceUiState>()
    val getBalanceVisibility = MutableLiveData<Boolean>()

    fun myBalance() {
        viewModelScope.launch {
            myBalanceRepository.getMyBalance().collectLatest { resultStatus ->
                when (resultStatus) {
                    is ResultStatus.Success -> {
                        myBalance.postValue(MyBalanceUiState.Success(resultStatus.data?.amount?.currencyFormat()))
                    }
                    is ResultStatus.Error -> {
                        myBalance.postValue(MyBalanceUiState.Error(R.string.an_error_has_occurred))
                    }
                    is ResultStatus.Loading -> {
                        myBalance.postValue(MyBalanceUiState.Loading)
                    }
                }
            }
        }
    }

    fun getBalanceVisibility() {
        viewModelScope.launch {
            myBalanceVisibilityRepository.getBalanceVisibility().collect { visibility ->
                getBalanceVisibility.postValue(visibility)
            }
        }
    }

    fun setBalanceVisibility(setupVisibility: Boolean) {
        viewModelScope.launch {
            myBalanceVisibilityRepository.setBalanceVisibility(setupVisibility)
        }
    }
}