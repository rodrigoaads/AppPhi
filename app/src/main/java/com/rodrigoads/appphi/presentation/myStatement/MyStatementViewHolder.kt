package com.rodrigoads.appphi.presentation.myStatement

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rodrigoads.appphi.R
import com.rodrigoads.appphi.databinding.StatementItemLayoutBinding
import com.rodrigoads.appphi.model.StatementItem
import com.rodrigoads.appphi.utils.currencyFormat
import com.rodrigoads.appphi.utils.dateAndTimeFormat

class MyStatementViewHolder(
    private val statementItemLayoutBinding: StatementItemLayoutBinding,
    private val clickItem: (id: String) -> Unit,
    private val context: Context
) : RecyclerView.ViewHolder(statementItemLayoutBinding.root) {
    fun bind(statementItem: StatementItem) {

        statementItemLayoutBinding.textViewStatementDescription.text = statementItem.description

        statementItemLayoutBinding.textViewStatementAmount.text =
            statementItem.amount.run {
                if (statementItem.tType.endsWith(context.getString(R.string.out))) "-${currencyFormat()}" else
                    currencyFormat()
            }

        statementItemLayoutBinding.textViewStatementDate.text =
            statementItem.createdAt.dateAndTimeFormat(true)

        statementItemLayoutBinding.textViewClientName.text = statementItem.to.let {
            if (statementItem.to.isNotEmpty()) it else context.getString(R.string.your_account)
        }

        val getTypePix = statementItem.tType.substring(0..2)


        if (getTypePix == context.getString(R.string.pix_out)) {
            statementItemLayoutBinding.textViewStatementPix.visibility = View.VISIBLE
            statementItemLayoutBinding.statementLayout.setBackgroundResource(R.color.color_light_gray)
        } else {
            statementItemLayoutBinding.textViewStatementPix.visibility = View.INVISIBLE
            statementItemLayoutBinding.statementLayout.setBackgroundResource(R.color.white)
        }

        statementItemLayoutBinding.root.setOnClickListener {
            clickItem(statementItem.id)
        }

    }

    companion object {
        fun create(
            parent: ViewGroup,
            clickItem: (id: String) -> Unit,
            context: Context
        ): MyStatementViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val itemBinding = StatementItemLayoutBinding.inflate(inflater, parent, false)
            return MyStatementViewHolder(itemBinding, clickItem, context)
        }
    }
}