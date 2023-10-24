package com.example.a7minuteworkout

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.a7minuteworkout.databinding.ActivityBmiCalculatorBinding

class BmiCalculator : AppCompatActivity() {
    private var heightInCm: Boolean = false

    private var binding: ActivityBmiCalculatorBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBmiCalculatorBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        setSupportActionBar(binding?.toolbarBmiCalculator)
        if (supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.title = "CALCULATE BMI"
        }
        binding?.toolbarBmiCalculator?.setNavigationOnClickListener {
            onBackPressed()
        }
        binding?.btnCalculate?.setOnClickListener {
            val  weight = binding?.etWeight?.text.toString()
            if (heightInCm) {
              if(weight.isNotEmpty() && binding?.etHeight?.text.toString().isNotEmpty())
                calculateBmiCm()
            }
           else{
               if (weight.isNotEmpty() && binding?.etFeet?.text.toString().isNotEmpty() ||  binding?.etInch?.text.toString().isNotEmpty())
               calculateBmiFeet()
            }
        }
        binding?.rbHeightInCm?.setOnCheckedChangeListener { _, isChecked ->
            // Your code to handle the checked state change here
            if (isChecked) {
                heightInCm = true
                // The RadioButton is checked
                // Add your code for what should happen when it's checked
                setUpHeightInCm()
            }
        }
        binding?.rbHeightInFt?.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                heightInCm = false
                setUpHeightInFeet()
            }
        }
    }

    private fun calculateBmiFeet() {
        val bmiText: String
        val bmiMessage: String
        val weightText = binding?.etWeight?.text.toString()
        val feetText = binding?.etFeet?.text.toString()
        val inchesText = binding?.etInch?.text.toString()

        val weight = if (weightText.isNotEmpty()) {
            weightText.toDouble()
        } else {
            0.0
        }

        val feet = if (feetText.isNotEmpty()) {
            feetText.toDouble()
        } else {
            0.0
        }

        val inches = if (inchesText.isNotEmpty()) {
            inchesText.toDouble()
        } else {
            0.0
        }
        val heightMeters = (feet * 0.3048) + (inches * 0.0254)
        val bmi = weight / (heightMeters * heightMeters)
        binding?.tvTitle?.visibility = View.VISIBLE
        binding?.tvBmi?.text = String.format("%.2f", bmi)

        if (bmi < 18.5) {
            // Handle BMI less than 18.5
            bmiText = "Underweight"
            bmiMessage =
                "Oops! You really need to take care of yourself! Good nutrition maybe!"
        } else if (bmi >= 25) {
            bmiText = "Overweight"
            bmiMessage =
                "Oops! you really need to take care of yourself! Workout maybe!"
            // Handle empty weight or height input
        } else {
            bmiText = "Normal"
            bmiMessage = "Congratulations! You are in a good shape"
        }
        binding?.tvBmiText?.text = bmiText
        binding?.tvBmiMessage?.text = bmiMessage

    }

    private fun setUpHeightInFeet() {
        binding?.etHeight?.setText("")
        binding?.tvTitle?.visibility = View.INVISIBLE
        binding?.tvBmiText?.text = ""
        binding?.tvBmi?.text = ""
        binding?.tvBmiMessage?.text = ""
        binding?.etFeet?.visibility = View.VISIBLE
        binding?.etInch?.visibility = View.VISIBLE
        binding?.et2?.visibility = View.GONE
    }

    private fun setUpHeightInCm() {
        binding?.etFeet?.text?.clear()
        binding?.etInch?.setText("")
        binding?.tvTitle?.visibility = View.INVISIBLE
        binding?.tvBmiText?.text = ""
        binding?.tvBmi?.text = ""
        binding?.tvBmiMessage?.text = ""
        binding?.et2?.visibility = View.VISIBLE
        binding?.etFeet?.visibility = View.GONE
        binding?.etInch?.visibility = View.GONE
    }

    private fun calculateBmiCm() {
        val bmiText: String
        val bmiMessage: String
        val weightText = binding?.etWeight?.text.toString()
        val heightText = binding?.etHeight?.text.toString()
        val weight = if (weightText.isNotEmpty()) {
            weightText.toDouble()
        } else {
            0.0
        }
        val heightInCm = if (heightText.isNotEmpty()){
            heightText.toDouble()
        }else{
            0.0
        }

        // Convert height from centimeters to meters
        val heightInMeter = heightInCm / 100.0

        val bmi = weight / (heightInMeter * heightInMeter)
        // Set the calculated BMI to your TextView
        binding?.tvTitle?.visibility = View.VISIBLE
        binding?.tvBmi?.text = String.format("%.2f", bmi)

        if (bmi < 18.5) {
            // Handle BMI less than 18.5
            bmiText = "Underweight"
            bmiMessage =
                "Oops! You really need to take care of yourself! Good nutrition maybe!"
        } else if (bmi >= 25) {
            bmiText = "Overweight"
            bmiMessage =
                "Oops! you really need to take care of yourself! Workout maybe!"
            // Handle empty weight or height input
        } else {
            bmiText = "Normal"
            bmiMessage = "Congratulations! You are in a good shape"
        }
        binding?.tvBmiText?.text = bmiText
        binding?.tvBmiMessage?.text = bmiMessage
    }
}