package com.example.tubes2

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.widget.Toast
import android.os.Handler
import androidx.core.content.ContextCompat.getSystemService

interface QuizContract {
    interface View {
        fun showQuestion(question: String)
        fun showScore(score: Int)
    }

    interface Presenter {
        fun startQuiz(theme: String)
        fun answerQuestion(isTrue: Boolean)
        fun handleSensorEvent(event: SensorEvent)
    }
}

class QuizPresenter(private val view: QuizContract.View, private val context: Context) : QuizContract.Presenter, SensorEventListener {

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

    override fun startQuiz(theme: String) {
        // Implement logic to fetch questions from https://www.swapi.tech/ based on the selected theme
        // For simplicity, hardcoded questions are used in this example
        questions.clear()
        questions.add(QuizQuestion("Is it true?", true))
        questions.add(QuizQuestion("Is it false?", false))
        questions.add(QuizQuestion("Another true or false?", true))

        showCurrentQuestion()
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
