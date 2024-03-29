package com.example.tubes2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.tubes2.databinding.FragmentScoreBinding
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.random.Random

class ScoreFragment : Fragment() {
    private lateinit var binding: FragmentScoreBinding
    private lateinit var presenter: QuizContract.Presenter
    private lateinit var swapiRepository: SwapiRepository

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentScoreBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val activity = requireActivity() as MainActivity
        val quizScoreModel = activity.quiz

        val formattedScore = String.format("%.2f", quizScoreModel.calculateScore())
        binding.isiScore.text = formattedScore

        val button_play = binding.playAgain
        val fragmentContainer = requireActivity().findViewById<View>(R.id.fragmentContainer)

        button_play.setOnClickListener {
            quizScoreModel.updateScore(0)

            val homeFragment = HomeFragment()
            val transaction: FragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(fragmentContainer.id, homeFragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
    }
}