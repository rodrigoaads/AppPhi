package com.rodrigoads.appphi.di

import com.rodrigoads.appphi.repositories.myBalance.MyBalanceRepository
import com.rodrigoads.appphi.repositories.myBalance.MyBalanceRepositoryImpl
import com.rodrigoads.appphi.repositories.myBalance.MyBalanceVisibilityRepository
import com.rodrigoads.appphi.repositories.myBalance.MyBalanceVisibilityRepositoryImpl
import com.rodrigoads.appphi.repositories.myStatementItemDetail.MyStatementItemDetailRepository
import com.rodrigoads.appphi.repositories.myStatementItemDetail.MyStatementItemDetailRepositoryImpl
import com.rodrigoads.appphi.repositories.myStatementItems.MyStatementItemsRepository
import com.rodrigoads.appphi.repositories.myStatementItems.MyStatementItemsRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    fun bindMyStatementItems(
        myStatementItemsRepositoryImpl: MyStatementItemsRepositoryImpl
    ): MyStatementItemsRepository

    @Binds
    fun bindMyBalance(
        myBalanceRepositoryImpl: MyBalanceRepositoryImpl
    ): MyBalanceRepository

    @Binds
    fun bindMyBalanceVisibility(
        myBalanceVisibilityRepositoryImpl: MyBalanceVisibilityRepositoryImpl
    ): MyBalanceVisibilityRepository

    @Binds
    fun bindMyStatementItemDetail(
        myStatementItemDetailRepositoryImpl: MyStatementItemDetailRepositoryImpl
    ): MyStatementItemDetailRepository
}