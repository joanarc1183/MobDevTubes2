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

class QuizFragment : Fragment(), QuizContract.View, SensorEventListener {

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

        // Initialize presenter and sensor
        presenter = QuizPresenter(this, requireContext(), swapiRepository, quizScoreModel)
        sensorManager = requireActivity().getSystemService(Context.SENSOR_SERVICE) as SensorManager
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

        // theme game and length theme
        this.theme = quizScoreModel.getTheme()

        val question = presenter.startQuiz(this.theme, 10, 1)

        binding.isiQuestion.text = question

    }

    override fun onDetection() {
        val quizFragment = Quiz2Fragment()
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
}