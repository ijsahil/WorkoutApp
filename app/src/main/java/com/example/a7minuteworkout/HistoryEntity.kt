package com.example.a7minuteworkout

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate
import java.time.LocalTime

@Entity(tableName = "history-table")
data class HistoryEntity(
        @PrimaryKey
        val date : String
)