package com.example.a7minuteworkout

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.a7minuteworkout.databinding.RecycleViewHistoryBinding

class HistoryAdapter(private val historyItems: List<HistoryEntity>) :
    RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {
    class ViewHolder(binding: RecycleViewHistoryBinding) : RecyclerView.ViewHolder(binding.root) {
        val viewSerialNo = binding.tvCount
        val viewDateTime = binding.tvDateTime
        val llMain = binding.llMain
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            RecycleViewHistoryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return historyItems.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val context = holder.itemView.context
        val historyItem = historyItems[position]
        holder.viewDateTime.text = historyItem.date
        holder.viewSerialNo.text = "${position + 1}"
        if (position % 2 == 0) {
            holder.llMain.setBackgroundColor(ContextCompat.getColor(context, R.color.lightGray))
        } else {
            holder.llMain.setBackgroundColor(ContextCompat.getColor(context, R.color.white))
        }
    }

}