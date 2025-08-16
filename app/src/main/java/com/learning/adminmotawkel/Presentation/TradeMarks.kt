package com.learning.adminmotawkel.Presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.learning.adminmotawkel.R
import com.learning.adminmotawkel.databinding.FragmentSectionBinding
import com.learning.adminmotawkel.databinding.FragmentTradeMarksBinding

class TradeMarks : Fragment() {
    private var _binding: FragmentTradeMarksBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTradeMarksBinding.inflate(inflater, container, false)
        return binding.root    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}