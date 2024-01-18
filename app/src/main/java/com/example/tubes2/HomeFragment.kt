package com.example.tubes2

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.tubes2.databinding.FragmentHomeBinding

class HomeFragment: Fragment() {
    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val option1 = binding.option1
        val option2 = binding.option2
        val option3 = binding.option3

        option1.setOnClickListener {
            startQuiz(binding.category1.text.toString().lowercase())
        }
        option2.setOnClickListener {
            startQuiz(binding.category2.text.toString().lowercase())
        }
        option3.setOnClickListener {
            startQuiz(binding.category3.text.toString().lowercase())
        }
    }

    private fun startQuiz(theme: String) {
        val quizFragment = QuizFragment()

        val activity = requireActivity() as MainActivity
        val quizScoreModel = activity.quiz

        quizScoreModel.updateTheme(theme)

        // Menampilkan QuizFragment
        requireFragmentManager().beginTransaction()
            .replace(R.id.fragmentContainer, quizFragment)
            .addToBackStack(null)
            .commit()

    }
}