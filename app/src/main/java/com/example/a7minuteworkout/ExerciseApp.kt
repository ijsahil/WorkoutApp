package com.example.a7minuteworkout

import android.app.Application

class ExerciseApp : Application() {
    val db by lazy {
        HistoryDatabase.getInstance(this)
    }
}