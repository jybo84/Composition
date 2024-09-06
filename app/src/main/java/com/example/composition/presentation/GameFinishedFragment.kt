package com.example.composition.presentation

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.composition.R
import com.example.composition.databinding.FinishedGameFragmentBinding
import com.example.composition.domain.entity.GameResult

class GameFinishedFragment : Fragment() {

    private val args by navArgs<GameFinishedFragmentArgs>()

    private lateinit var binding: FinishedGameFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FinishedGameFragmentBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnAgain.setOnClickListener {
            retryGame()
        }
        result(args.gameResult)
    }

    private fun retryGame() {
        findNavController().popBackStack()

    }

    private fun result(gameResult: GameResult) {
        binding.resultSmile.setImageResource(getSmile())
        binding.minCountResult.text = gameResult.gameSettings.minCountOfRightAnswers.toString()
        binding.score.text = gameResult.countOfRightAnswers.toString()
        binding.resultPercent.text = getPercentOfRightAnswers(gameResult).toString()
        binding.minPercent.text = gameResult.gameSettings.minPercentOfRightAnswers.toString()
    }

    private fun getSmile(): Int {
        return if (args.gameResult.winner) {
            R.drawable.happy
        } else {
            R.drawable.sad
        }
    }

    private fun getPercentOfRightAnswers(gameResult: GameResult): Int {
        return if (gameResult.countOfQuestion == 0) {
            0
        } else
            ((gameResult.countOfRightAnswers / gameResult.countOfQuestion.toDouble())*100).toInt()
    }
}