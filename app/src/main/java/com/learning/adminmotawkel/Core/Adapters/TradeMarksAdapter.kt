package com.learning.adminmotawkel.Core.Adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.learning.adminmotawkel.Domain.models.product.TradeMarksModel
import com.learning.adminmotawkel.databinding.ItemTradeMarksBinding

class TradeMarksAdapter(
    private val tradeMarks: MutableList<TradeMarksModel>,
    private val listener: OnTradeMarkClickListener
) : RecyclerView.Adapter<TradeMarksAdapter.TradeMarkViewHolder>() {

    inner class TradeMarkViewHolder(val binding: ItemTradeMarksBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener.onTradeMarkClick(tradeMarks[position])
                }
            }
        }
    }

    interface OnTradeMarkClickListener {
        fun onTradeMarkClick(tradeMark: TradeMarksModel)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TradeMarkViewHolder {
        val binding = ItemTradeMarksBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TradeMarkViewHolder(binding)
    }

    override fun getItemCount(): Int = tradeMarks.size

    override fun onBindViewHolder(holder: TradeMarkViewHolder, position: Int) {
        val tradeMark = tradeMarks[position]
        Glide.with(holder.itemView.context)
            .load(tradeMark.tradeMarkImage)
            .into(holder.binding.tradeMarksImage)
    }

    fun updateTradeMarks(newList: List<TradeMarksModel>) {
        tradeMarks.clear()
        tradeMarks.addAll(newList)
        notifyDataSetChanged()
    }

    fun addTradeMark(tradeMark: TradeMarksModel) {
        tradeMarks.add(0, tradeMark)
        notifyItemInserted(0)
    }
}
