package com.example.m7animedex.data.api

import com.example.m7animedex.data.model.Anime
import com.example.m7animedex.data.model.Fav
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface AnimeService {

    // 🔹 Obtener anime por ID
    @GET("anime/{anime_id}")
    suspend fun getAnimeById(@Path("anime_id") animeId: Int): Response<Anime>

    // 🔹 Obtener animes en emisión (airing)
    @GET("anime/airing")
    suspend fun getAiringAnime(): Response<List<Anime>>

    // 🔹 Obtener animes populares (popular)
    @GET("anime/popular")
    suspend fun getPopularAnime(): Response<List<Anime>>

    // 🔹 Obtener los animes favoritos del usuario
    @GET("favorites/")
    suspend fun getFavorites(): Response<List<Fav>>

    // 🔹 Agregar un anime a favoritos
    @POST("favorites/")
    suspend fun addFavorite(@Query("id_anime") idAnime: Int, @Query("status") status: String = "Planned"): Response<Void>

    // 🔹 Actualizar estado de un anime en favoritos
    @PUT("favorites/{id_anime}/status")
    suspend fun updateFavoriteStatus(
        @Path("id_anime") idAnime: Int,
        @Query("status") status: String
    ): Response<Void>

    // 🔹 Eliminar un anime de favoritos
    @DELETE("favorites/{id_anime}")
    suspend fun deleteFavorite(@Path("id_anime") idAnime: Int): Response<Void>
}
