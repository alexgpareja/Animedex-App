package com.example.m7animedex

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.m7animedex.data.AnimeAPI
import com.example.m7animedex.data.api.AnimeService
import kotlinx.coroutines.launch
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory


class HomeFragment : Fragment() {
    private lateinit var topAiringRecyclerView: RecyclerView
    private lateinit var topAiringAdapter: TopAiringAdapter
    private lateinit var animeApiService: AnimeService

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Configurar RecyclerView
        topAiringRecyclerView = view.findViewById(R.id.topAiringGrid)
        topAiringRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        // Inicializar el adapter vacío y asignarlo al RecyclerView
        topAiringAdapter = TopAiringAdapter(emptyList())
        topAiringRecyclerView.adapter = topAiringAdapter

        // Obtener la instancia de AnimeAPI
        animeApiService = AnimeAPI.getService()

        // Llamar a la API
        fetchTopAiringAnimes()
    }

    private fun fetchTopAiringAnimes() {
        lifecycleScope.launch {
            try {
                val response = animeApiService.getTopAiringAnime()
                if (response.isSuccessful) {
                    val animes = response.body() ?: emptyList()
                    topAiringAdapter.updateList(animes) // Método en tu adapter para actualizar la lista
                } else {
                    Log.e("HomeFragment", "Error al obtener animes: ${response.errorBody()}")
                    Toast.makeText(requireContext(), "Error al obtener animes", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Log.e("HomeFragment", "Error en la petición: ${e.message}")
                Toast.makeText(requireContext(), "Error en la red", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
