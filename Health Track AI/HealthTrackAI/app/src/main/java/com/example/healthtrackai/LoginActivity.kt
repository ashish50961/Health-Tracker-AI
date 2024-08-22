package com.example.healthtrackai

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
    private lateinit var editTextTextEmailAddress: EditText
    private lateinit var editTextTextPassword: EditText
    private lateinit var loginbutton: Button
    private lateinit var registerbutton: Button
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        // Initialize views
        editTextTextEmailAddress = findViewById(R.id.editTextTextEmailAddress)
        editTextTextPassword = findViewById(R.id.editTextTextPassword)
        loginbutton = findViewById(R.id.Signinbutton)
        registerbutton = findViewById(R.id.Registerbutton)

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()

        // Set click listener for the "Forgot Password"
        val textViewResetPassword = findViewById<TextView>(R.id.textView3)
        textViewResetPassword.setOnClickListener {
            val intent = Intent(this@LoginActivity,ResetpasswordActivity::class.java)
            startActivity(intent)
        }

        // Handle Login Button Click
        loginbutton.setOnClickListener {
            // Retrieve email and password from your UI elements
            val email = editTextTextEmailAddress.text.toString().trim()
            val password = editTextTextPassword.text.toString().trim()

            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success
                        Toast.makeText(
                            this@LoginActivity, "Login successful.",
                            Toast.LENGTH_SHORT
                        ).show()
                        val intent = Intent(this@LoginActivity, DataInputActivity::class.java)
                        startActivity(intent)
                    } else {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(
                            this@LoginActivity, "Authentication failed.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        }

        // Handle Sign Up Text Click
        registerbutton.setOnClickListener {
            val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(intent)
        }
    }
}
