package com.rodrigoads.appphi.repositories.myBalance

import kotlinx.coroutines.flow.Flow

interface MyBalanceVisibilityRepository {

    fun getBalanceVisibility(): Flow<Boolean>

    suspend fun setBalanceVisibility(setupVisibility: Boolean)
}