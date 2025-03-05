package com.example.m7animedex

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.m7animedex.data.model.Anime
import com.example.m7animedex.data.model.Fav

class FavAnimeAdapter(
    private var animeList: MutableList<Anime> = mutableListOf(),
    private var favList: MutableList<Fav> = mutableListOf(),
    private val onAnimeClickListener: (Anime) -> Unit
) : RecyclerView.Adapter<FavAnimeAdapter.FavAnimeHolder>() {

    // Esta lista se utilizará para almacenar el conjunto de animes completo (sin filtrar)
    private var fullAnimeList: MutableList<Anime> = animeList.toMutableList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavAnimeHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.elementllistafav, parent, false)
        return FavAnimeHolder(view, onAnimeClickListener)
    }

    override fun getItemCount(): Int = animeList.size

    override fun onBindViewHolder(holder: FavAnimeHolder, position: Int) {
        holder.bind(animeList[position], favList)
    }

    // Método para actualizar la lista del adaptador
    fun updateList(newAnimeList: List<Anime>, newFavList: List<Fav>) {
        animeList = newAnimeList.toMutableList()
        favList = newFavList.toMutableList()
        fullAnimeList = newAnimeList.toMutableList()  // Actualizamos la lista completa de animes
        notifyDataSetChanged()
    }

    // Método para filtrar la lista de animes
    fun filterList(query: String) {
        val filteredList = fullAnimeList.filter { it.title.contains(query, ignoreCase = true) }
        animeList = filteredList.toMutableList()  // Actualizamos la lista mostrada con la lista filtrada
        notifyDataSetChanged()
    }

    class FavAnimeHolder(itemView: View, private val onAnimeClickListener: (Anime) -> Unit) :
        RecyclerView.ViewHolder(itemView) {

        private val titulo: TextView = itemView.findViewById(R.id.animeNombre)
        private val episodios: TextView = itemView.findViewById(R.id.animeEpisodios)
        private val estado: TextView = itemView.findViewById(R.id.animeEstado)
        private val imagen: ImageView = itemView.findViewById(R.id.animeImagen)

        fun bind(anime: Anime, favList: List<Fav>) {
            titulo.text = anime.title
            episodios.text = "${anime.num_episodes} episodios"

            // Buscamos el status en favList
            val favStatus = favList.find { it.idAnime == anime.id }?.status ?: "Desconocido"
            estado.text = favStatus  // Mostramos el status de Fav

            Glide.with(itemView.context)
                .load(anime.main_picture)
                .into(imagen)

            itemView.setOnClickListener {
                onAnimeClickListener(anime)
            }
        }
    }
}
