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
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.tubes2.databinding.FragmentQuizBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class QuizFragment : Fragment(), QuizContract.View, SensorEventListener {

    private lateinit var presenter: QuizContract.Presenter
    private lateinit var tvQuestion: TextView
    private lateinit var binding: FragmentQuizBinding

//    private lateinit var swapiRepository: SwapiRepository
    private val swapiService: SwapiService

    private lateinit var sensorManager: SensorManager
    private var accelerometer: Sensor? = null

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
        val call = swapiService.getDetails("people", "1")
        call.enqueue(object : Callback<SwapiResponse>{
            override fun onResponse(call: Call<SwapiResponse>, response: Response<SwapiResponse>) {
                if (response.isSuccessful) {
                    val result = response.body()?.result?.properties
                    val question = "Apakah ${result?.name} memiliki rambut ${result?.hair_color}?"
                    binding.isiQuestion.text = question
                } else {
                    Log.e("UnsuccessfulMsgError", "Unsuccessful response from API")
                }
            }

            override fun onFailure(call: Call<SwapiResponse>, t: Throwable) {
                // Handle error
                Log.e("onFailureMsgError", "onFailure called", t)
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