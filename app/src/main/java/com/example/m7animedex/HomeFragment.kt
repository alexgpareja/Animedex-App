package com.example.m7animedex

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.m7animedex.data.AnimeAPI
import com.example.m7animedex.data.api.AnimeService
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {
    private lateinit var topAiringRecyclerView: RecyclerView
    private lateinit var mostPopularRecyclerView: RecyclerView
    private lateinit var topAiringAdapter: AnimeAdapter
    private lateinit var mostPopularAdapter: AnimeAdapter
    private lateinit var animeApiService: AnimeService

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        topAiringRecyclerView = view.findViewById(R.id.topAiringGrid)
        mostPopularRecyclerView = view.findViewById(R.id.mostPopularGrid)

        topAiringRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        mostPopularRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        topAiringAdapter = AnimeAdapter(mutableListOf())
        mostPopularAdapter = AnimeAdapter(mutableListOf())

        topAiringRecyclerView.adapter = topAiringAdapter
        mostPopularRecyclerView.adapter = mostPopularAdapter

        // 🔹 Se obtiene correctamente la instancia de AnimeService
        animeApiService = AnimeAPI.getService()

        fetchTopAiringAnimes()
        fetchMostPopularAnimes()
    }

    private fun fetchTopAiringAnimes() {
        lifecycleScope.launch {
            try {
                val response = animeApiService.getAiringAnime() // ✅ Se usa el nombre correcto del método
                if (response.isSuccessful) {
                    val animes = response.body() ?: emptyList()
                    topAiringAdapter.updateList(animes) // ✅ Método en AnimeAdapter para actualizar datos
                } else {
                    Log.e("HomeFragment", "Error al obtener animes en emisión: ${response.errorBody()?.string()}")
                    Toast.makeText(requireContext(), "Error al obtener animes en emisión", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Log.e("HomeFragment", "Error en la petición: ${e.message}")
                Toast.makeText(requireContext(), "Error en la red", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun fetchMostPopularAnimes() {
        lifecycleScope.launch {
            try {
                val response = animeApiService.getPopularAnime() // ✅ Se usa el nombre correcto del método
                if (response.isSuccessful) {
                    val mostPopularAnimes = response.body() ?: emptyList()
                    mostPopularAdapter.updateList(mostPopularAnimes) // ✅ Se usa la lista directamente
                } else {
                    Log.e("HomeFragment", "Error al obtener animes populares: ${response.errorBody()?.string()}")
                    Toast.makeText(requireContext(), "Error al obtener animes populares", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Log.e("HomeFragment", "Error en la petición: ${e.message}")
                Toast.makeText(requireContext(), "Error en la red", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
