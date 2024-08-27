package com.example.composition.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.composition.R
import com.example.composition.databinding.FinishedGameFragmentBinding

class GameFinishedFragment: Fragment() {

    private lateinit var binding: FinishedGameFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FinishedGameFragmentBinding.inflate(inflater, container, false)
        return binding.root

    }
}