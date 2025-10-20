package com.learning.adminmotawkel.Presentation.Section

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
import androidx.navigation.fragment.findNavController
import com.learning.adminmotawkel.Core.Adapters.ProductsAdapter
import com.learning.adminmotawkel.Core.Helpers.showLogs
import com.learning.adminmotawkel.Core.UiState
import com.learning.adminmotawkel.Domain.ViewModels.ProductVM
import com.learning.adminmotawkel.Domain.models.product.Category
import com.learning.adminmotawkel.R
import com.learning.adminmotawkel.databinding.FragmentSectionBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.UUID

@AndroidEntryPoint
class AddProduct : Fragment() {
    private val viewModel: ProductVM by viewModels()

    private var _binding: FragmentSectionBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: ProductsAdapter
    private val productsList = mutableListOf<Category>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSectionBinding.inflate(inflater, container, false)

        initTheView()
        lifecycleScope.launch {
            viewModel.getSections.collect { state ->
                when (state) {
                    is UiState.Loading -> { /* show progress */ }
                    is UiState.Success -> {
                        adapter.updateProducts(state.data)

                    }
                    is UiState.Error -> { showLogs("ErrrorrrR?RR ${state.error}")}
                }
            }
        }


        lifecycleScope.launch{
    viewModel.getSections.collect { state ->
        when (state) {
            is UiState.Loading -> {
            }
            is UiState.Success -> {
                adapter.updateProducts(state.data)
            }
            is UiState.Error -> {
                showLogs("Error loading sections: ${state.error}")
            }
        }
    }
}
        return binding.root
    }
    private fun initTheView(){
        lifecycleScope.launch {

        viewModel.loadSections()

        }
        adapter = ProductsAdapter(productsList , object  : ProductsAdapter.OnProductListener{
            override fun onProductClick(category: Category) {
                val bundle = Bundle().apply {
                    putParcelable("category", category)
                }
                findNavController().navigate(R.id.tradeMarks,bundle)

            }

        })
        showLogs("Show list of recee ${productsList.size}")
        binding.productsRecycler.adapter = adapter

        binding.addProductFab.setOnClickListener {
            showAddProductDialog()
        }
    }
    @SuppressLint("MissingInflatedId")
    private fun showAddProductDialog() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_add_product, null)
        val nameEditText = dialogView.findViewById<EditText>(R.id.productNameInput)
        val imageView = dialogView.findViewById<ImageView>(R.id.sectionImage_Pic)

        AlertDialog.Builder(requireContext())
            .setTitle("إضافة منتج جديد")
            .setView(dialogView)
            .setPositiveButton("إضافة") { dialog, _ ->
                val name = nameEditText.text.toString()
//                val image = imageEditText.text.toString()

                if (name.isNotEmpty()) {
                    val product = Category(
                        id = UUID.randomUUID().toString(),
                        sectionImage = "",
                        sectionName = name
                    )
                    viewModel.addSection(product)
                    adapter.addProduct(Category(id=name, sectionImage = "", sectionName = "" ) )
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
