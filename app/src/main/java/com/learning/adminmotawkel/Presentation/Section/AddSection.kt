package com.learning.adminmotawkel.Presentation.Section

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.learning.adminmotawkel.Core.UiState
import com.learning.adminmotawkel.Domain.ViewModels.SectionVM
import com.learning.adminmotawkel.Domain.models.sections.SectionModel
import com.learning.adminmotawkel.databinding.FragmentSectionBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.UUID

@AndroidEntryPoint
class AddSection : Fragment() {

    private var _binding: FragmentSectionBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SectionVM by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSectionBinding.inflate(inflater, container, false)

        binding.AddSection.setOnClickListener {
            lifecycleScope.launch(Dispatchers.Main) {
                viewModel.addSections.collect { state ->
                    {
                        when (state) {
                            is UiState.Loading -> {
                                binding.progressSection.visibility = View.VISIBLE

                            }

                            is UiState.Success -> {
                                binding.progressSection.visibility = View.GONE
                                viewModel.loadSections()
                            }

                            is UiState.Error -> {
                                binding.progressSection.visibility = View.GONE

                            }
                        }
                    }
                }
            }
            viewModel.addSection(getDataFromUI())

        }


        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun getDataFromUI(): SectionModel {
        return SectionModel(
            id = UUID.randomUUID().toString(),
            name = binding.sectionName.text.toString(),
            img = ""
        )
    }
}