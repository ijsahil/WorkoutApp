package com.example.a7minuteworkout

import android.content.Intent
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.a7minuteworkout.databinding.ActivityMainBinding
import java.util.Locale

class MainActivity : AppCompatActivity(), TextToSpeech.OnInitListener {
    private var binding: ActivityMainBinding? = null
    private var ttS: TextToSpeech? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        binding?.flStart?.setOnClickListener {
            val intent = Intent(this, ExerciseActivity::class.java)
            startActivity(intent)
        }
        binding?.flBmi?.setOnClickListener {
            val intent = Intent(this, BmiCalculator::class.java)
            startActivity(intent)
        }
        binding?.flHistory?.setOnClickListener {
            val intent = Intent(this, HistoryActivity::class.java)
            startActivity(intent)
        }
        ttS = TextToSpeech(this, this)
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            var result = ttS!!.setLanguage(Locale.US)
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "The language specified is not supported!!")
            } else {
                val speechRate =
                    0.7f // Adjust this value as needed (0.5 is slower, 1.0 is normal, 2.0 is faster)
                ttS?.setSpeechRate(speechRate)
                //  onSpeak("Press Start to begin the workout session.")
            }
        } else {
            Log.e("TTS", "Initialization Failed")
        }
    }

    private fun onSpeak(text: String) {
        ttS?.speak(text, TextToSpeech.QUEUE_FLUSH, null, "")
    }

    override fun onDestroy() {
        super.onDestroy()
        if (ttS != null) {
            ttS?.stop()
            ttS?.shutdown()
        }
        binding = null
    }
}