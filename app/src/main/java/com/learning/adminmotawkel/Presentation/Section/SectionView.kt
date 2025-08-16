package com.learning.adminmotawkel.Presentation.Section

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.learning.adminmotawkel.R
import com.learning.adminmotawkel.databinding.FragmentSectionBinding
import com.learning.adminmotawkel.databinding.FragmentSectionViewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SectionView : Fragment() {

    private var _binding: FragmentSectionViewBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,

        savedInstanceState: Bundle?

    ): View? {

        _binding = FragmentSectionViewBinding.inflate(inflater, container, false)
        return binding.root
    }

}