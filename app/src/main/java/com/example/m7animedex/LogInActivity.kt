package com.example.m7animedex

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class LogInActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_log_in)

        // Ajustar los márgenes de la UI con respecto a la barra de estado
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Cargar el Fragment del Header en su contenedor
        supportFragmentManager.beginTransaction()
            .replace(R.id.header, FragmentHeader())
            .commit()

        // Cargar el Fragment del Bottom Navigation en su contenedor
        supportFragmentManager.beginTransaction()
            .replace(R.id.footer, BottomNavigationFragment())
            .commit()

        // Configurar la navegación entre Login y SignIn
        val logInButton = findViewById<Button>(R.id.log_in_button)
        val signInButton = findViewById<Button>(R.id.sign_in_button)

        logInButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish() // Cierra esta activity para evitar que el usuario pueda volver atrás a Login
        }

        signInButton.setOnClickListener {
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
        }
    }
}
