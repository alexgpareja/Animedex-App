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

    @GET("favorites/search")
    suspend fun searchFavorites(@Query("q") query: String): Response<List<Fav>>

    // 🔹 Obtener animes en emisión (airing)
    @GET("anime/airing")
    suspend fun getAiringAnime(): Response<List<Anime>>

    // 🔹 Obtener animes populares (popular)
    @GET("anime/popular")
    suspend fun getPopularAnime(): Response<List<Anime>>

    @GET("anime/random")
    suspend fun getRandomAnimes(): Response<List<Anime>>

    // 🔹 Buscar animes por nombre (NUEVO ENDPOINT)
    @GET("anime/search")
    suspend fun searchAnimes(@Query("q") query: String): Response<List<Anime>>

    // 🔹 Obtener los animes favoritos del usuario
    @GET("favorites/")
    suspend fun getFavorites(): Response<List<Fav>>

    // 🔹 Obtener animes favoritos con status Planned (NUEVO ENDPOINT)
    @GET("favorites/planned")
    suspend fun getPlannedFavorites(): Response<List<Fav>>

    // 🔹 Obtener animes favoritos con status Watching (NUEVO ENDPOINT)
    @GET("favorites/watching")
    suspend fun getWatchingFavorites(): Response<List<Fav>>

    // 🔹 Obtener animes favoritos con status Completed (NUEVO ENDPOINT)
    @GET("favorites/completed")
    suspend fun getCompletedFavorites(): Response<List<Fav>>


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
