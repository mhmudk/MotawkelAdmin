package com.learning.adminmotawkel.Core.Adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.learning.adminmotawkel.Core.Helpers.showLogs
import com.learning.adminmotawkel.Domain.models.product.Category
import com.learning.adminmotawkel.databinding.ItemProductBinding

class ProductsAdapter(private val products: MutableList<Category>,  val listener: OnProductListener
) :
    RecyclerView.Adapter<ProductsAdapter.ProductViewHolder>() {

    inner class ProductViewHolder(val binding: ItemProductBinding) :
        RecyclerView.ViewHolder(binding.root){
        init {
            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener.onProductClick(products[position])
                }
            }
        }
        }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding)
    }
    interface  OnProductListener{
        fun onProductClick(category: Category)
    }
    override fun getItemCount(): Int = products.size

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = products[position]
        showLogs("show the data ${product.sectionName}")
        holder.binding.productName.text = product.sectionName
        Glide.with(holder.itemView.context)
            .load(product.sectionImage)
            .into(holder.binding.productImage)
    }

    fun addProduct(product: Category) {
        products.add(0, product)
        notifyItemInserted(0)
    }
    fun updateProducts(newList: List<Category>) {
        products.clear()
        products.addAll(newList)
        notifyDataSetChanged()
    }

    }
