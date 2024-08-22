package com.example.healthtrackai

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class SummaryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_summary)

        val textViewSummary = findViewById<TextView>(R.id.summaryTextView)
        val logoutButton = findViewById<Button>(R.id.logoutButton)

        // Get the feedback from the Intent
        val feedback = intent.getStringExtra("FEEDBACK")

        // Display the feedback
        textViewSummary.text = feedback

        logoutButton.setOnClickListener {
            performLogout()
        }

    }

    private fun performLogout() {
        // Clear user data or session
        // For example, clearing shared preferences
        val sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()

        // Redirect to LoginActivity or MainActivity
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }
}
