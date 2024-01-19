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
import androidx.fragment.app.Fragment
import com.example.tubes2.databinding.FragmentQuizBinding

class Quiz2Fragment : Fragment(), QuizContract.View, SensorEventListener {

    private lateinit var presenter: QuizContract.Presenter
    private lateinit var binding: FragmentQuizBinding
    private lateinit var swapiRepository: SwapiRepository
    private var theme: String = ""
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

        val activity = requireActivity() as MainActivity
        val quizScoreModel = activity.quiz

        // Init presenter dan sensor
        presenter = QuizPresenter(this, requireContext(), swapiRepository, quizScoreModel)
        sensorManager = requireActivity().getSystemService(Context.SENSOR_SERVICE) as SensorManager
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

        // theme game and length theme
        this.theme = quizScoreModel.getTheme()

        val question = presenter.startQuiz(this.theme, 10, 2)

        binding.isiQuestion.text = question.first
        setImageFilm(question.second)
    }

    private fun setImageFilm(film: String){
        if (film == "A New Hope"){
            binding.imageContainer.setImageResource(R.drawable.anewhope)
        } else if (film == "The Empire Strikes Back"){
            binding.imageContainer.setImageResource(R.drawable.empirestrike)
        } else if (film == "Return of the Jedi"){
            binding.imageContainer.setImageResource(R.drawable.returnjedi)
        } else if (film == "The Phantom Menace"){
            binding.imageContainer.setImageResource(R.drawable.phantom)
        } else if (film == "Attack of the Clones"){
            binding.imageContainer.setImageResource(R.drawable.attackclone)
        } else if (film == "Revenge of the Sith"){
            binding.imageContainer.setImageResource(R.drawable.revengesith)
        }
    }
    override fun onDetection() {
        val quizFragment = Quiz3Fragment()
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
    }
}