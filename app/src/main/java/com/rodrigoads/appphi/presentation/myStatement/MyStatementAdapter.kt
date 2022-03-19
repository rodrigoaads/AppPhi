package com.rodrigoads.appphi.presentation.myStatement

import android.content.Context
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.rodrigoads.appphi.model.StatementItem

class MyStatementAdapter(
    private val context: Context,
    private val clickItem: (id: String) -> Unit
) : PagingDataAdapter<StatementItem, MyStatementViewHolder>(diffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyStatementViewHolder {
        return MyStatementViewHolder.create(parent, clickItem, context)
    }

    override fun onBindViewHolder(holder: MyStatementViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<StatementItem>() {
            override fun areItemsTheSame(
                oldItem: StatementItem,
                newItem: StatementItem
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: StatementItem,
                newItem: StatementItem
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}