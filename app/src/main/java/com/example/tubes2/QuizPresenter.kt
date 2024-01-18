package com.example.tubes2

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.widget.Toast
import android.os.Handler
import android.util.Log
import kotlinx.coroutines.*
import androidx.core.content.ContextCompat.getSystemService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.random.Random

interface QuizContract {
    interface View {
        fun showQuestion(question: String)
        fun showScore(score: Int)
    }

    interface Presenter {
        fun startQuiz(theme: String, length: Int): Triple<String, String, String>
        fun answerQuestion(isTrue: Boolean)
        fun handleSensorEvent(event: SensorEvent)
    }
}

class QuizPresenter(private val view: QuizContract.View, private val context: Context, private var swapiRepository: SwapiRepository) : QuizContract.Presenter, SensorEventListener {

    private val questions = mutableListOf<QuizQuestion>()
    private var currentQuestionIndex = 0
    private var score = 0
    private var canAnswer = true
    private val handler = Handler()

    private lateinit var sensorManager: SensorManager
    private var accelerometer: Sensor? = null

    init {
        // Initialize sensor manager and accelerometer
        sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
    }

    override fun startQuiz(theme: String, length: Int): Triple<String, String, String> {
        val result = runBlocking {
            if (theme == "people") {
                themePeople(length)
            } else if (theme == "planets") {
                themePlanets(length)
            } else {
                themeStarships(length)
            }
        }
        return result
    }

    private suspend fun themePeople(length: Int): Triple<String, String, String> = withContext(Dispatchers.IO) {
        val id = Random.nextInt(1, length)
        var variable1 = ""
        var variable2 = ""
        var question = ""

        try {
            val response = swapiRepository.getPeopleDetailsAsync("$id").await()
            if (response.isSuccessful) {
                val result = response.body()?.result?.properties
                question = "Does ${result?.name} have ${result?.eye_color} eyes?"
                variable1 = result?.name.toString()
                variable2 = result?.eye_color.toString()
            } else {
                Log.e("UnsuccessfulMsgError", "Unsuccessful response from API")
            }
        } catch (t: Throwable) {
            Log.e("onFailureMsgError", "onFailure called", t)
        }

        return@withContext Triple(variable1, variable2, question)
    }

    private suspend fun themePlanets(length: Int): Triple<String, String, String> = withContext(Dispatchers.IO) {
        val id = Random.nextInt(1, length)
        var variable1 = ""
        var variable2 = ""
        var question = ""

        try {
            val response = swapiRepository.getPlanetsDetailsAsync("$id").await()
            if (response.isSuccessful) {
                val result = response.body()?.result?.properties
                question = "Is ${result?.terrain} the terrain of the planet ${result?.name}?"
                variable1 = result?.name.toString()
                variable2 = result?.terrain.toString()
            } else {
                Log.e("UnsuccessfulMsgError", "Unsuccessful response from API")
            }
        } catch (t: Throwable) {
            Log.e("onFailureMsgError", "onFailure called", t)
        }

        return@withContext Triple(variable1, variable2, question)
    }

    private suspend fun themeStarships(length: Int): Triple<String, String, String> = withContext(Dispatchers.IO) {
        val availableNumbers = listOf(2, 3, 5, 9, 11, 10, 13, 15, 12, 17)
        val randomIndex = Random.nextInt(availableNumbers.size)
        val id = availableNumbers[randomIndex]
        var variable1 = ""
        var variable2 = ""
        var question = ""

        try {
            val response = swapiRepository.getStarshipsDetailsAsync("$id").await()
            if (response.isSuccessful) {
                val result = response.body()?.result?.properties
                question = "Is ${result?.name} manufactured by ${result?.manufacturer}?"
                variable1 = result?.name.toString()
                variable2 = result?.manufacturer.toString()
            } else {
                Log.e("UnsuccessfulMsgError", "Unsuccessful response from API")
            }
        } catch (t: Throwable) {
            Log.e("onFailureMsgError", "onFailure called", t)
        }

        return@withContext Triple(variable1, variable2, question)
    }

    private fun showCurrentQuestion() {
        if (currentQuestionIndex < questions.size) {
            view.showQuestion(questions[currentQuestionIndex].question)
        } else {
            view.showScore(score)
        }
    }

    override fun answerQuestion(isTrue: Boolean) {
        if (currentQuestionIndex < questions.size) {
            val correctAnswer = questions[currentQuestionIndex].answer
            if (isTrue == correctAnswer) {
                score++
            }
            currentQuestionIndex++
            showCurrentQuestion()
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    override fun handleSensorEvent(event: SensorEvent) {
        // Implement logic to handle accelerometer events
        // For simplicity, you can call answerQuestion based on tilt direction
        val x = event.values[0]

//        if (angle > 60) {
//            showToast("Tilted more than 60 degrees!")
//        }
        if (canAnswer && x < -10) {
            showToast("Tilted to the right (answer 'Yes')")
            answerQuestion(true)
        } else if (canAnswer && x > 10) {
            showToast("Tilted to the left (answer 'No')")
            answerQuestion(false)
        }
        canAnswer = false
        handler.postDelayed({ canAnswer = true }, 2000)
    }

    fun onResume() {
        accelerometer?.let {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_NORMAL)
        }
    }

    fun onPause() {
        sensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent) {
        // Implement logic to handle accelerometer events
        // For simplicity, you can call answerQuestion based on tilt direction
        val x = event.values[0]
        if (x < -5) {
            answerQuestion(false)  // Tilted to the left (answer "No")
        } else if (x > 5) {
            answerQuestion(true)   // Tilted to the right (answer "Yes")
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        // Not needed for this example
    }
}
