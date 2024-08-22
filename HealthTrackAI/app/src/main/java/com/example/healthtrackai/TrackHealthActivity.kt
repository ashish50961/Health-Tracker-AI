package com.example.healthtrackai

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class TrackHealthActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_track_health)

        val editTextHeartRate = findViewById<EditText>(R.id.editTextHeartRate)
        val editTextBloodPressure = findViewById<EditText>(R.id.editTextBloodPressure)
        val editTextBloodSugar = findViewById<EditText>(R.id.editTextBloodSugar)
        val editTextBodyTemperature = findViewById<EditText>(R.id.editTextBodyTemperature)
        val editTextBMI = findViewById<EditText>(R.id.editTextBMI)
        val editTextHydration = findViewById<EditText>(R.id.editTextHydration)
        val editTextWeight = findViewById<EditText>(R.id.editTextWeight)
        val buttonTrack = findViewById<Button>(R.id.buttonTrack)

        buttonTrack.setOnClickListener {
            val heartRate = editTextHeartRate.text.toString().toIntOrNull() ?: 0
            val bloodPressure = editTextBloodPressure.text.toString().toIntOrNull() ?: 0
            val bloodSugar = editTextBloodSugar.text.toString().toFloatOrNull() ?: 0f
            val bodyTemperature = editTextBodyTemperature.text.toString().toFloatOrNull() ?: 0f
            val bmi = editTextBMI.text.toString().toFloatOrNull() ?: 0f
            val hydration = editTextHydration.text.toString().toFloatOrNull() ?: 0f
            val weight = editTextWeight.text.toString().toFloatOrNull() ?: 0f

            val feedback = generateDetailedFeedback(heartRate, bloodPressure, bloodSugar, bodyTemperature, bmi, hydration, weight)

            // Start SummaryActivity with the feedback
            val intent = Intent(this, SummaryActivity::class.java)
            intent.putExtra("FEEDBACK", feedback)
            startActivity(intent)
        }
    }

    private fun generateDetailedFeedback(
        heartRate: Int,
        bloodPressure: Int,
        bloodSugar: Float,
        bodyTemperature: Float,
        bmi: Float,
        hydration: Float,
        weight: Float
    ): String {
        return buildString {
            append("Health Summary:\n\n")
            append("Heart Rate: ${heartRate} bpm\n")
            append("Blood Pressure: ${bloodPressure} mmHg\n")
            append("Blood Sugar: ${bloodSugar} mg/dL\n")
            append("Body Temperature: ${bodyTemperature} °C\n")
            append("BMI: ${bmi}\n")
            append("Hydration Level: ${hydration} L\n")
            append("Weight: ${weight} kg\n\n")

            // Heart Rate Feedback
            if (heartRate < 60) append("⚠️ Heart Rate is below normal. A low heart rate might indicate bradycardia. Consult a healthcare provider to assess any potential concerns.\n")
            else if (heartRate > 100) append("⚠️ Heart Rate is elevated. Consider monitoring your stress levels or engaging in regular physical activity to manage your heart rate.\n")

            // Blood Pressure Feedback
            if (bloodPressure < 90) append("⚠️ Blood Pressure is low. This might lead to dizziness or fainting. Ensure adequate salt intake and consult a healthcare provider if symptoms persist.\n")
            else if (bloodPressure > 120) append("⚠️ Blood Pressure is high. High blood pressure can be a risk factor for cardiovascular diseases. Regular monitoring and lifestyle changes are recommended.\n")

            // Blood Sugar Feedback
            if (bloodSugar < 70) append("⚠️ Blood Sugar level is low. Symptoms of hypoglycemia include shakiness and confusion. Increase your intake of carbohydrates and consult your healthcare provider.\n")
            else if (bloodSugar > 140) append("⚠️ Blood Sugar level is high. Consider reviewing your diet and exercise routine. Frequent monitoring and medical consultation might be necessary.\n")

            // Body Temperature Feedback
            if (bodyTemperature < 36.0) append("⚠️ Body Temperature is low. This could indicate hypothermia or other medical conditions. Dress warmly and consult a healthcare provider if symptoms persist.\n")
            else if (bodyTemperature > 37.5) append("⚠️ Body Temperature is elevated. This might indicate a fever. Stay hydrated and consult a healthcare provider if the temperature persists or if you have other symptoms.\n")

            // BMI Feedback
            if (bmi < 18.5) append("⚠️ BMI indicates underweight. This could be due to insufficient nutrition. Consider consulting a nutritionist to improve your diet and health.\n")
            else if (bmi > 25) append("⚠️ BMI indicates overweight or obesity. Engage in regular exercise and adopt a balanced diet to manage your weight.\n")

            // Hydration Feedback
            if (hydration < 2.0) append("⚠️ Hydration level is low. Increase your water intake to stay hydrated. Aim for at least 2 liters of water daily.\n")
            else if (hydration > 4.0) append("⚠️ Hydration level is high. While hydration is important, excessive water intake may lead to imbalance in electrolytes. Ensure a balanced intake.\n")
        }
    }
}
