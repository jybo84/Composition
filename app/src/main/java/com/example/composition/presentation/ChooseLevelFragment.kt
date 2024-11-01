package com.example.composition.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.composition.R
import com.example.composition.databinding.FragmentChooseLevelBinding
import com.example.composition.domain.entity.Level
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChooseLevelFragment: Fragment() {

    private lateinit var binding: FragmentChooseLevelBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentChooseLevelBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnLevelTest.setOnClickListener {
            launchGameFragment(Level.TEST)
        }
        binding.btnLevelEasy.setOnClickListener {
            launchGameFragment(Level.EASY)
        }
        binding.btnLevelNormal.setOnClickListener {
            launchGameFragment(Level.NORMAL)
        }
        binding.btnLevelHard.setOnClickListener {
            launchGameFragment(Level.HARD)
        }
    }

       private fun launchGameFragment(level: Level){
        findNavController().navigate(ChooseLevelFragmentDirections.actionChooseLevelFragmentToGameFragment(level))

    }
}