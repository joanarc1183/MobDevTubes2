package com.example.tubes2

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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