package com.example.tubes2

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.tubes2.databinding.FragmentHomeBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HomeFragment: Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val swapiService: SwapiService

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://www.swapi.tech/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        swapiService = retrofit.create(SwapiService::class.java)
    }
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
            startQuiz(binding.option1.toString())
        }

        option2.setOnClickListener {
            startQuiz(binding.option2.toString())
        }

        option3.setOnClickListener {
            startQuiz(binding.option3.toString())
        }
    }

    private fun startQuiz(theme: String) {
        val quizFragment = QuizFragment()
        val bundle = Bundle()

        // Menambahkan variabel ke dalam Bundle
        bundle.putString("theme", theme)
        bundle.putInt("length", 10)

        // Menetapkan Bundle ke Fragment
        quizFragment.arguments = bundle

        // Menampilkan QuizFragment
        requireFragmentManager().beginTransaction()
            .replace(R.id.fragmentContainer, quizFragment)
            .addToBackStack(null)
            .commit()

    }
}