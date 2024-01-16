package com.example.tubes2

import android.os.Bundle
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
        val btnPeople = binding.btnPeople
        val btnFilms = binding.btnFilms
        val btnPlanets = binding.btnPlanets

        btnPeople.setOnClickListener {
            startQuiz("people")
        }

        btnFilms.setOnClickListener {
            startQuiz("films")
        }

        btnPlanets.setOnClickListener {
            startQuiz("planets")
        }
    }

    private fun startQuiz(theme: String) {
        val fragment = QuizFragment.newInstance()
        val presenter = QuizPresenter(fragment)
        presenter.startQuiz(theme)

        val quizFragment = QuizFragment()
        requireFragmentManager().beginTransaction()
            .replace(R.id.fragmentContainer, quizFragment)
            .addToBackStack(null)
            .commit()

    }
}