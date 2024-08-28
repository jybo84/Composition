package com.example.composition.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.composition.R
import com.example.composition.databinding.FinishedGameFragmentBinding
import com.example.composition.domain.entity.GameResult

class GameFinishedFragment : Fragment() {

    private lateinit var binding: FinishedGameFragmentBinding
    private lateinit var gameResult: GameResult

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parsArgs()
    }

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
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                retryGame()
            }
        })
        binding.btnAgain.setOnClickListener {
            retryGame()
        }
    }

    companion object {

        const val KEY_GAME_RESULT = "game_result"

        fun newInstance(gameResult: GameResult): GameFinishedFragment {
            val gameFinishFragment = GameFinishedFragment()
            val bundle = Bundle()
            bundle.putParcelable(KEY_GAME_RESULT, gameResult)
            gameFinishFragment.arguments = bundle
            return gameFinishFragment
        }
    }

    private fun parsArgs() {
         requireArguments().getParcelable<GameResult>(KEY_GAME_RESULT).let {
             if (it != null) {
                 gameResult = it
             }
         }
    }

    private fun retryGame(){
        requireActivity().supportFragmentManager.popBackStack(GameFragment.NANE, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }
}