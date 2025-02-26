// data/model/Fav.kt
package com.example.m7animedex.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Fav(
    val idUsuario: Int,
    val idAnime: Int,
    val status: String  // Pot ser "watching", "completed", etc.
) : Parcelable