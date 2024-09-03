package com.example.composition.presentation

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.composition.R
import com.example.composition.databinding.FragmentGameBinding
import com.example.composition.domain.entity.GameResult
import com.example.composition.domain.entity.GameSettings
import com.example.composition.domain.entity.Level
import com.example.composition.domain.entity.Questions

class GameFragment : Fragment() {

    private lateinit var level: Level
    private lateinit var binding: FragmentGameBinding
    private val gameViewModel: GameViewModel by viewModels()
    private val optionsAll by lazy {
        mutableListOf<TextView>().apply {
            add(binding.tvOption1)
            add(binding.tvOption2)
            add(binding.tvOption3)
            add(binding.tvOption4)
            add(binding.tvOption5)
            add(binding.tvOption6)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parsArgs()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGameBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        gameViewModel.startGame(level)
        observeViewModel()
        setOnClickListenerToOptions()
    }

    private fun setOnClickListenerToOptions(){
        for (el in optionsAll){
            el.setOnClickListener {
                gameViewModel.chooseAnswer(el.text.toString().toInt())
            }
        }
    }

    private fun observeViewModel() {
        gameViewModel.time.observe(viewLifecycleOwner) {
            binding.tvTimer.text = it
        }
        gameViewModel.question.observe(viewLifecycleOwner) {
            binding.tvSum.text = it.sum.toString()
            binding.tvVisibleNumber.text = it.visibleNumber.toString()
            for (el in 0 until optionsAll.size) {
                optionsAll[el].text = it.options[el].toString()
            }
        }
        gameViewModel.percentOfRightAnswers.observe(viewLifecycleOwner) {
            binding.progressBar.setProgress(it, true)
        }
        gameViewModel.enoughCountOfRightAnswers.observe(viewLifecycleOwner) {
            val colorResId = if (it) {
                android.R.color.holo_green_light
            } else
                android.R.color.holo_red_light

            val color = ContextCompat.getColor(requireContext(), colorResId)
            binding.tvPercentRightAnswer.setTextColor(color)
        }

        gameViewModel.enoughPercentOfRightAnswers.observe(viewLifecycleOwner){
            val colorResId = if (it) {
                android.R.color.holo_green_light
            } else
                android.R.color.holo_red_light

            val color = ContextCompat.getColor(requireContext(), colorResId)
            binding.progressBar.progressTintList = ColorStateList.valueOf(color)
        }
        gameViewModel.minPercent.observe(viewLifecycleOwner){
            binding.progressBar.secondaryProgress = it
        }

        gameViewModel.progressAnswers.observe(viewLifecycleOwner){
            binding.tvPercentRightAnswer.text = it
        }
        gameViewModel.gameResult.observe(viewLifecycleOwner){
            launchFinish(it)
        }
    }


    private fun parsArgs() {
        requireArguments().getParcelable<Level>(KEY_LEVEL).let {
            if (it != null) {
                level = it
            }
        }
    }

    companion object {

        const val KEY_LEVEL = "level"
        const val NANE = "GameFragment"

        fun newInstance(level: Level): GameFragment {
            val gameFragment = GameFragment()
            val bundle = Bundle()
            bundle.putParcelable(KEY_LEVEL, level)
            gameFragment.arguments = bundle
            return gameFragment
        }
    }

    private fun launchFinish(gameResult: GameResult) {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.main_container, GameFinishedFragment.newInstance(gameResult))
            .addToBackStack(null)
            .commit()
    }
}