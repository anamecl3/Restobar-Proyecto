package com.cibertec.reztrov01.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Restaurant(
    val id: Int = 0,
    val nombre: String,
    val rating: Double,
    val imagen: Int,
    val distrito: String,
    val tipo: String
) : Parcelable
