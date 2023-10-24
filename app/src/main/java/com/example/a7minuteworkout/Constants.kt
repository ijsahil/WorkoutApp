package com.example.a7minuteworkout

object Constants {
    fun exerciseDefaultValue(): ArrayList<ExerciseModel> {
        val list: ArrayList<ExerciseModel> = ArrayList()
        val exercise1 = (ExerciseModel(1, "Sitting quad stretch", R.drawable.exercise1))
        list.add(exercise1)
        val exercise2 = (ExerciseModel(2, "Lower back stretch", R.drawable.exercise2))
        list.add(exercise2)
        val exercise3 = (ExerciseModel(3, "Cat cow stretch", R.drawable.exercise3))
        list.add(exercise3)
        val exercise4 = (ExerciseModel(4, "Core Strength Intensive", R.drawable.exercise4))
        list.add(exercise4)
        val exercise5 = (ExerciseModel(5, "Pilates Power", R.drawable.exercise5))
        list.add(exercise5)
        val exercise6 = (ExerciseModel(6, "Flexibility Fusion", R.drawable.exercise6))
        list.add(exercise6)
        val exercise7 = (ExerciseModel(7, "Push ups", R.drawable.exercise7))
        list.add(exercise7)
        val exercise8 = (ExerciseModel(8, "Thigh planks", R.drawable.exercise8))
        list.add(exercise8)
        val exercise9 = (ExerciseModel(9, "Side way stretch", R.drawable.exercise9))
        list.add(exercise9)
        val exercise10 = (ExerciseModel(10, "Upper body stretch", R.drawable.exercise10))
        list.add(exercise10)
        val exercise11 = (ExerciseModel(11, "Lower pushups", R.drawable.exercise11))
        list.add(exercise11)
        return list
    }
}