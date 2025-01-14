package com.example.m7animedex

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import android.graphics.BitmapFactory
import java.net.URL

class AnimeAdapter(private val animeList: List<Anime>) :
    RecyclerView.Adapter<AnimeAdapter.AnimeViewHolder>() {

    inner class AnimeViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val nameTextView: TextView = view.findViewById(R.id.animeNombre)
        private val imageView: ImageView = view.findViewById(R.id.animeImagen)

        fun bind(anime: Anime) {
            nameTextView.text = anime.nombre
            loadImageFromUrl(anime.imagenUrl, imageView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.elementllistahome, parent, false)
        return AnimeViewHolder(view)
    }

    override fun onBindViewHolder(holder: AnimeViewHolder, position: Int) {
        holder.bind(animeList[position])
    }

    override fun getItemCount(): Int = animeList.size

    // Método para cargar imágenes utilizando Thread
    private fun loadImageFromUrl(url: String, imageView: ImageView) {
        Thread {
            try {
                val connection = URL(url).openConnection()
                connection.connect()
                val inputStream = connection.getInputStream()
                val bitmap = BitmapFactory.decodeStream(inputStream)

                // Actualizar la ImageView en el hilo principal
                imageView.post {
                    imageView.setImageBitmap(bitmap)
                }
            } catch (e: Exception) {
                e.printStackTrace()

                // Si ocurre un error, establece una imagen predeterminada
                imageView.post {
                    imageView.setImageResource(android.R.color.darker_gray) // Imagen predeterminada
                }
            }
        }.start()
    }
}
