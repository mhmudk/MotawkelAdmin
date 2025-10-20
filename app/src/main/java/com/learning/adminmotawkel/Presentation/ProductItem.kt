package com.learning.adminmotawkel.Presentation

import android.widget.Button
import android.widget.CheckBox
import com.learning.adminmotawkel.Domain.ViewModels.ProductVM
import com.learning.adminmotawkel.Domain.models.product.ItemModel
import com.learning.adminmotawkel.Domain.models.product.TradeMarksModel


import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.learning.adminmotawkel.Core.Adapters.ItemsAdapter
import com.learning.adminmotawkel.Core.Helpers.showLogs
import com.learning.adminmotawkel.Core.UiState
import com.learning.adminmotawkel.R
import com.learning.adminmotawkel.databinding.FragmentItemBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.UUID

@AndroidEntryPoint
class ProductItem : Fragment() {

    private val viewModel: ProductVM by viewModels()
    private var _binding: FragmentItemBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: ItemsAdapter
    private val itemsList = mutableListOf<ItemModel>()

    private var categoryId: String? = null
    private var tradeMarkId: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentItemBinding.inflate(inflater, container, false)

        // Get the TradeMark and Category ID from arguments
        val tradeMark = arguments?.getParcelable<TradeMarksModel>("tradeMark")
        categoryId = arguments?.getString("categoryId")
        tradeMarkId = tradeMark?.id

        showLogs("TradeMark ID: $tradeMarkId | Category ID: $categoryId")

        initTheView()

        // Observe Items
        lifecycleScope.launch {
            viewModel.getItems.collect { state ->
                when (state) {
                    is UiState.Loading -> { /* Show progress bar */
                    }

                    is UiState.Success -> adapter.updateItems(state.data)
                    is UiState.Error -> showLogs("Error loading items: ${state.error}")
                }
            }
        }

        return binding.root
    }

    private fun initTheView() {
        lifecycleScope.launch {
            if (categoryId != null && tradeMarkId != null) {
                viewModel.loadItems(categoryId!!, tradeMarkId!!)
            }
        }

        adapter = ItemsAdapter(itemsList)
        binding.itemsRecycler.adapter = adapter

        binding.addItemsFab.setOnClickListener {
            showAddItemDialog()
        }
    }

    @SuppressLint("MissingInflatedId")
    private fun showAddItemDialog() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_add_item, null)
        val imageItem = dialogView.findViewById<ImageView>(R.id.image_Item)
        val itemName = dialogView.findViewById<EditText>(R.id.item_name)
        val allPrice = dialogView.findViewById<EditText>(R.id.all_price)
        val singlePrice = dialogView.findViewById<EditText>(R.id.single_price)
        val hasOffer = dialogView.findViewById<CheckBox>(R.id.has_offer_or_not)
        val isBestSeller = dialogView.findViewById<CheckBox>(R.id.best_seller)
        val isOfferForYou = dialogView.findViewById<CheckBox>(R.id.offer_for_you)
        val singlePriceName = dialogView.findViewById<EditText>(R.id.single_price_name)
        val allPriceName = dialogView.findViewById<EditText>(R.id.all_price_name)
        val addItem = dialogView.findViewById<Button>(R.id.add_item)

        AlertDialog.Builder(requireContext())
            .setTitle("إضافة منتج جديد")
            .setView(dialogView)
            .setPositiveButton("إضافة") { dialog, _ ->
                if (categoryId != null && tradeMarkId != null) {
                    val item = ItemModel(
                        id = UUID.randomUUID().toString(),
                        itemImage = "",
                        itemName = itemName.text.toString(),
                        singlePrice = singlePrice.text.toString(),
                        allPrice = allPrice.text.toString(),
                        hasOffer = hasOffer.isChecked,
                        isBestSeller = isBestSeller.isChecked,
                        offerForYou = isOfferForYou.isChecked,
                        singlePriceName = singlePriceName.text.toString(),
                        allPriceName = allPriceName.text.toString()
                    )
                    viewModel.addItems(item, categoryId!!, tradeMarkId!!)
                    adapter.addItem(item)
                }
                dialog.dismiss()
            }
            .setNegativeButton("إلغاء") { dialog, _ -> dialog.dismiss() }
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
