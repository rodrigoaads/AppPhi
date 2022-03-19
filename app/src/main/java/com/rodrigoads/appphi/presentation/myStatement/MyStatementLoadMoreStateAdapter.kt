package com.rodrigoads.appphi.presentation.myStatement

import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter

class MyStatementLoadMoreStateAdapter(
    private val retry: () -> Unit
) : LoadStateAdapter<MyStatementLoadMoreStateViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): MyStatementLoadMoreStateViewHolder {
        return MyStatementLoadMoreStateViewHolder.create(parent, retry)
    }

    override fun onBindViewHolder(
        holder: MyStatementLoadMoreStateViewHolder,
        loadState: LoadState
    ) {
        holder.bind(loadState)
    }
}