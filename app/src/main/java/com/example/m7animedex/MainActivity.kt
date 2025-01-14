package com.example.m7animedex

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private lateinit var topAiringRecyclerView: RecyclerView
    private lateinit var mostPopularRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        topAiringRecyclerView = findViewById(R.id.topAiringGrid)
        mostPopularRecyclerView = findViewById(R.id.mostPopularGrid)

        // Añadir el Header
        supportFragmentManager.beginTransaction()
            .replace(R.id.header, FragmentHeader())
            .commit()

        // Añadir el Bottom Navigation Fragment
        supportFragmentManager.beginTransaction()
            .replace(R.id.footer, BottomNavigationFragment())
            .commit()

        // Configurar insets para Edge-to-Edge
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}
