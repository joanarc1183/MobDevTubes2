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
import kotlin.random.Random

interface QuizContract {
    interface View {
        fun onDetection()
    }

    interface Presenter {
        fun startQuiz(theme: String, number: Int): Pair<String, String>
        fun answerQuestion(isTrue: Boolean)
        fun handleSensorEvent(event: SensorEvent)
    }
}

class QuizPresenter(private val view: QuizContract.View, private val context: Context, private var swapiRepository: SwapiRepository, private var quiz: QuizModel) : QuizContract.Presenter, SensorEventListener {
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

    override fun startQuiz(theme: String, number: Int): Pair<String, String> {
        when (number) {
            1 -> {
                val result = runBlocking {
                    var length = lengthCategory(theme)
                    when (theme) {
                        "people" -> {
                            themePeople(length)
                        }
                        "planets" -> {
                            themePlanets(length)
                        }
                        else -> {
                            themeStarships(length)
                        }
                    }
                }
                return result
            }
            2 -> {
                val result = runBlocking {
                    var length = lengthCategory(theme)
                    when (theme) {
                        "people" -> {
                            themePeople2(length)
                        }
                        "planets" -> {
                            themePlanets2(length)
                        }
                        else -> {
                            themeStarships2(length)
                        }
                    }
                }
                return result
            }
            else -> {
                val result = runBlocking {
                    var length = lengthCategory(theme)
                    when (theme) {
                        "people" -> {
                            themePeople3(length)
                        }
                        "planets" -> {
                            themePlanets3(length)
                        }
                        else -> {
                            themeStarships3(length)
                        }
                    }
                }
                return result
            }
        }
    }

    private suspend fun lengthCategory(category: String): Int = withContext(Dispatchers.IO) {
        try {
            val response = swapiRepository.getLengthAsync(category).await()
            if (response.isSuccessful) {
                return@withContext response.body()?.total_records!!
            } else {
                Log.e("UnsuccessfulMsgError", "Unsuccessful response from API")
            }
        } catch (t: Throwable) {
            Log.e("onFailureMsgError", "onFailure called", t)
        }

        return@withContext 0
    }

    private suspend fun themeFilm(length: Int, id: Int): String = withContext(Dispatchers.IO) {
        try {
            val response = swapiRepository.getFilmDetailsAsync("$id").await()
            if (response.isSuccessful) {
                return@withContext response.body()?.result?.properties!!.title
            } else {
                Log.e("UnsuccessfulMsgError", "Unsuccessful response from API")
            }
        } catch (t: Throwable) {
            Log.e("onFailureMsgError", "onFailure called", t)
        }

        return@withContext "Film not available"
    }

    private suspend fun themePeople(length: Int): Pair<String, String> = withContext(Dispatchers.IO) {
        val id = Random.nextInt(1, length)
        val id2 = Random.nextInt(1, length)
        var question = ""
        var film = ""
        try {
            val response = swapiRepository.getPeopleDetailsAsync("$id").await()
            val response2 = swapiRepository.getPeopleDetailsAsync("$id2").await()
            if (response.isSuccessful) {
                val result = response.body()?.result?.properties
                val result2 = response2.body()?.result?.properties
                question = "Does ${result?.name} have ${result2?.eye_color} eyes?"
                answer = result?.eye_color == result2?.eye_color
            } else {
                Log.e("UnsuccessfulMsgError", "Unsuccessful response from API")
            }
        } catch (t: Throwable) {
            Log.e("onFailureMsgError", "onFailure called", t)
        }

        return@withContext Pair(question, film)
    }

    private suspend fun themePeople2(length: Int): Pair<String, String> = withContext(Dispatchers.IO) {
        val id = Random.nextInt(1, length)
        val id2 = Random.nextInt(1, length)
        var question = ""
        var film = ""
        try {
            val response = swapiRepository.getPeopleDetailsAsync("$id").await()
            val response2 = swapiRepository.getPeopleDetailsAsync("$id2").await()
            if (response.isSuccessful) {
                val result = response.body()?.result?.properties
                val result2 = response2.body()?.result?.properties
                question = "Is ${result?.name} ${result2?.gender}?"
                answer = result?.gender == result2?.gender
                film = themeFilm(length, id)
            } else {
                Log.e("UnsuccessfulMsgError", "Unsuccessful response from API")
            }
        } catch (t: Throwable) {
            Log.e("onFailureMsgError", "onFailure called", t)
        }

        return@withContext Pair(question, film)
    }

    private suspend fun themePeople3(length: Int): Pair<String, String> = withContext(Dispatchers.IO) {
        val id = Random.nextInt(1, length)
        val id2 = Random.nextInt(1, length)
        var question = ""
        var film = ""
        try {
            val response = swapiRepository.getPeopleDetailsAsync("$id").await()
            val response2 = swapiRepository.getPeopleDetailsAsync("$id2").await()
            if (response.isSuccessful) {
                val result = response.body()?.result?.properties
                val result2 = response2.body()?.result?.properties
                question = "Is ${result?.name} ${result2?.height} cm tall?"
                answer = result?.height == result2?.height
                film = themeFilm(length, id)
            } else {
                Log.e("UnsuccessfulMsgError", "Unsuccessful response from API")
            }
        } catch (t: Throwable) {
            Log.e("onFailureMsgError", "onFailure called", t)
        }

        return@withContext Pair(question, film)
    }

    private suspend fun themePlanets(length: Int): Pair<String, String> = withContext(Dispatchers.IO) {
        val id = Random.nextInt(1, length)
        val id2 = Random.nextInt(1, length)
        var question = ""
        var film = ""
        try {
            val response = swapiRepository.getPlanetsDetailsAsync("$id").await()
            val response2 = swapiRepository.getPlanetsDetailsAsync("$id2").await()
            if (response.isSuccessful) {
                val result = response.body()?.result?.properties
                val result2 = response2.body()?.result?.properties
                question = "Is ${result2?.terrain} the terrain of the planet ${result?.name}?"
                answer = result?.terrain == result2?.terrain
                film = themeFilm(length, id)
            } else {
                Log.e("UnsuccessfulMsgError", "Unsuccessful response from API")
            }
        } catch (t: Throwable) {
            Log.e("onFailureMsgError", "onFailure called", t)
        }

        return@withContext Pair(question, film)
    }

    private suspend fun themePlanets2(length: Int): Pair<String, String> = withContext(Dispatchers.IO) {
        val id = Random.nextInt(1, length)
        val id2 = Random.nextInt(1, length)
        var question = ""
        var film = ""
        try {
            val response = swapiRepository.getPlanetsDetailsAsync("$id").await()
            val response2 = swapiRepository.getPlanetsDetailsAsync("$id2").await()
            if (response.isSuccessful) {
                val result = response.body()?.result?.properties
                val result2 = response2.body()?.result?.properties
                question = "Does ${result?.name} have ${result2?.climate} climate?"
                answer = result?.climate == result2?.climate
                film = themeFilm(length, id)
            } else {
                Log.e("UnsuccessfulMsgError", "Unsuccessful response from API")
            }
        } catch (t: Throwable) {
            Log.e("onFailureMsgError", "onFailure called", t)
        }

        return@withContext Pair(question, film)
    }

    private suspend fun themePlanets3(length: Int): Pair<String, String> = withContext(Dispatchers.IO) {
        val id = Random.nextInt(1, length)
        val id2 = Random.nextInt(1, length)
        var question = ""
        var film = ""
        try {
            val response = swapiRepository.getPlanetsDetailsAsync("$id").await()
            val response2 = swapiRepository.getPlanetsDetailsAsync("$id2").await()
            if (response.isSuccessful) {
                val result = response.body()?.result?.properties
                val result2 = response2.body()?.result?.properties
                question = "Is The ${result?.name} ${result2?.diameter} km in diameter?"
                answer = result?.diameter == result2?.diameter
                film = themeFilm(length, id)
            } else {
                Log.e("UnsuccessfulMsgError", "Unsuccessful response from API")
            }
        } catch (t: Throwable) {
            Log.e("onFailureMsgError", "onFailure called", t)
        }

        return@withContext Pair(question, film)
    }

    private suspend fun themeStarships(length: Int): Pair<String, String> = withContext(Dispatchers.IO) {
        val availableNumbers = listOf(2, 3, 5, 9, 11, 10, 13, 15, 12, 17)
        val randomIndex = Random.nextInt(availableNumbers.size)
        val id = availableNumbers[randomIndex]
        val id2 = availableNumbers[randomIndex]
        var question = ""
        var film = ""
        try {
            val response = swapiRepository.getStarshipsDetailsAsync("$id").await()
            val response2 = swapiRepository.getStarshipsDetailsAsync("$id2").await()
            if (response.isSuccessful) {
                val result = response.body()?.result?.properties
                val result2 = response2.body()?.result?.properties
                question = "Is ${result?.name} starship manufactured by ${result2?.manufacturer}?"
                answer = result?.manufacturer == result2?.manufacturer
                film = themeFilm(length, id)
            } else {
                Log.e("UnsuccessfulMsgError", "Unsuccessful response from API")
            }
        } catch (t: Throwable) {
            Log.e("onFailureMsgError", "onFailure called", t)
        }

        return@withContext Pair(question, film)
    }

    private suspend fun themeStarships2(length: Int): Pair<String, String> = withContext(Dispatchers.IO) {
        val availableNumbers = listOf(2, 3, 5, 9, 11, 10, 13, 15, 12, 17)
        val randomIndex = Random.nextInt(availableNumbers.size)
        val id = availableNumbers[randomIndex]
        val id2 = availableNumbers[randomIndex]
        var question = ""
        var film = ""
        try {
            val response = swapiRepository.getStarshipsDetailsAsync("$id").await()
            val response2 = swapiRepository.getStarshipsDetailsAsync("$id2").await()
            if (response.isSuccessful) {
                val result = response.body()?.result?.properties
                val result2 = response2.body()?.result?.properties
                question = "Does The ${result?.name} starship have a ${result2?.model} model?"
                answer = result?.model == result2?.model
                film = themeFilm(length, id)
            } else {
                Log.e("UnsuccessfulMsgError", "Unsuccessful response from API")
            }
        } catch (t: Throwable) {
            Log.e("onFailureMsgError", "onFailure called", t)
        }

        return@withContext Pair(question, film)
    }

    private suspend fun themeStarships3(length: Int): Pair<String, String> = withContext(Dispatchers.IO) {
        val availableNumbers = listOf(2, 3, 5, 9, 11, 10, 13, 15, 12, 17)
        val randomIndex = Random.nextInt(availableNumbers.size)
        val id = availableNumbers[randomIndex]
        val id2 = availableNumbers[randomIndex]
        var question = ""
        var film = ""
        try {
            val response = swapiRepository.getStarshipsDetailsAsync("$id").await()
            val response2 = swapiRepository.getStarshipsDetailsAsync("$id2").await()
            if (response.isSuccessful) {
                val result = response.body()?.result?.properties
                val result2 = response2.body()?.result?.properties
                question = "Is it really The ${result?.name} starship have ${result2?.crew} crew?"
                answer = result?.crew == result2?.crew
                film = themeFilm(length, id)
            } else {
                Log.e("UnsuccessfulMsgError", "Unsuccessful response from API")
            }
        } catch (t: Throwable) {
            Log.e("onFailureMsgError", "onFailure called", t)
        }

        return@withContext Pair(question, film)
    }

    override fun answerQuestion(isTrue: Boolean) {
        if (isTrue == answer) {
            quiz.updateScore(quiz.getScore()+1)
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
