package com.example.a7minuteworkout

import android.app.Dialog
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.a7minuteworkout.databinding.ActivityExerciseBinding
import com.example.a7minuteworkout.databinding.CustomExitDialogBinding
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

class ExerciseActivity : AppCompatActivity(), TextToSpeech.OnInitListener {
    private var binding: ActivityExerciseBinding? = null
    private var countDownTimer: CountDownTimer? = null
    private var exerciseTimer: CountDownTimer? = null
    private var exerciseList: ArrayList<ExerciseModel>? = null
    private var exercisePosition = -1
    private var restDuration: Long = 10
    private var exerciseDuration: Long = 30
    private var ttS: TextToSpeech? = null
    private var player: MediaPlayer? = null
    private var exerciseAdapter: ExerciseStatusAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExerciseBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        setSupportActionBar(binding?.toolbarExercise)
        ttS = TextToSpeech(this, this)

        exerciseList = Constants.exerciseDefaultValue()


        if (supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
        binding?.toolbarExercise?.setNavigationOnClickListener {
            onBackPressed()
        }


        setUpRestView()
        setupExerciseStatusRecycleView()

    }

    private fun restView() {
        countDownTimer = object : CountDownTimer(
            restDuration * 1000,
            1000
        ) {
            override fun onTick(millisUntilFinished: Long) {
                playSound()
                binding?.tvTimer?.text = (millisUntilFinished / 1000).toString()
                binding?.progressBar?.progress = (millisUntilFinished / 1000).toInt()
            }

            override fun onFinish() {
                exercisePosition++
                exerciseList!![exercisePosition].setIsSelected(true)
                exerciseAdapter!!.notifyDataSetChanged()
                setUpExerciseView()
            }
        }.start()
    }

    private fun exerciseView() {
        exerciseTimer = object : CountDownTimer(exerciseDuration * 1000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                binding?.tvExerciseTimer?.text = (millisUntilFinished / 1000).toString()
                binding?.progressBarExercise?.progress = (millisUntilFinished / 1000).toInt()
            }

            @RequiresApi(Build.VERSION_CODES.O)
            override fun onFinish() {
                exerciseList!![exercisePosition].setIsSelected(false)
                exerciseList!![exercisePosition].setIsDone(true)
                exerciseAdapter!!.notifyDataSetChanged()


                if (exercisePosition < exerciseList?.size!! - 1) {
                    setUpRestView()
                } else {

                    val intent = Intent(this@ExerciseActivity, FinishActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
        }.start()
    }


    private fun setUpRestView() {


        binding?.flRestView?.visibility = View.VISIBLE
        binding?.tvTitle?.visibility = View.VISIBLE
        binding?.flExerciseView?.visibility = View.INVISIBLE
        binding?.exerciseImage?.visibility = View.INVISIBLE
        binding?.tvExerciseName?.visibility = View.INVISIBLE
        binding?.tvUpcomingTitle?.visibility = View.VISIBLE
        binding?.tvUpcomingExercise?.visibility = View.VISIBLE
        if (countDownTimer != null) {
            countDownTimer?.cancel()
        }
        val upcomingExerciseName = exerciseList!![exercisePosition + 1].getExerciseName()
        binding?.tvUpcomingExercise?.text = upcomingExerciseName
        restView()
    }

    private fun setUpExerciseView() {
        binding?.flRestView?.visibility = View.INVISIBLE
        binding?.tvTitle?.visibility = View.INVISIBLE
        binding?.tvExerciseName?.visibility = View.VISIBLE
        binding?.flExerciseView?.visibility = View.VISIBLE
        binding?.exerciseImage?.visibility = View.VISIBLE
        binding?.tvUpcomingTitle?.visibility = View.INVISIBLE
        binding?.tvUpcomingExercise?.visibility = View.INVISIBLE

        if (exerciseTimer != null) {
            exerciseTimer?.cancel()
        }
        binding?.exerciseImage?.setImageResource(
            exerciseList?.get(exercisePosition)!!.getImageExercise()
        )
        binding?.tvExerciseName?.text = exerciseList!![exercisePosition].getExerciseName()
        onSpeak("${exerciseList!![exercisePosition].getExerciseName()}$exerciseDuration seconds")
        exerciseView()
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
            }
        } else {
            Log.e("TTS", "Initialization Failed")
        }
    }

    private fun onSpeak(text: String) {
        ttS?.speak(text, TextToSpeech.QUEUE_FLUSH, null, "")
    }

    private fun playSound() {
        try {
            val soundUri =
                Uri.parse("android.resource://com.example.a7minuteworkout/${R.raw.start}")
            player = MediaPlayer.create(applicationContext, soundUri)
            player?.isLooping = false
            player?.start()

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun setupExerciseStatusRecycleView() {
        binding?.rvExerciseStatus?.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        exerciseAdapter = ExerciseStatusAdapter(exerciseList!!)
        binding?.rvExerciseStatus?.adapter = exerciseAdapter


    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        showExitDialog()
    }

    private fun showExitDialog() {
        val customDialog = Dialog(this)
        val dialogBinding = CustomExitDialogBinding.inflate(layoutInflater)
        customDialog.setContentView(dialogBinding.root)
        dialogBinding.viewStay.setOnClickListener {
            customDialog.dismiss()
        }
        dialogBinding.viewExit.setOnClickListener {
            this@ExerciseActivity.finish()
            customDialog.dismiss()
        }
        customDialog.show()
        customDialog.setCancelable(false)
    }


    override fun onDestroy() {
        if (countDownTimer != null) {
            countDownTimer?.cancel()
        }
        if (exerciseTimer != null) {
            exerciseTimer?.cancel()
        }
        if (ttS != null) {
            ttS?.stop()
            ttS?.shutdown()
        }
        if (player != null) {
            player?.stop()
        }
        super.onDestroy()
        binding = null
    }
}