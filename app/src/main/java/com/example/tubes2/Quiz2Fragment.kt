package com.example.tubes2

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.tubes2.databinding.FragmentQuizBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.random.Random

class Quiz2Fragment : Fragment(), QuizContract.View, SensorEventListener {

    private lateinit var presenter: QuizContract.Presenter
    private lateinit var tvQuestion: TextView
    private lateinit var binding: FragmentQuizBinding
    private lateinit var swapiRepository: SwapiRepository
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

        // Initialize presenter and sensor
        presenter = QuizPresenter(this, requireContext(), swapiRepository)
//        presenter = QuizPresenter(this, requireContext())
        sensorManager = requireActivity().getSystemService(Context.SENSOR_SERVICE) as SensorManager
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

        // Your existing code for fetching data from API

        // theme game and length theme
        this.theme = arguments?.getString("theme")!!
        this.length = arguments?.getInt("length")!!

        val presenter = presenter.startQuiz(this.theme, this.length,2)

        binding.isiQuestion.text = presenter.third

    }

    override fun onDetection() {
        val quizFragment = Quiz3Fragment()
        val bundle = Bundle()

        // Menambahkan variabel ke dalam Bundle
        bundle.putString("theme", theme)
        bundle.putInt("length", 10)

        // Menetapkan Bundle ke Fragment
        quizFragment.arguments = bundle
        requireFragmentManager().beginTransaction()
            .replace(R.id.fragmentContainer, quizFragment)
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
        fun newInstance() = Quiz2Fragment()
    }
}