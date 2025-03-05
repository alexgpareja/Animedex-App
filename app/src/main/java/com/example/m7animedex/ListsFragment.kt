package com.example.m7animedex

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.m7animedex.data.AnimeAPI
import com.example.m7animedex.data.model.Fav
import com.example.m7animedex.data.model.Anime
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ListsFragment : Fragment() {

    private lateinit var searchEditText: EditText
    private lateinit var buttonPlanned: Button
    private lateinit var buttonWatching: Button
    private lateinit var buttonWatched: Button
    private lateinit var recyclerView: RecyclerView
    private lateinit var favAnimeAdapter: FavAnimeAdapter
    private val animeService = AnimeAPI.getService() // Usa el Singleton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_lists, container, false)

        searchEditText = view.findViewById(R.id.searchBox)
        buttonPlanned = view.findViewById(R.id.buttonPlanned)
        buttonWatching = view.findViewById(R.id.buttonWatching)
        buttonWatched = view.findViewById(R.id.buttonWatched)
        recyclerView = view.findViewById(R.id.recyclerViewLists)

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        favAnimeAdapter = FavAnimeAdapter(mutableListOf()) { anime ->
            Toast.makeText(requireContext(), "Seleccionaste: ${anime.title}", Toast.LENGTH_SHORT).show()
        }
        recyclerView.adapter = favAnimeAdapter

        loadFavorites("Planned") // Cargar datos iniciales

        buttonPlanned.setOnClickListener { loadFavorites("Planned") }
        buttonWatching.setOnClickListener { loadFavorites("Watching") }
        buttonWatched.setOnClickListener { loadFavorites("Watched") }

        return view
    }

    private fun loadFavorites(status: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = when (status) {
                    "Planned" -> animeService.getPlannedFavorites()
                    "Watching" -> animeService.getWatchingFavorites()
                    "Watched" -> animeService.getCompletedFavorites()
                    else -> null
                }

                if (response != null && response.isSuccessful) {
                    val favorites = response.body() ?: emptyList()
                    val animeList = getAnimeDetails(favorites)
                    withContext(Dispatchers.Main) {
                        favAnimeAdapter.updateList(animeList, favorites) // 🔹 Pasamos también la lista de Fav
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(requireContext(), "Error al cargar datos", Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(requireContext(), "Error de red: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


    private suspend fun getAnimeDetails(favorites: List<Fav>): List<Anime> {
        val animeList = mutableListOf<Anime>()
        for (fav in favorites) {
            try {
                println("📡 Solicitando anime con ID: ${fav.idAnime}")  // Agrega esto para ver los IDs en Logcat

                val response = animeService.getAnimeById(fav.idAnime)
                if (response.isSuccessful) {
                    response.body()?.let {
                        println("✅ Anime recibido: ${it.title}")  // Verifica que llegue el título
                        animeList.add(it)
                    }
                } else {
                    println("⚠️ Error en getAnimeById(${fav.idAnime}): ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                println("❌ Error en getAnimeById(${fav.idAnime}): ${e.message}")
            }
        }
        return animeList
    }
}
