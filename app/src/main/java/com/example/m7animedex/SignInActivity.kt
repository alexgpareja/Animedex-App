package com.example.m7animedex

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class SignInActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_sign_in)

        // Ajustar la UI con respecto a las barras de estado y navegación
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Cargar el Fragment del Header
        supportFragmentManager.beginTransaction()
            .replace(R.id.header, FragmentHeader())
            .commit()

        // Cargar el Fragment del Bottom Navigation
        supportFragmentManager.beginTransaction()
            .replace(R.id.footer, BottomNavigationFragment())
            .commit()

        // Configuración de botones
        val signInButton = findViewById<Button>(R.id.sign_in_button)
        val logInButton = findViewById<Button>(R.id.log_in_button)

        // Si el usuario se registra, lo llevamos a la MainActivity
        signInButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish() // Cerramos esta actividad para evitar que el usuario vuelva con el botón "atrás"
        }

        // Si el usuario ya tiene cuenta, lo llevamos a la pantalla de LogIn
        logInButton.setOnClickListener {
            val intent = Intent(this, LogInActivity::class.java)
            startActivity(intent)
        }
    }
}
