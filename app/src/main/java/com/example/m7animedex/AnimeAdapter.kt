package com.example.m7animedex

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.m7animedex.data.model.Anime
class AnimeAdapter(private var animeList: MutableList<Anime> = mutableListOf()) : RecyclerView.Adapter<AnimeHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimeHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.elementllistahome, parent, false)
        return AnimeHolder(view)
    }

    override fun getItemCount(): Int = animeList.size

    override fun onBindViewHolder(holder: AnimeHolder, position: Int) {
        holder.bind(animeList[position])
    }

    // Método para actualizar la lista de animes dinámicamente
    fun updateList(newList: List<Anime>) {
        animeList = newList.toMutableList() // Convierte a mutable antes de asignar
        notifyDataSetChanged() // Notifica cambios al RecyclerView
    }
}

class AnimeHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val titulo: TextView = itemView.findViewById(R.id.animeNombreHome)
    private val imagen: ImageView = itemView.findViewById(R.id.animeImagenHome)

    fun bind(anime: Anime) {
        val maxLength = 20 // Número máximo de caracteres antes de truncar
        titulo.text = if (anime.title.length > maxLength) {
            "${anime.title.substring(0, maxLength)}..." // Corta el texto y agrega "..."
        } else {
            anime.title // Si es menor, lo muestra completo
        }

        Glide.with(itemView.context)
            .load(anime.mainPicture)
            .into(imagen)
    }

}
