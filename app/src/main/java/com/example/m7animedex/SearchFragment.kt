package com.example.m7animedex

import AnimeDetailFragment
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.m7animedex.data.AnimeAPI
import com.example.m7animedex.data.api.AnimeService
import com.example.m7animedex.data.model.Anime
import com.example.m7animedex.data.model.Fav
import kotlinx.coroutines.launch
import retrofit2.Response

class SearchFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: FavAnimeAdapter
    private lateinit var searchBox: EditText
    private val animeList: MutableList<Anime> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_search, container, false)

        // Inicializar vistas
        searchBox = view.findViewById(R.id.searchBox)
        recyclerView = view.findViewById(R.id.recyclerViewAnimes)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Configurar el adaptador
        adapter = FavAnimeAdapter(animeList, animeList, mutableListOf<Fav>()) { anime ->
            // Acción al hacer clic en un anime: abrir el detalle del anime
            openAnimeDetailFragment(anime)
        }
        recyclerView.adapter = adapter

        // Configurar el listener para el buscador
        searchBox.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == KeyEvent.KEYCODE_ENTER || actionId == KeyEvent.ACTION_DOWN) {
                val query = searchBox.text.toString().trim()
                if (query.isNotEmpty()) {
                    searchAnimes(query)
                } else {
                    loadAllAnimes()
                }
                true
            } else {
                false
            }
        }

        return view
    }

    /**
     * Abre el fragmento de detalles del anime seleccionado.
     */
    private fun openAnimeDetailFragment(anime: Anime) {
        val fragment = AnimeDetailFragment()
        val args = Bundle()
        args.putParcelable("ARG_ANIME", anime)
        fragment.arguments = args

        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }

    /**
     * Busca animes por título utilizando el endpoint /anime/search.
     */
    private fun searchAnimes(query: String) {
        lifecycleScope.launch {
            try {
                val response = AnimeAPI.getService().searchAnimes(query)
                if (response.isSuccessful && response.body() != null) {
                    val animes = response.body()!!

                    // Limpiar la lista actual
                    animeList.clear()

                    // Agregar los resultados a la lista
                    animeList.addAll(animes)

                    // Actualizar el adaptador
                    adapter.updateList(animes, mutableListOf<Fav>())
                } else {
                    Toast.makeText(requireContext(), "Error al buscar animes", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(requireContext(), "Error de conexión", Toast.LENGTH_SHORT).show()
            }
        }
    }

    /**
     * Carga todos los animes disponibles utilizando el endpoint /anime/all.
     */
    private fun loadAllAnimes() {
        lifecycleScope.launch {
            try {
                val response = AnimeAPI.getService().getRandomAnimes()
                if (response.isSuccessful && response.body() != null) {
                    val animes = response.body()!!

                    // Limpiar la lista actual
                    animeList.clear()

                    // Agregar los resultados a la lista
                    animeList.addAll(animes)

                    // Actualizar el adaptador
                    adapter.updateList(animes, mutableListOf<Fav>())
                } else {
                    Toast.makeText(requireContext(), "Error al cargar animes", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(requireContext(), "Error de conexión", Toast.LENGTH_SHORT).show()
            }
        }
    }
}