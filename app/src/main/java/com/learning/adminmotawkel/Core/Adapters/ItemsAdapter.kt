package com.learning.adminmotawkel.Core.Adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.learning.adminmotawkel.Domain.models.product.ItemModel
import com.learning.adminmotawkel.databinding.ItemItemsBinding
import com.learning.adminmotawkel.databinding.ItemProductBinding

class ItemsAdapter(
    private val items: MutableList<ItemModel>
) : RecyclerView.Adapter<ItemsAdapter.ItemViewHolder>() {

    inner class ItemViewHolder(val binding:ItemItemsBinding) :
        RecyclerView.ViewHolder(binding.root) {
    }

    interface OnItemClickListener {
        fun onItemClick(item: ItemModel)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = ItemItemsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = items[position]
        holder.binding.itemName.text = item.itemName
        Glide.with(holder.itemView.context)
            .load(item.itemImage)
            .into(holder.binding.itemImage)
    }

    fun updateItems(newList: List<ItemModel>) {
        items.clear()
        items.addAll(newList)
        notifyDataSetChanged()
    }

    fun addItem(item: ItemModel) {
        items.add(0, item)
        notifyItemInserted(0)
    }
}
