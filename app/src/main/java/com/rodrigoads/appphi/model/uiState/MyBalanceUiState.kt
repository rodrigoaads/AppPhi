package com.rodrigoads.appphi.model.uiState

import androidx.annotation.StringRes

sealed class MyBalanceUiState {
    object Loading : MyBalanceUiState()
    data class Success(val amount : String?) : MyBalanceUiState()
    data class Error(@StringRes val message: Int) : MyBalanceUiState()
}
