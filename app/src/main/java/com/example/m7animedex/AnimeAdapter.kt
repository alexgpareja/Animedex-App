package com.example.m7animedex

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.m7animedex.data.model.Anime

class AnimeAdapter(
    private var animeList: MutableList<Anime> = mutableListOf(),
    private val onAnimeClickListener: (Anime) -> Unit // Listener para manejar clics
) : RecyclerView.Adapter<AnimeHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimeHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.elementllistahome, parent, false)
        return AnimeHolder(view, onAnimeClickListener)
    }

    override fun getItemCount(): Int = animeList.size

    override fun onBindViewHolder(holder: AnimeHolder, position: Int) {
        holder.bind(animeList[position])
    }

    // Método para actualizar la lista de animes dinámicamente
    fun updateList(newList: List<Anime>) {
        animeList = newList.toMutableList()
        notifyDataSetChanged()
    }
}

class AnimeHolder(itemView: View, private val onAnimeClickListener: (Anime) -> Unit) : RecyclerView.ViewHolder(itemView) {
    private val titulo: TextView = itemView.findViewById(R.id.animeNombreHome)
    private val imagen: ImageView = itemView.findViewById(R.id.animeImagenHome)

    fun bind(anime: Anime) {
        val maxLength = 20
        titulo.text = if (anime.title.length > maxLength) {
            "${anime.title.substring(0, maxLength)}..."
        } else {
            anime.title
        }

        Glide.with(itemView.context)
            .load(anime.main_picture)
            .into(imagen)

        // Configurar el clic en el elemento
        itemView.setOnClickListener {
            onAnimeClickListener(anime)
        }
    }
}
