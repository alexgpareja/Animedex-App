package com.example.m7animedex

import AnimeDetailFragment
import android.os.Bundle
import android.view.KeyEvent
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
import com.example.m7animedex.data.api.AnimeService
import com.example.m7animedex.data.model.Anime
import com.example.m7animedex.data.model.Fav
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response

class ListsFragment : Fragment() {

    private lateinit var searchEditText: EditText
    private lateinit var buttonPlanned: Button
    private lateinit var buttonWatching: Button
    private lateinit var buttonWatched: Button
    private lateinit var recyclerView: RecyclerView
    private lateinit var favAnimeAdapter: FavAnimeAdapter
    private val animeService: AnimeService = AnimeAPI.getService()

    // 🔹 Variables para rastrear el estado actual y la consulta de búsqueda
    private var currentStatus: String = "Planned"
    private var currentQuery: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_lists, container, false)

        // Inicializar vistas
        searchEditText = view.findViewById(R.id.searchBox)
        buttonPlanned = view.findViewById(R.id.buttonPlanned)
        buttonWatching = view.findViewById(R.id.buttonWatching)
        buttonWatched = view.findViewById(R.id.buttonWatched)
        recyclerView = view.findViewById(R.id.recyclerViewLists)

        // Configurar el RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        favAnimeAdapter = FavAnimeAdapter(mutableListOf()) { anime ->
            // Acción al hacer clic en un anime: abrir el detalle del anime
            openAnimeDetailFragment(anime)
        }
        recyclerView.adapter = favAnimeAdapter

        // Cargar datos iniciales
        loadFilteredData()

        // Configurar los botones de filtro
        buttonPlanned.setOnClickListener {
            currentStatus = "Planned"
            loadFilteredData()
        }
        buttonWatching.setOnClickListener {
            currentStatus = "Watching"
            loadFilteredData()
        }
        buttonWatched.setOnClickListener {
            currentStatus = "Watched"
            loadFilteredData()
        }

        // Configurar el buscador
        setupSearchBox()

        return view
    }

    /**
     * Configura el buscador para buscar favoritos por título.
     */
    private fun setupSearchBox() {
        searchEditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == KeyEvent.KEYCODE_ENTER || actionId == KeyEvent.ACTION_DOWN) {
                val query = searchEditText.text.toString().trim()
                if (query.isNotEmpty()) {
                    // Si hay una consulta, actualizar la variable y cargar datos filtrados
                    currentQuery = query
                    loadFilteredData()
                } else {
                    // Si el campo está vacío, limpiar la consulta y cargar todos los favoritos
                    currentQuery = ""
                    loadFilteredData()
                }
                true
            } else {
                false
            }
        }
    }

    /**
     * Carga los datos filtrados según el estado actual y la consulta de búsqueda.
     */
    private fun loadFilteredData() {
        if (currentQuery.isNotEmpty()) {
            // Si hay una consulta, buscar favoritos por título y estado
            searchFavorites(currentQuery, currentStatus)
        } else {
            // Si no hay consulta, cargar todos los favoritos del estado actual
            loadFavoritesByStatus(currentStatus)
        }
    }

    /**
     * Busca favoritos por título y estado utilizando el endpoint /favorites/search.
     */
    private fun searchFavorites(query: String, status: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = animeService.searchFavorites(query)
                if (response.isSuccessful) {
                    val favorites = response.body() ?: emptyList()
                    // Filtrar los favoritos por estado
                    val filteredFavorites = favorites.filter { it.status.equals(status, ignoreCase = true) }
                    val animeList = getAnimeDetails(filteredFavorites)
                    withContext(Dispatchers.Main) {
                        favAnimeAdapter.updateList(animeList, filteredFavorites)
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(requireContext(), "Error al buscar favoritos", Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(requireContext(), "Error de red: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    /**
     * Carga los favoritos según el estado seleccionado (Planned, Watching, Completed).
     */
    private fun loadFavoritesByStatus(status: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = when (status) {
                    "Planned" -> animeService.getPlannedFavorites()
                    "Watching" -> animeService.getWatchingFavorites()
                    "Watched" -> animeService.getCompletedFavorites() // Cambiado a Watched
                    else -> null
                }

                if (response != null && response.isSuccessful) {
                    val favorites = response.body() ?: emptyList()
                    val animeList = getAnimeDetails(favorites)
                    withContext(Dispatchers.Main) {
                        favAnimeAdapter.updateList(animeList, favorites)
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

    /**
     * Obtiene los detalles de los animes asociados a los favoritos.
     */
    private suspend fun getAnimeDetails(favorites: List<Fav>): List<Anime> {
        val animeList = mutableListOf<Anime>()
        for (fav in favorites) {
            try {
                println("📡 Solicitando anime con ID: ${fav.idAnime}")
                val response = animeService.getAnimeById(fav.idAnime)
                if (response.isSuccessful) {
                    response.body()?.let {
                        println("✅ Anime recibido: ${it.title}")
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
}