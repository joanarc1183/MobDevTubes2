package com.example.tubes2

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorListener
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
        fun onDetection()
    }

    interface Presenter {
        fun startQuiz(theme: String, length: Int, number: Int): Triple<String, String, String>
        fun answerQuestion(isTrue: Boolean)
        fun handleSensorEvent(event: SensorEvent)
    }
}

class QuizPresenter(private val view: QuizContract.View, private val context: Context, private var swapiRepository: SwapiRepository) : QuizContract.Presenter, SensorEventListener {
    private var score = 0
    private var canAnswer = true
    private val handler = Handler()
    private var answer: Boolean = true
    private lateinit var sensorManager: SensorManager
    private var accelerometer: Sensor? = null

    init {
        // Initialize sensor manager and accelerometer
        sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
    }

    override fun startQuiz(theme: String, length: Int, number: Int): Triple<String, String, String> {
        if (number == 1){
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
        } else if (number == 2){
            val result = runBlocking {
                if (theme == "people") {
                    themePeople2(length)
                } else if (theme == "planets") {
                    themePlanets2(length)
                } else {
                    themeStarships2(length)
                }
            }
            return result
        } else {
            val result = runBlocking {
                if (theme == "people") {
                    themePeople3(length)
                } else if (theme == "planets") {
                    themePlanets3(length)
                } else {
                    themeStarships3(length)
                }
            }
            return result
        }
    }

    private suspend fun themePeople(length: Int): Triple<String, String, String> = withContext(Dispatchers.IO) {
        val id = Random.nextInt(1, length)
        val id2 = Random.nextInt(1, length)
        var variable1 = ""
        var variable2 = ""
        var question = ""

        try {
            val response = swapiRepository.getPeopleDetailsAsync("$id").await()
            val response2 = swapiRepository.getPeopleDetailsAsync("$id2").await()
            if (response.isSuccessful) {
                val result = response.body()?.result?.properties
                val result2 = response2.body()?.result?.properties
                question = "Does ${result?.name} have ${result2?.eye_color} eyes?"
                answer = result?.eye_color == result2?.eye_color
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

    private suspend fun themePeople2(length: Int): Triple<String, String, String> = withContext(Dispatchers.IO) {
        val id = Random.nextInt(1, length)
        val id2 = Random.nextInt(1, length)
        var variable1 = ""
        var variable2 = ""
        var question = ""

        try {
            val response = swapiRepository.getPeopleDetailsAsync("$id").await()
            val response2 = swapiRepository.getPeopleDetailsAsync("$id2").await()
            if (response.isSuccessful) {
                val result = response.body()?.result?.properties
                val result2 = response2.body()?.result?.properties
                question = "Is ${result?.name} ${result2?.gender}?"
                answer = result?.gender == result2?.gender
                variable1 = result?.name.toString()
                variable2 = result?.gender.toString()
            } else {
                Log.e("UnsuccessfulMsgError", "Unsuccessful response from API")
            }
        } catch (t: Throwable) {
            Log.e("onFailureMsgError", "onFailure called", t)
        }

        return@withContext Triple(variable1, variable2, question)
    }

    private suspend fun themePeople3(length: Int): Triple<String, String, String> = withContext(Dispatchers.IO) {
        val id = Random.nextInt(1, length)
        val id2 = Random.nextInt(1, length)
        var variable1 = ""
        var variable2 = ""
        var question = ""

        try {
            val response = swapiRepository.getPeopleDetailsAsync("$id").await()
            val response2 = swapiRepository.getPeopleDetailsAsync("$id2").await()
            if (response.isSuccessful) {
                val result = response.body()?.result?.properties
                val result2 = response2.body()?.result?.properties
                question = "Is ${result?.name} ${result2?.height} cm tall?"
                answer = result?.height == result2?.height
                variable1 = result?.name.toString()
                variable2 = result?.height.toString()
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
        val id2 = Random.nextInt(1, length)
        var variable1 = ""
        var variable2 = ""
        var question = ""

        try {
            val response = swapiRepository.getPlanetsDetailsAsync("$id").await()
            val response2 = swapiRepository.getPlanetsDetailsAsync("$id2").await()
            if (response.isSuccessful) {
                val result = response.body()?.result?.properties
                val result2 = response2.body()?.result?.properties
                question = "Is ${result2?.terrain} the terrain of the planet ${result?.name}?"
                answer = result?.terrain == result2?.terrain
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

    private suspend fun themePlanets2(length: Int): Triple<String, String, String> = withContext(Dispatchers.IO) {
        val id = Random.nextInt(1, length)
        val id2 = Random.nextInt(1, length)
        var variable1 = ""
        var variable2 = ""
        var question = ""

        try {
            val response = swapiRepository.getPlanetsDetailsAsync("$id").await()
            val response2 = swapiRepository.getPlanetsDetailsAsync("$id2").await()
            if (response.isSuccessful) {
                val result = response.body()?.result?.properties
                val result2 = response2.body()?.result?.properties
                question = "Does ${result?.name} have ${result2?.climate} climate?"
                answer = result?.climate == result2?.climate
                variable1 = result?.name.toString()
                variable2 = result?.climate.toString()
            } else {
                Log.e("UnsuccessfulMsgError", "Unsuccessful response from API")
            }
        } catch (t: Throwable) {
            Log.e("onFailureMsgError", "onFailure called", t)
        }

        return@withContext Triple(variable1, variable2, question)
    }

    private suspend fun themePlanets3(length: Int): Triple<String, String, String> = withContext(Dispatchers.IO) {
        val id = Random.nextInt(1, length)
        val id2 = Random.nextInt(1, length)
        var variable1 = ""
        var variable2 = ""
        var question = ""

        try {
            val response = swapiRepository.getPlanetsDetailsAsync("$id").await()
            val response2 = swapiRepository.getPlanetsDetailsAsync("$id2").await()
            if (response.isSuccessful) {
                val result = response.body()?.result?.properties
                val result2 = response2.body()?.result?.properties
                question = "Is The ${result?.name} ${result2?.diameter} km in diameter?"
                answer = result?.diameter == result2?.diameter
                variable1 = result?.name.toString()
                variable2 = result?.diameter.toString()
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
        val id2 = availableNumbers[randomIndex]
        var variable1 = ""
        var variable2 = ""
        var question = ""

        try {
            val response = swapiRepository.getStarshipsDetailsAsync("$id").await()
            val response2 = swapiRepository.getStarshipsDetailsAsync("$id2").await()
            if (response.isSuccessful) {
                val result = response.body()?.result?.properties
                val result2 = response2.body()?.result?.properties
                question = "Is ${result?.name} starship manufactured by ${result2?.manufacturer}?"
                answer = result?.manufacturer == result2?.manufacturer
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

    private suspend fun themeStarships2(length: Int): Triple<String, String, String> = withContext(Dispatchers.IO) {
        val availableNumbers = listOf(2, 3, 5, 9, 11, 10, 13, 15, 12, 17)
        val randomIndex = Random.nextInt(availableNumbers.size)
        val id = availableNumbers[randomIndex]
        val id2 = availableNumbers[randomIndex]
        var variable1 = ""
        var variable2 = ""
        var question = ""

        try {
            val response = swapiRepository.getStarshipsDetailsAsync("$id").await()
            val response2 = swapiRepository.getStarshipsDetailsAsync("$id2").await()
            if (response.isSuccessful) {
                val result = response.body()?.result?.properties
                val result2 = response2.body()?.result?.properties
                question = "Does The ${result?.name} starship have a ${result2?.model} model?"
                answer = result?.model == result2?.model
                variable1 = result?.name.toString()
                variable2 = result?.model.toString()
            } else {
                Log.e("UnsuccessfulMsgError", "Unsuccessful response from API")
            }
        } catch (t: Throwable) {
            Log.e("onFailureMsgError", "onFailure called", t)
        }

        return@withContext Triple(variable1, variable2, question)
    }

    private suspend fun themeStarships3(length: Int): Triple<String, String, String> = withContext(Dispatchers.IO) {
        val availableNumbers = listOf(2, 3, 5, 9, 11, 10, 13, 15, 12, 17)
        val randomIndex = Random.nextInt(availableNumbers.size)
        val id = availableNumbers[randomIndex]
        val id2 = availableNumbers[randomIndex]
        var variable1 = ""
        var variable2 = ""
        var question = ""

        try {
            val response = swapiRepository.getStarshipsDetailsAsync("$id").await()
            val response2 = swapiRepository.getStarshipsDetailsAsync("$id2").await()
            if (response.isSuccessful) {
                val result = response.body()?.result?.properties
                val result2 = response2.body()?.result?.properties
                question = "Is it really The ${result?.name} starship have ${result2?.crew} crew?"
                answer = result?.crew == result2?.crew
                variable1 = result?.name.toString()
                variable2 = result?.crew.toString()
            } else {
                Log.e("UnsuccessfulMsgError", "Unsuccessful response from API")
            }
        } catch (t: Throwable) {
            Log.e("onFailureMsgError", "onFailure called", t)
        }

        return@withContext Triple(variable1, variable2, question)
    }

//    private fun showCurrentQuestion() {
//        if (currentQuestionIndex < questions.size) {
//            view.showQuestion(questions[currentQuestionIndex].question)
//        } else {
//            view.showScore(score)
//        }
//    }

    override fun answerQuestion(isTrue: Boolean) {
        if (isTrue == answer) {
            score++
        }
        Log.d("HALAH:D", "${score}")
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
            // Panggil callback
            view.onDetection()
        } else if (canAnswer && x > 10) {
            showToast("Tilted to the left (answer 'No')")
            answerQuestion(false)
            // Panggil callback
            view.onDetection()
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
