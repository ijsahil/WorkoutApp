package com.example.a7minuteworkout

class ExerciseModel(
    private var id: Int,
    private var exerciseName: String,
    private var imgExercise: Int,
    private var isSelected: Boolean = false,
    private var isDone: Boolean = false
) {
    fun setId(id: Int) {
        this.id = id
    }

    fun getId(): Int {
        return this.id
    }

    fun setExerciseImage(img: Int) {
        this.imgExercise = img
    }

    fun getImageExercise(): Int {
        return this.imgExercise
    }

    fun setIsSelected(value: Boolean) {
        this.isSelected = value
    }

    fun getIsSelected(): Boolean {
        return this.isSelected
    }

    fun setIsDone(value: Boolean) {
        this.isDone = value
    }

    fun getIsDone(): Boolean {
        return this.isDone
    }
    fun setExerciseName(name: String){
        exerciseName = name
    }
    fun getExerciseName() : String{
        return exerciseName
    }
}