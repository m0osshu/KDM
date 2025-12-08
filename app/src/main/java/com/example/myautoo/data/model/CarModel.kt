package com.example.myautoo.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CarModel(
    // todos los atributos deben coincidir con Firebase
    val id: Int = 0,
    val title: String = "",
    val description: String = "",
    val totalCapacity: String = "",
    val highestSpeed: String = "",
    val engineOutput: String = "",
    val picUrl: String = "",
    val price: Double = 0.0,
    val rating: Double = 0.0,
    val brand: String = ""
): Parcelable
