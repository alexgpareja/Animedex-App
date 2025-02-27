// data/model/Fav.kt
package com.example.m7animedex.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.LocalDateTime

@Parcelize
data class Fav(
    val idUsuario: Int,
    val idAnime: Int,
    val status: String,
    val dateAdded: LocalDateTime,
    val dateFinished: LocalDateTime? = null
) : Parcelable