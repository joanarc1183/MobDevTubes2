package com.example.tubes2

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
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

class QuizFragment : Fragment(), QuizContract.View, SensorEventListener {

    private lateinit var presenter: QuizContract.Presenter
    private lateinit var tvQuestion: TextView
    private lateinit var binding: FragmentQuizBinding
    private lateinit var swapiRepository: SwapiRepository
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

        // Initialize presenter and sensor
        presenter = QuizPresenter(this, requireContext())
        sensorManager = requireActivity().getSystemService(Context.SENSOR_SERVICE) as SensorManager
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

        // Your existing code for fetching data from API
        swapiRepository = SwapiRepository()
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
        fun newInstance() = QuizFragment()
    }
}