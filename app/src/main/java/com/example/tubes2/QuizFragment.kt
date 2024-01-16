package com.example.tubes2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.tubes2.databinding.FragmentQuizBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class QuizFragment : Fragment(), QuizContract.View {
    private lateinit var presenter: QuizContract.Presenter
    private lateinit var tvQuestion: TextView
    private lateinit var binding: FragmentQuizBinding
    private lateinit var swapiRepository: SwapiRepository

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentQuizBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        swapiRepository = SwapiRepository()

        // Mengambil data dari API dan menampilkan pertanyaan
        swapiRepository.getPeopleDetails("1", object : Callback<SwapiResponse> {
            override fun onResponse(call: Call<SwapiResponse>, response: Response<SwapiResponse>) {
                if (response.isSuccessful) {
                    val result = response.body()?.result?.get(0)
                    val question = "Apakah ${result?.name} memiliki rambut ${result?.hair_color}?"
                    binding.isiQuestion.text = question
                }
            }

            override fun onFailure(call: Call<SwapiResponse>, t: Throwable) {
                // Handle error
            }
        })

        // pertanyaan dari swapi
        binding.isiQuestion.setText("LALALA")
//        val view = inflater.inflate(binding.tvQuestion, container, false)
//        tvQuestion = view.findViewById(binding.tvQuestion)
    }

    override fun showQuestion(question: String) {
        tvQuestion.text = question
    }

    override fun showScore(score: Int) {
        // Implement logic to display the score to the user
    }

    companion object {
        fun newInstance() = QuizFragment()
    }
}