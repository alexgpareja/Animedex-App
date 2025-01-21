package com.example.m7animedex

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.m7animedex.R.id.main
import org.json.JSONArray
import java.net.URL
import android.graphics.BitmapFactory

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
        topAiringRecyclerView.layoutManager = LinearLayoutManager(this)
        val topAiringAnimeList = loadAnimeDataFromJson()
        topAiringRecyclerView.adapter = AnimeAdapter(topAiringAnimeList)

        val decorator = DividerItemDecoration(this, LinearLayoutManager.VERTICAL)
        topAiringRecyclerView.addItemDecoration(decorator)

        // Configurar insets para Edge-to-Edge
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun loadAnimeDataFromJson(): List<Anime> {
        val animeList = mutableListOf<Anime>()
        try {
            val json = assets.open("animes.json").bufferedReader().use { it.readText() }
            val jsonArray = JSONArray(json)
            for (i in 0 until jsonArray.length()) {
                val jsonObject = jsonArray.getJSONObject(i)
                val name = jsonObject.getString("name")
                val imageUrl = jsonObject.getString("imageUrl")
                animeList.add(Anime(name, imageUrl))
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return animeList
    }
}