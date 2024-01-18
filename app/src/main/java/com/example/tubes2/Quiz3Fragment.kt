package com.example.tubes2

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.tubes2.databinding.FragmentQuizBinding

class Quiz3Fragment : Fragment(), QuizContract.View, SensorEventListener {

    private lateinit var presenter: QuizContract.Presenter
    private lateinit var tvQuestion: TextView
    private lateinit var binding: FragmentQuizBinding
    private lateinit var swapiRepository: SwapiRepository
    private lateinit var quizScoreModel: QuizScoreModel
    private var theme: String = ""
    private var length: Int = 0
    private lateinit var sensorManager: SensorManager
    private var accelerometer: Sensor? = null

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

//        quizModel = (requireActivity().application as MainActivity).quizModel
//        val application = requireActivity().application as MainActivity
//        val quizScoreModel = application.quizScoreModel
//        quizScoreModel.score += 10
        quizScoreModel = QuizScoreModel()

        // Initialize presenter and sensor
        presenter = QuizPresenter(this, requireContext(), swapiRepository, quizScoreModel)
//        presenter = QuizPresenter(this, requireContext())
        sensorManager = requireActivity().getSystemService(Context.SENSOR_SERVICE) as SensorManager
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

        // Your existing code for fetching data from API

        // theme game and length theme
        this.theme = arguments?.getString("theme")!!
        this.length = arguments?.getInt("length")!!

        val presenter = presenter.startQuiz(this.theme, this.length, 3)

        binding.isiQuestion.text = presenter.third

    }

    override fun onDetection() {
        requireFragmentManager().beginTransaction()
            .replace(R.id.fragmentContainer, ScoreFragment())
            .addToBackStack(null)
            .commit()
    }

    override fun onResume() {
        super.onResume()
        accelerometer?.let {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_NORMAL)
        }
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent) {
        presenter.handleSensorEvent(event)
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        // Not needed for this example
    }

    override fun showQuestion(question: String) {
        tvQuestion.text = question
    }


    override fun showScore(score: Int) {
        // Implement logic to display the score to the user
    }

    companion object {
        fun newInstance() = Quiz3Fragment()
    }
}