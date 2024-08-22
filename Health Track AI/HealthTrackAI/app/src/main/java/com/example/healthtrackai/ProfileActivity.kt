package com.example.healthtrackai

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class ProfileActivity : AppCompatActivity() {

    private lateinit var usernameEditText: EditText
    private lateinit var saveButton: Button

    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        usernameEditText = findViewById(R.id.usernameEditText)
        saveButton = findViewById(R.id.saveButton)

        // Initialize Firebase Authentication instance
        auth = FirebaseAuth.getInstance()
        // Initialize Firebase Database instance
        database = FirebaseDatabase.getInstance()

        saveButton.setOnClickListener {
            saveUserProfile()
        }
    }

    private fun saveUserProfile() {
        val username = usernameEditText.text.toString().trim()

        if (username.isNotEmpty()) {
            // Get current user ID
            val userId = auth.currentUser?.uid
            if (userId != null) {
                // Get reference to user's profile in the database
                val userRef = database.reference.child("users").child(userId)

                // Update username in the database
                userRef.child("username").setValue(username)
                    .addOnSuccessListener {
                        // Username saved successfully
                        showToast("Profile updated successfully")

                        // Navigate to DataInputActivity upon successful profile update
                        startActivity(Intent(this@ProfileActivity, DataInputActivity::class.java))
                        finish() // Close the current activity
                    }
                    .addOnFailureListener { e ->
                        // Error saving username
                        showToast("Failed to update profile: ${e.message}")
                    }
            } else {
                // User is not authenticated
                showToast("User not authenticated")
            }
        } else {
            showToast("Please enter a username")
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
