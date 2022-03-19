package com.rodrigoads.appphi.repositories.myBalance

import com.rodrigoads.appphi.local.DataStoreService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MyBalanceVisibilityRepositoryImpl @Inject constructor(
    private val dataStoreService: DataStoreService
) : MyBalanceVisibilityRepository {

    override fun getBalanceVisibility(): Flow<Boolean> {
        return dataStoreService.getDataStoreBalanceVisibility()
    }

    override suspend fun setBalanceVisibility(setupVisibility: Boolean) {
        dataStoreService.setDataStoreBalanceVisibility(setupVisibility)
    }
}