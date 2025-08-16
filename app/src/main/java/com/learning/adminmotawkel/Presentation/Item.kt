package com.learning.adminmotawkel.Presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.learning.adminmotawkel.R
import com.learning.adminmotawkel.databinding.FragmentItemBinding
import com.learning.adminmotawkel.databinding.FragmentTradeMarksBinding

class Item : Fragment() {

    private var _binding: FragmentItemBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentItemBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}