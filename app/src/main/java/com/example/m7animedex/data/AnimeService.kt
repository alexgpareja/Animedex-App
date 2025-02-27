// data/api/AnimeService.kt
package com.example.m7animedex.data.api

import com.example.m7animedex.data.model.Anime
import com.example.m7animedex.data.model.Fav
import com.example.m7animedex.data.model.Genre
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface AnimeService {

    // Obtenir tots els animes
    @GET("animes/")
    suspend fun getAnimes(): Response<List<Anime>>

    // Obtenir un anime per la seva ID
    @GET("animes/{id}")
    suspend fun getAnimeById(@Path("id") id: Int): Response<Anime>

    @GET("anime/airing") // Endpoint correcto según tu API
    suspend fun getTopAiringAnime(): Response<List<Anime>>


    // Afegir un nou anime
    @POST("animes/")
    suspend fun addAnime(@Body anime: Anime): Response<Void>

    // Eliminar un anime per la seva ID
    @DELETE("animes/{id}")
    suspend fun deleteAnime(@Path("id") id: Int): Response<Void>

    // Obtenir tots els gèneres
    @GET("genres/")
    suspend fun getGenres(): Response<List<Genre>>

    // Obtenir tots els favorits d'un usuari
    @GET("favs/{id_usuario}")
    suspend fun getFavsByUser(@Path("id_usuario") idUsuario: Int): Response<List<Fav>>

    // Afegir un anime als favorits d'un usuari
    @POST("favs/")
    suspend fun addFav(@Body fav: Fav): Response<Void>

    // Eliminar un anime dels favorits d'un usuari
    @DELETE("favs/{id_usuario}/{id_anime}")
    suspend fun deleteFav(
        @Path("id_usuario") idUsuario: Int,
        @Path("id_anime") idAnime: Int
    ): Response<Void>
}