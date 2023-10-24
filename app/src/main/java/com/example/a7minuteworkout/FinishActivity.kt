package com.example.a7minuteworkout

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.a7minuteworkout.databinding.ActivityFinishBinding
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class FinishActivity : AppCompatActivity() {
    private var binding: ActivityFinishBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFinishBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        setSupportActionBar(binding?.toolbarFinishExercise)

        if (supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
        binding?.toolbarFinishExercise?.setNavigationOnClickListener {
            onBackPressed()
        }
        val historyDao = (application as ExerciseApp).db.historyDao()
        addHistoryRecord(historyDao)
        binding?.btnFinish?.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

    }

    private fun addHistoryRecord(historyDao: HistoryDao) {
        val c = Calendar.getInstance()
        val dateTime = c.time
        val sdf = SimpleDateFormat("dd MMM yyyy HH:mm:ss", Locale.getDefault())
        val date = sdf.format(dateTime)
        lifecycleScope.launch {
            try {
                historyDao.insert(HistoryEntity(date))
                Log.e("msg","Data inserted successfully")
            } catch (e: Exception) {
                e.printStackTrace()
                Log.e("error msg","Data insertion failed")
            }
        }
    }

    override fun onDestroy() {
        binding = null
        super.onDestroy()
    }
}