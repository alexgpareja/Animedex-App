package com.example.m7animedex

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class AnimeAdapter(private val animeList: List<Anime>) : RecyclerView.Adapter<AnimeHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimeHolder {
        var iteminflater = LayoutInflater.from(parent.context)
        var recycleritem = iteminflater.inflate(R.layout.elementllistahome, parent, false)
        return AnimeHolder(recycleritem)
    }
    override fun getItemCount(): Int {
        return animeList.size
    }
    override fun onBindViewHolder(holder: AnimeHolder, position: Int) {
        val movie = animeList.get(position)
        holder.Renderitzar(movie)
    }
}

class AnimeHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
    var titol: TextView = itemView.findViewById(R.id.animeNombre)
    var foto: ImageView = itemView.findViewById(R.id.animeImagen)

    public fun Renderitzar(anime: Anime) {
        titol.text = anime.nombre

        Glide.with(itemView.context)
            .load(anime.imagenUrl)
            .into(foto)
    }
}
