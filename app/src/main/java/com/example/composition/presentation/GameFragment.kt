package com.example.composition.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.composition.R
import com.example.composition.databinding.FragmentGameBinding
import com.example.composition.domain.entity.GameResult
import com.example.composition.domain.entity.GameSettings
import com.example.composition.domain.entity.Level

class GameFragment: Fragment() {

    private lateinit var level: Level

    private lateinit var binding: FragmentGameBinding


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
        binding.tvOption1.setOnClickListener {
            launchFinish(GameResult(true, 5,5, GameSettings(7, 7,7,7)))
        }
    }

    private fun parsArgs(){
        level = requireArguments().getSerializable(KEY_LEVEL) as Level
    }

    companion object{

        const val KEY_LEVEL = "level"
        const val NANE = "GameFragment"

        fun newInstance(level: Level): GameFragment{
           val gameFragment = GameFragment()
            val bundle = Bundle()
            bundle.putSerializable(KEY_LEVEL, level)
            gameFragment.arguments = bundle
            return gameFragment
        }
    }

    private fun launchFinish(gameResult: GameResult){
      requireActivity().supportFragmentManager.beginTransaction()
          .replace(R.id.main_container, GameFinishedFragment.newInstance(gameResult))
          .addToBackStack(null)
          .commit()
    }
}