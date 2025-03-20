import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.m7animedex.R
import com.example.m7animedex.data.AnimeAPI
import com.example.m7animedex.data.api.AnimeService
import com.example.m7animedex.data.model.Anime
import com.example.m7animedex.data.model.Fav
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class AnimeDetailFragment : Fragment() {

    private var anime: Anime? = null
    private lateinit var animeApiService: AnimeService
    private var isFavorite = false

    // Nuevos elementos
    private lateinit var statusLayout: View
    private lateinit var statusSpinner: Spinner
    private lateinit var startDateText: TextView
    private lateinit var completedDateText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            anime = it.getParcelable("ARG_ANIME")
        }
        animeApiService = AnimeAPI.getService()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_anime_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val animeMainPicture: ImageView = view.findViewById(R.id.anime_main_picture)
        val animeTitle: TextView = view.findViewById(R.id.anime_title)
        val animeSynopsisText: TextView = view.findViewById(R.id.anime_synopsis_text)
        val animeGenres: TextView = view.findViewById(R.id.anime_genres)
        val animeStartDate: TextView = view.findViewById(R.id.anime_start_date)
        val animeEndDate: TextView = view.findViewById(R.id.anime_end_date)
        val animeMean: TextView = view.findViewById(R.id.anime_mean)
        val animeRank: TextView = view.findViewById(R.id.anime_rank)
        val animePopularity: TextView = view.findViewById(R.id.anime_popularity)
        val animeMediaType: TextView = view.findViewById(R.id.anime_media_type)
        val animeStatus: TextView = view.findViewById(R.id.anime_status)
        val fabAddToFavorites: FloatingActionButton = view.findViewById(R.id.fab_add_to_favorites)

        // Inicializar nuevos elementos
        statusLayout = view.findViewById(R.id.status_layout)
        statusSpinner = view.findViewById(R.id.anime_status_spinner)
        startDateText = view.findViewById(R.id.anime_start_watching_date)
        completedDateText = view.findViewById(R.id.anime_completed_date)

        anime?.let {
            if (it.genres.isNullOrEmpty() || it.media_type.isNullOrEmpty()) {
                loadFullAnimeDetails(it.id)
            } else {
                displayAnimeData(it)
            }
            checkIfFavorite(it.id, fabAddToFavorites)
        }

        fabAddToFavorites.setOnClickListener {
            anime?.let { selectedAnime ->
                if (isFavorite) {
                    removeFromFavorites(selectedAnime.id, fabAddToFavorites)
                } else {
                    addToFavorites(selectedAnime.id, fabAddToFavorites)
                }
            }
        }

        // Configurar el listener del Spinner
        statusSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedStatus = parent?.getItemAtPosition(position).toString()
                anime?.let { selectedAnime ->
                    lifecycleScope.launch {
                        try {
                            when (selectedStatus) {
                                "Planned" -> animeApiService.markAsPlanned(selectedAnime.id)
                                "Watching" -> animeApiService.markAsWatching(selectedAnime.id)
                                "Watched" -> animeApiService.markAsCompleted(selectedAnime.id)
                            }
                            fetchFavoriteStatus(selectedAnime.id) // Refrescar el estado y las fechas
                        } catch (e: Exception) {
                            Toast.makeText(requireContext(), "Error de conexión", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    private fun loadFullAnimeDetails(id: Int) {
        lifecycleScope.launch {
            try {
                val response = animeApiService.getAnimeById(id)
                if (response.isSuccessful) {
                    response.body()?.let { fullAnime ->
                        anime = fullAnime
                        displayAnimeData(fullAnime)
                    }
                } else {
                    Toast.makeText(requireContext(), "Error cargando detalles del Anime", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(requireContext(), "Error de conexión", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun displayAnimeData(anime: Anime) {
        view?.let { view ->
            Glide.with(this).load(anime.main_picture).into(view.findViewById(R.id.anime_main_picture))
            view.findViewById<TextView>(R.id.anime_title).text = anime.title
            view.findViewById<TextView>(R.id.anime_synopsis_text).text = anime.synopsis ?: "Sinopsis no disponible"
            view.findViewById<TextView>(R.id.anime_genres).text = anime.genres?.joinToString(", ") { it.name } ?: "Géneros no disponibles"
            view.findViewById<TextView>(R.id.anime_start_date).text = anime.start_date ?: "Fecha no disponible"
            view.findViewById<TextView>(R.id.anime_end_date).text = anime.end_date ?: "Fecha no disponible"
            view.findViewById<TextView>(R.id.anime_mean).text = anime.mean?.toString() ?: "N/A"
            view.findViewById<TextView>(R.id.anime_rank).text = anime.rank?.toString() ?: "N/A"
            view.findViewById<TextView>(R.id.anime_popularity).text = anime.popularity?.toString() ?: "N/A"
            view.findViewById<TextView>(R.id.anime_media_type).text = anime.media_type ?: "N/A"
            view.findViewById<TextView>(R.id.anime_status).text = anime.status ?: "N/A"
        }
    }

    private fun checkIfFavorite(idAnime: Int, fab: FloatingActionButton) {
        lifecycleScope.launch {
            try {
                val response = animeApiService.getFavorites()
                if (response.isSuccessful) {
                    val favorites = response.body() ?: emptyList()
                    isFavorite = favorites.any { it.idAnime == idAnime }
                    updateFabIcon(fab)
                    updateStatusVisibility()
                    if (isFavorite) {
                        fetchFavoriteStatus(idAnime)
                    }
                } else {
                    Toast.makeText(requireContext(), "Error al cargar favoritos", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(requireContext(), "Error de conexión", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun fetchFavoriteStatus(idAnime: Int) {
        lifecycleScope.launch {
            try {
                val response = animeApiService.getFavorites()
                if (response.isSuccessful) {
                    val favorites = response.body() ?: emptyList()
                    val fav = favorites.find { it.idAnime == idAnime }
                    fav?.let {
                        updateStatusFields(it.status, it.dateAdded, it.dateFinished)
                    }
                } else {
                    Toast.makeText(requireContext(), "Error al cargar favoritos", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(requireContext(), "Error de conexión", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun updateStatusVisibility() {
        if (isFavorite) {
            statusLayout.visibility = View.VISIBLE
        } else {
            statusLayout.visibility = View.GONE
        }
    }

    private fun updateStatusFields(status: String, dateAdded: LocalDateTime?, dateFinished: LocalDateTime?) {
        // Formateador para convertir LocalDateTime a String con formato "yyyy-MM-dd"
        val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

        when (status) {
            "Watching" -> {
                startDateText.text = "Fecha inicio: ${dateAdded?.format(dateFormatter) ?: "-"}"
                completedDateText.text = "Fecha fin: N/A"
            }
            "Completed" -> {
                startDateText.text = "Fecha inicio: ${dateAdded?.format(dateFormatter) ?: "-"}"
                completedDateText.text = "Fecha fin: ${dateFinished?.format(dateFormatter) ?: "-"}"
            }
            else -> {
                startDateText.text = "Fecha inicio: -"
                completedDateText.text = "Fecha fin: -"
            }
        }
    }

    private fun addToFavorites(idAnime: Int, fab: FloatingActionButton) {
        lifecycleScope.launch {
            try {
                val response = animeApiService.addFavorite(idAnime)
                if (response.isSuccessful) {
                    isFavorite = true
                    updateFabIcon(fab)

                    // Refrescar el estado y mostrar los nuevos campos
                    fetchFavoriteStatus(idAnime)

                    //Toast.makeText(requireContext(), "Añadido a favoritos", Toast.LENGTH_SHORT).show()
                } else {
                    //Toast.makeText(requireContext(), "Error al añadir a favoritos", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                //Toast.makeText(requireContext(), "Error de conexión", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun removeFromFavorites(idAnime: Int, fab: FloatingActionButton) {
        lifecycleScope.launch {
            try {
                val response = animeApiService.removeFavorite(idAnime)
                if (response.isSuccessful) {
                    isFavorite = false
                    updateFabIcon(fab)
                    Toast.makeText(requireContext(), "Eliminado de favoritos", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(requireContext(), "Error al eliminar de favoritos", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(requireContext(), "Error de conexión", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun updateFabIcon(fab: FloatingActionButton) {
        if (isFavorite) {
            fab.setImageResource(R.drawable.ic_favorite_filled)
        } else {
            fab.setImageResource(R.drawable.ic_favorite_border)
        }
    }
}