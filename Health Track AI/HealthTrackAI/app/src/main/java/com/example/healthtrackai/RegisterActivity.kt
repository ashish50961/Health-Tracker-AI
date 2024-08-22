package com.example.healthtrackai

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class RegisterActivity : AppCompatActivity() {
    private lateinit var registerEmailAddress: EditText
    private lateinit var editTextTextPassword2: EditText
    private lateinit var editTextTextPassword3: EditText
    private lateinit var editTextPhone: EditText
    private lateinit var registerbutton: Button
    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        mAuth = FirebaseAuth.getInstance()

        // Initialize views
        registerEmailAddress = findViewById(R.id.registerEmailAddress)
        editTextTextPassword2 = findViewById(R.id.editPassword)
        editTextTextPassword3 = findViewById(R.id.repeatPassword)
        editTextPhone = findViewById(R.id.editTextPhone)
        registerbutton = findViewById(R.id.registerbutton)

        // Set click listener for the sign-up button
        registerbutton.setOnClickListener {
            val username = registerEmailAddress.text.toString().trim()
            val password = editTextTextPassword2.text.toString().trim()
            val confirmPassword = editTextTextPassword3.text.toString().trim()

            if (validateSignUp(username, password, confirmPassword)) {
                // Perform sign-up logic
                mAuth.createUserWithEmailAndPassword(username, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            // Sign up success
                            Toast.makeText(
                                this@RegisterActivity, "Sign up successful.",
                                Toast.LENGTH_SHORT
                            ).show()
                            val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                            startActivity(intent)
                            finish() // Finish the sign-up activity
                        } else {
                            // If sign up fails, display a message to the user.
                            Toast.makeText(
                                this@RegisterActivity,
                                "Authentication failed: ${task.exception?.message}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
            }
        }
    }

    // Dummy validation (replace with your actual validation logic)
    private fun validateSignUp(
        username: String,
        password: String,
        confirmPassword: String
    ): Boolean {
        if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            return false
        }

        if (password != confirmPassword) {
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
            return false
        }

        return true
    }
}