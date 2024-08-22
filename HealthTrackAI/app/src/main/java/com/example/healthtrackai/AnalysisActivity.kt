package com.example.healthtrackai

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class AnalysisActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_analysis)

        // Extract data from intent extras
        val heartRate = intent.getDoubleExtra("HEART_RATE", 0.0)
        val sleepHours = intent.getDoubleExtra("SLEEP_HOURS", 0.0)
        val activityLevel = intent.getStringExtra("ACTIVITY_LEVEL") ?: "Unknown"

        // Perform simple rule-based analysis using the provided data
        val analysisResult = performSimpleAnalysis(heartRate, sleepHours, activityLevel)

        // Display analysis result in a more user-friendly format
        val textViewAnalysisResult = findViewById<TextView>(R.id.textViewAnalysisResult)
        textViewAnalysisResult.text = analysisResult

        // Set OnClickListener to navigate to SummaryActivity when clicked
        val moreTextView: TextView = findViewById(R.id.textView6)
        moreTextView.setOnClickListener {
            val intent = Intent(this@AnalysisActivity, TrackHealthActivity::class.java)
            intent.putExtra("ANALYSIS_RESULT", analysisResult)
            startActivity(intent)
        }
    }

    private fun performSimpleAnalysis(heartRate: Double, sleepHours: Double, activityLevel: String): String {
        val heartRateFeedback = when {
            heartRate < 60 -> "Your heart rate is below the normal range. Consider consulting a healthcare provider."
            heartRate > 100 -> "Your heart rate is above the normal range. Ensure you are not overly stressed."
            else -> "Your heart rate is within the normal range. Keep up the good work!"
        }

        val sleepFeedback = when {
            sleepHours < 6 -> "You need more sleep. Aim for at least 7-8 hours per night."
            sleepHours > 9 -> "You might be oversleeping. Try to regulate your sleep patterns."
            else -> "Your sleep duration is good. Maintain this healthy habit!"
        }

        val activityFeedback = when (activityLevel) {
            "Low" -> "Your activity level is low. Try to incorporate more physical activity into your daily routine."
            "Medium" -> "You have a moderate activity level. Keep it up!"
            "High" -> "Great job staying active! Continue with your high level of physical activity."
            else -> "Unknown activity level. Ensure you are getting enough exercise."
        }

        return "Health Analysis:\n\n$heartRateFeedback\n\n$sleepFeedback\n\n$activityFeedback"
    }

    companion object {
        private const val TAG = "AnalysisActivity"
    }
}
