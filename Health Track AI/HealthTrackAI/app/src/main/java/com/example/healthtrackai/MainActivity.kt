    package com.example.healthtrackai

    import ModelLoader
    import android.content.Intent
    import androidx.appcompat.app.AppCompatActivity
    import android.os.Bundle
    import android.widget.Button

    class MainActivity : AppCompatActivity() {
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_main)

            val loginButton = findViewById<Button>(R.id.loginButton)
            val modelLoader = ModelLoader(this)
            val interpreter = modelLoader.loadModel()

            // Set OnClickListener for the loginButton
            loginButton.setOnClickListener {
                // Start LoginActivity when loginButton is clicked
                val intent = Intent(this@MainActivity, LoginActivity::class.java)
                startActivity(intent)
            }
        }
    }
