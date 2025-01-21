package com.example.m7animedex

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class SignInActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.enableEdgeToEdge()
        setContentView(R.layout.activity_sign_in)

        ViewCompat.setOnApplyWindowInsetsListener(
            findViewById(R.id.main)
        ) { v: View, insets: WindowInsetsCompat ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val signInButton = findViewById<Button>(R.id.sign_in_button)
        val logInButton = findViewById<Button>(R.id.log_in_button)

        signInButton.setOnClickListener {
            val intent = Intent(
                this@SignInActivity,
                MainActivity::class.java
            )
            startActivity(intent)
            finish()
        }

        logInButton.setOnClickListener {
            val intent = Intent(
                this@SignInActivity,
                LogInActivity::class.java
            )
            startActivity(intent)
        }
    }
}
