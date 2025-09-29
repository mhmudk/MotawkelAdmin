package com.learning.adminmotawkel.Presentation.Section

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.learning.adminmotawkel.Core.Helpers.showLogs
import com.learning.adminmotawkel.Core.UiState
import com.learning.adminmotawkel.Domain.ViewModels.SectionVM
import com.learning.adminmotawkel.Domain.models.product.Product
import com.learning.adminmotawkel.databinding.FragmentSectionBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.UUID

@AndroidEntryPoint
class AddProduct : Fragment() {

    private var _binding: FragmentSectionBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SectionVM by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSectionBinding.inflate(inflater, container, false)

        lifecycleScope.launch(Dispatchers.Main) {
            viewModel.addSections.collect { state ->
                when (state) {
                    is UiState.Loading -> {
                        binding.loading.visibility = View.VISIBLE
                        showLogs("item loading ")

                    }

                    is UiState.Success -> {
                        binding.loading.visibility = View.GONE
//                            viewModel.loadSections()
                        showLogs("item added successfully ")

                    }

                    is UiState.Error -> {
                        binding.loading.visibility = View.GONE
                        showLogs("item error ${state.error} ")

                    }
                }

            }
        }

binding.addItem.setOnClickListener {
    showLogs("addItemClicked")
    viewModel.addSection(buildProductRequest())
}
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun buildProductRequest(): Product {
        return Product(
            id = UUID.randomUUID().toString(),
            sectionImage = "Section Image",
            sectionName = binding.sectionName.text.toString(),
            tradeMarkImage = "TradeMarksImage",
            itemImage = "item image",
            itemName = binding.itemName.text.toString(),
            singlePrice = binding.singlePrice.text.toString(),
            allPrice = binding.allPrice.text.toString(),
            hasOffer = binding.hasOfferOrNot.isChecked,
            isBestSeller = binding.bestSeller.isChecked,
            offerForYou = binding.offerForYou.isChecked,
        )
    }
}