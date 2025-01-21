package com.example.m7animedex

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.RecyclerView

class ListasActivity : AppCompatActivity() {

    private lateinit var listsRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listas)

        // Inicializar el RecyclerView

        // Reemplazar el contenedor del footer con el BottomNavigationFragment
        supportFragmentManager.beginTransaction()
            .replace(R.id.footer, BottomNavigationFragment())
            .commit()

        // Configurar padding para evitar solapamientos con las barras del sistema
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}
