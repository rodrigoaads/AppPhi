package com.rodrigoads.appphi.presentation.myStatement

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.rodrigoads.appphi.databinding.ItemMyStatementLoadMoreStateBinding

class MyStatementLoadMoreStateViewHolder(
    itemMyStatementLoadMoreStateBinding: ItemMyStatementLoadMoreStateBinding,
    retry: () -> Unit
) : RecyclerView.ViewHolder(itemMyStatementLoadMoreStateBinding.root) {

    private val binding = ItemMyStatementLoadMoreStateBinding.bind(itemView)
    private val progressBarLoadMoreState = binding.progressBarErrorLoadMoreState
    private val textViewErrorLoadMoreState = binding.textViewErrorLoadMoreState.also {
        it.setOnClickListener {
            retry()
        }
    }

    fun bind(loadState: LoadState) {
        progressBarLoadMoreState.isVisible = loadState is LoadState.Loading
        textViewErrorLoadMoreState.isVisible = loadState is LoadState.Error
    }

    companion object {
        fun create(parent: ViewGroup, retry: () -> Unit): MyStatementLoadMoreStateViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val itemBinding = ItemMyStatementLoadMoreStateBinding.inflate(inflater, parent, false)
            return MyStatementLoadMoreStateViewHolder(itemBinding, retry)
        }
    }
}