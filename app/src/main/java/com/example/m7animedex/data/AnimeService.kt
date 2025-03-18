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

    // Obtiene un anime por su ID único
    @GET("anime/{anime_id}")
    suspend fun getAnimeById(@Path("anime_id") animeId: Int): Response<Anime>

    // Busca animes favoritos que coinciden con la cadena de búsqueda proporcionada
    @GET("favorites/search")
    suspend fun searchFavorites(@Query("q") query: String): Response<List<Fav>>

    // Obtiene una lista de animes que están actualmente en emisión
    @GET("anime/airing")
    suspend fun getAiringAnime(): Response<List<Anime>>

    // Obtiene una lista de animes populares
    @GET("anime/popular")
    suspend fun getPopularAnime(): Response<List<Anime>>

    // Obtiene una selección aleatoria de animes
    @GET("anime/random")
    suspend fun getRandomAnimes(): Response<List<Anime>>

    // Busca animes por título utilizando la cadena de búsqueda proporcionada
    @GET("anime/search")
    suspend fun searchAnimes(@Query("q") query: String): Response<List<Anime>>

    // Obtiene todos los animes favoritos del usuario actual
    @GET("favorites/")
    suspend fun getFavorites(): Response<List<Fav>>

    // Obtiene animes favoritos con estado 'Planned'
    @GET("favorites/planned")
    suspend fun getPlannedFavorites(): Response<List<Fav>>

    // Obtiene animes favoritos con estado 'Watching'
    @GET("favorites/watching")
    suspend fun getWatchingFavorites(): Response<List<Fav>>

    // Obtiene animes favoritos con estado 'Completed'
    @GET("favorites/completed")
    suspend fun getCompletedFavorites(): Response<List<Fav>>

    // Agrega un anime a favoritos con el estado especificado (predeterminado: 'Planned')
    @POST("favorites/")
    suspend fun addFavorite(
        @Query("id_anime") idAnime: Int,
        @Query("status") status: String = "Planned"
    ): Response<Void>

    // Actualiza el estado de un anime favorito
    @PUT("favorites/{id_anime}")
    suspend fun updateFavoriteStatus(
        @Path("id_anime") idAnime: Int,
        @Query("status") newStatus: String
    ): Response<Void>

    // Elimina un anime de la lista de favoritos del usuario
    @DELETE("favorites/{id_anime}")
    suspend fun removeFavorite(@Path("id_anime") idAnime: Int): Response<Void>
}
