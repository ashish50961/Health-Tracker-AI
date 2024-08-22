package com.example.healthtrackai

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class DataInputActivity : AppCompatActivity() {

    private lateinit var editTextHeartRate: EditText
    private lateinit var editTextSleepHours: EditText
    private lateinit var editTextActivityLevel: EditText
    private lateinit var buttonSubmit: Button
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data_input)

        // Initialize views
        editTextHeartRate = findViewById(R.id.editTextHeartRate)
        editTextSleepHours = findViewById(R.id.editTextSleepHours)
        editTextActivityLevel = findViewById(R.id.editTextActivityLevel)
        buttonSubmit = findViewById(R.id.buttonSubmit)
        progressBar = findViewById(R.id.progressBar)

        // Set click listener for the submit button
        buttonSubmit.setOnClickListener {
            // Validate input data
            if (validateInput()) {
                // If input is valid, show progress bar
                showProgressBar()

                // Pass data to AnalysisActivity
                submitData()
            }
        }
    }

    // Validate input data
    private fun validateInput(): Boolean {
        val heartRateText = editTextHeartRate.text.toString()
        val sleepHoursText = editTextSleepHours.text.toString()
        val activityLevelText = editTextActivityLevel.text.toString()

        // Perform validation
        if (heartRateText.isEmpty() || sleepHoursText.isEmpty() || activityLevelText.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            return false
        }

        return true
    }

    // Show progress bar
    private fun showProgressBar() {
        progressBar.visibility = ProgressBar.VISIBLE
    }

    // Hide progress bar
    private fun hideProgressBar() {
        progressBar.visibility = ProgressBar.GONE
    }

    // Submit data and move to AnalysisActivity
    private fun submitData() {
        val heartRateText = editTextHeartRate.text.toString().toDouble()
        val sleepHoursText = editTextSleepHours.text.toString().toDouble()
        val activityLevelText = editTextActivityLevel.text.toString()

        val intent = Intent(this, AnalysisActivity::class.java).apply {
            putExtra("HEART_RATE", heartRateText)
            putExtra("SLEEP_HOURS", sleepHoursText)
            putExtra("ACTIVITY_LEVEL", activityLevelText)
        }
        startActivity(intent)

        // Hide progress bar after submission is complete
        hideProgressBar()
    }
}
