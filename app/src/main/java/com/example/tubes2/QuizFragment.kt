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

class QuizFragment : Fragment(), QuizContract.View, SensorEventListener {

    private lateinit var presenter: QuizContract.Presenter
    private lateinit var tvQuestion: TextView
    private lateinit var binding: FragmentQuizBinding
    private val swapiService: SwapiService
    private var theme: String = ""
    private var length: Int = 0
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

        // theme game and length theme
        this.theme = arguments?.getString("theme")!!
        this.length = arguments?.getInt("length")!!

        if (this.theme == "people"){
            themePeople()
        } else if (this.theme == "planets"){
            themePlanets()
        } else {
            themeStarships()
        }
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

    private fun themePeople(){
        val id = Random.nextInt(1, this.length)
        val call = swapiService.getDetailsPeople("${this.theme}", "$id")
        call.enqueue(object : Callback<SwapiResponsePeople>{
            override fun onResponse(call: Call<SwapiResponsePeople>, response: Response<SwapiResponsePeople>) {
                if (response.isSuccessful) {
                    val result = response.body()?.result?.properties
                    val question = "Does ${result?.name} have ${result?.eye_color} eyes?"
                    binding.isiQuestion.text = question
                } else {
                    Log.e("UnsuccessfulMsgError", "Unsuccessful response from API")
                }
            }

            override fun onFailure(call: Call<SwapiResponsePeople>, t: Throwable) {
                // Handle error
                Log.e("onFailureMsgError", "onFailure called", t)
            }
        })
    }

    private fun themePlanets(){
        val id = Random.nextInt(1, this.length)

        val call = swapiService.getDetailsPlanets("${this.theme}", "$id")
        call.enqueue(object : Callback<SwapiResponsePlanets>{
            override fun onResponse(call: Call<SwapiResponsePlanets>, response: Response<SwapiResponsePlanets>) {
                if (response.isSuccessful) {
                    val result = response.body()?.result?.properties
                    val question = "Is ${result?.terrain} the terrain of the planet ${result?.name}?"
                    binding.isiQuestion.text = question
                } else {
                    Log.e("UnsuccessfulMsgError", "Unsuccessful response from API")
                }
            }

            override fun onFailure(call: Call<SwapiResponsePlanets>, t: Throwable) {
                // Handle error
                Log.e("onFailureMsgError", "onFailure called", t)
            }
        })
    }

    private fun themeStarships(){
        val availableNumbers = listOf(2,3,5,9,11,10,13,15,12,17)
        val randomIndex = Random.nextInt(availableNumbers.size)
        val id = availableNumbers[randomIndex]

        val call = swapiService.getDetailsStarships("${this.theme}", "$id")
        call.enqueue(object : Callback<SwapiResponseStarships>{
            override fun onResponse(call: Call<SwapiResponseStarships>, response: Response<SwapiResponseStarships>) {
                if (response.isSuccessful) {
                    val result = response.body()?.result?.properties
                    val question = "Is ${result?.name} manufactured by ${result?.manufacturer}?"
                    binding.isiQuestion.text = question
                } else {
                    Log.e("UnsuccessfulMsgError", "Unsuccessful response from API")
                }
            }

            override fun onFailure(call: Call<SwapiResponseStarships>, t: Throwable) {
                // Handle error
                Log.e("onFailureMsgError", "onFailure called", t)
            }
        })
    }

    override fun showScore(score: Int) {
        // Implement logic to display the score to the user
    }

    companion object {
        fun newInstance() = QuizFragment()
    }
}