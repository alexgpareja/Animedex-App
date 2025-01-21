package com.example.m7animedex

data class Anime(
    val nombre: String,
    val imagenUrl: String
)
class AnimeProvider {

    companion object {
        @JvmStatic
        val Animes: List<Anime> = listOf(
            Anime(
                nombre= "Attack on Titan",
                imagenUrl= "https://upload.wikimedia.org/wikipedia/en/d/d6/Shingeki_no_Kyojin_manga_volume_1.jpg"
            ),
            Anime(
                nombre= "One Piece",
                imagenUrl= "https://upload.wikimedia.org/wikipedia/en/9/90/One_Piece%2C_Volume_61_Cover_%28Japanese%29.jpg"
            ),
            Anime(
                nombre= "Naruto",
                imagenUrl= "https://upload.wikimedia.org/wikipedia/en/9/94/NarutoCoverTankobon1.jpg"
            )
        )
    }
}
