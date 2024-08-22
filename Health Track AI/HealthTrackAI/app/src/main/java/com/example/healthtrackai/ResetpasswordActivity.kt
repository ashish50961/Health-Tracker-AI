package com.example.healthtrackai

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class ResetpasswordActivity : AppCompatActivity() {
    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resetpassword)

        mAuth = FirebaseAuth.getInstance()


        val editTextEmail = findViewById<EditText>(R.id.resetpasswordEmailAddress)
        val buttonResetPassword = findViewById<Button>(R.id.Resetbutton)

        buttonResetPassword.setOnClickListener {
            val email = editTextEmail.text.toString()

            if (email.isNotEmpty()) {
                mAuth.sendPasswordResetEmail(email)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(
                                this@ResetpasswordActivity,
                                "Password reset email sent. Check your email.",
                                Toast.LENGTH_SHORT
                            ).show()
                            finish() // Close the activity after sending the reset email
                        } else {
                            Toast.makeText(
                                this@ResetpasswordActivity,
                                "Failed to send reset email. ${task.exception?.message}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
            } else {
                Toast.makeText(
                    this@ResetpasswordActivity,
                    "Please enter your email.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}
