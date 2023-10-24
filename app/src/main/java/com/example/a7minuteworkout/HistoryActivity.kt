package com.example.a7minuteworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.a7minuteworkout.databinding.ActivityHistoryBinding
import kotlinx.coroutines.launch

class HistoryActivity : AppCompatActivity() {
    private var binding : ActivityHistoryBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        setSupportActionBar(binding?.toolbarHistory)

        if (supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.title = "HISTORY"
        }
        binding?.toolbarHistory?.setNavigationOnClickListener {
            onBackPressed()
        }
        val historyDao = (application as ExerciseApp).db.historyDao()
        lifecycleScope.launch {
            historyDao.fetchAllHistory().collect{
                val list = ArrayList(it)
                setUpRecycleViewDate(list)
            }
        }
    }
    private fun setUpRecycleViewDate(
        historyList : ArrayList<HistoryEntity>
    ){
        if(historyList.isNotEmpty()){
            val historyAdapter = HistoryAdapter(historyList)
            binding?.rvHistoryItems?.layoutManager = LinearLayoutManager(this)
            binding?.rvHistoryItems?.adapter = historyAdapter
            binding?.rvHistoryItems?.visibility = View.VISIBLE
            binding?.tvRecords?.visibility = View.VISIBLE
            binding?.tvNoRecords?.visibility = View.GONE
        }else{
            binding?.rvHistoryItems?.visibility = View.GONE
            binding?.tvRecords?.visibility = View.GONE
            binding?.tvNoRecords?.visibility = View.VISIBLE
        }
    }
}