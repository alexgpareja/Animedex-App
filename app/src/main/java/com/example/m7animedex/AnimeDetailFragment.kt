import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.m7animedex.R
import com.example.m7animedex.data.AnimeAPI
import com.example.m7animedex.data.api.AnimeService
import com.example.m7animedex.data.model.Anime
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AnimeDetailFragment : Fragment() {

    private var anime: Anime? = null
    private lateinit var animeApiService: AnimeService

    // Variable para saber si el anime está en favoritos.
    private var isFavorite = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            anime = it.getParcelable("ARG_ANIME")
        }
        animeApiService = AnimeAPI.getService() // Inicializa el servicio de la API aquí.
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_anime_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Referencias a las vistas del layout.
        val animeMainPicture: ImageView = view.findViewById(R.id.anime_main_picture)
        val animeTitle: TextView = view.findViewById(R.id.anime_title)
        val animeSynopsisText: TextView = view.findViewById(R.id.anime_synopsis_text)
        val fabAddToFavorites: FloatingActionButton = view.findViewById(R.id.fab_add_to_favorites)

        // Cargar los datos del anime en las vistas.
        anime?.let {
            Glide.with(this)
                .load(it.main_picture)
                .into(animeMainPicture)

            animeTitle.text = it.title
            animeSynopsisText.text = it.synopsis ?: "Sinopsis no disponible"

            // Comprobar si el anime ya está en favoritos y actualizar el ícono.
            checkIfFavorite(it.id, fabAddToFavorites)
        }

        // Listener para el botón flotante (añadir o eliminar de favoritos).
        fabAddToFavorites.setOnClickListener {
            anime?.let { selectedAnime ->
                if (isFavorite) {
                    removeFromFavorites(selectedAnime.id, fabAddToFavorites)
                } else {
                    addToFavorites(selectedAnime.id, fabAddToFavorites)
                }
            }
        }
    }

    private fun checkIfFavorite(idAnime: Int, fab: FloatingActionButton) {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val response = animeApiService.getFavorites()
                if (response.isSuccessful) {
                    val favorites = response.body() ?: emptyList()
                    isFavorite = favorites.any { it.idAnime == idAnime }
                    updateFabIcon(fab) // Actualiza el ícono según el estado.
                }
            } catch (e: Exception) {
                // Manejo de errores opcional.
            }
        }
    }

    private fun addToFavorites(idAnime: Int, fab: FloatingActionButton) {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val response = animeApiService.addFavorite(idAnime)
                if (response.isSuccessful) {
                    isFavorite = true
                    updateFabIcon(fab) // Cambia el ícono a "relleno".
                }
            } catch (e: Exception) {
                // Manejo de errores opcional.
            }
        }
    }

    private fun removeFromFavorites(idAnime: Int, fab: FloatingActionButton) {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val response = animeApiService.removeFavorite(idAnime)
                if (response.isSuccessful) {
                    isFavorite = false
                    updateFabIcon(fab) // Cambia el ícono a "vacío".
                }
            } catch (e: Exception) {
                // Manejo de errores opcional.
            }
        }
    }

    private fun updateFabIcon(fab: FloatingActionButton) {
        if (isFavorite) {
            fab.setImageResource(R.drawable.ic_favorite_filled) // Ícono relleno.
        } else {
            fab.setImageResource(R.drawable.ic_favorite_border) // Ícono vacío.
        }
    }
}
