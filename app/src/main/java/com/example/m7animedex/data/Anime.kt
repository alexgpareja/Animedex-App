// data/model/Anime.kt
package com.example.m7animedex.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.LocalDate
import java.time.LocalDateTime

@Parcelize
data class Anime(
    val id: Int,
    val title: String,
    val mainPicture: String? = null,  // Opcional
    val startDate: LocalDate?,        // Opcional
    val endDate: LocalDate?,          // Opcional
    val synopsis: String,
    val mean: Float? = null,          // Opcional
    val rank: Int? = null,            // Opcional
    val popularity: Int? = null,      // Opcional
    val mediaType: String,
    val status: String,
    val numEpisodes: Int? = null,     // Opcional
    val startSeason: String? = null,  // Opcional
    val genres: List<Genre>           // Llista de gèneres associats
) : Parcelable