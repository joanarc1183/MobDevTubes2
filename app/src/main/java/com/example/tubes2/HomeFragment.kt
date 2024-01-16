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
        val btn1 = binding.btn1
        val btn2 = binding.btn2
        val btn3 = binding.btn3

        btn1.setOnClickListener {
            startQuiz("${btn1.text}")
        }

        btn2.setOnClickListener {
            startQuiz("${btn2.text}")
        }

        btn3.setOnClickListener {
            startQuiz("${btn3.text}")
        }
    }

    private fun startQuiz(theme: String) {
        val quizFragment = QuizFragment()
        val bundle = Bundle()
        bundle.putString("theme", theme)
        quizFragment.arguments = bundle

        requireFragmentManager().beginTransaction()
            .replace(R.id.fragmentContainer, quizFragment)
            .addToBackStack(null)
            .commit()
    }
}