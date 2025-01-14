package com.example.m7animedex

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat.setOnApplyWindowInsetsListener
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.m7animedex.R.id.main

class MainActivity : AppCompatActivity() {
    private lateinit var topAiringRecyclerView: RecyclerView
    private lateinit var mostPopularRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        topAiringRecyclerView = findViewById(R.id.topAiringGrid)
        mostPopularRecyclerView = findViewById(R.id.mostPopularGrid)

        supportFragmentManager.beginTransaction()
            .replace(R.id.header, FragmentHeader())
            .commit()
        supportFragmentManager.beginTransaction()
            .replace(R.id.footer, FooterFragment())
            .commit()

        setOnApplyWindowInsetsListener(findViewById(main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}