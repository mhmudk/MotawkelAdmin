package com.learning.adminmotawkel.Presentation.TradeMarks

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
import com.learning.adminmotawkel.Core.Adapters.TradeMarksAdapter
import com.learning.adminmotawkel.Core.Helpers.showLogs
import com.learning.adminmotawkel.Core.UiState
import com.learning.adminmotawkel.Domain.ViewModels.ProductVM
import com.learning.adminmotawkel.Domain.models.product.Category
import com.learning.adminmotawkel.Domain.models.product.TradeMarksModel
import com.learning.adminmotawkel.R
import com.learning.adminmotawkel.databinding.FragmentTradeMarksBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.UUID

@AndroidEntryPoint
class TradeMarks : Fragment() {

    private val viewModel: ProductVM by viewModels()
    private var _binding: FragmentTradeMarksBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: TradeMarksAdapter
    private val tradeMarksList = mutableListOf<TradeMarksModel>()

    private var categoryId: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTradeMarksBinding.inflate(inflater, container, false)

        val category = arguments?.
        getParcelable<Category>("category")
        categoryId = category?.id
        showLogs("Category ID received: $categoryId")

        initTheView()

        // Collect tradeMarks state
        lifecycleScope.launch {
            viewModel.getTradeMarks.collect { state ->
                when (state) {
                    is UiState.Loading -> { /* Show progress bar */ }
                    is UiState.Success -> adapter.updateTradeMarks(state.data)
                    is UiState.Error -> showLogs("Error loading tradeMarks: ${state.error}")
                }
            }
        }

        return binding.root
    }

    private fun initTheView() {
        lifecycleScope.launch {
            viewModel.loadTradeMarks(categoryId!!)
        }

        adapter = TradeMarksAdapter(tradeMarksList, object : TradeMarksAdapter.OnTradeMarkClickListener {
            override fun onTradeMarkClick(tradeMark: TradeMarksModel) {
                val bundle = Bundle().apply {
                    putParcelable("tradeMark", tradeMark)
                    putString("categoryId", categoryId)
                }
                findNavController().navigate(R.id.item, bundle)
            }
        })

        binding.tradeMarksRecycler.adapter = adapter

        binding.addtradeMarksFab.setOnClickListener {
            showAddTradeMarkDialog()
        }
    }

    @SuppressLint("MissingInflatedId")
    private fun showAddTradeMarkDialog() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_add_trademark, null)
        val nameEditText = dialogView.findViewById<ImageView>(R.id.tradeMarks_Pic)

        AlertDialog.Builder(requireContext())
            .setTitle("إضافة علامة تجارية جديدة")
            .setView(dialogView)
            .setPositiveButton("إضافة") { dialog, _ ->

                if (categoryId != null) {
                    val tradeMark = TradeMarksModel(
                        id = UUID.randomUUID().toString(),
                      tradeMarkImage = "weeeeeee"
                    )
                    viewModel.addTradeMarks(tradeMark, categoryId!!)
                    adapter.addTradeMark(tradeMark)
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
