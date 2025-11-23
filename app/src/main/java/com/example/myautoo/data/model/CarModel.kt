package com.example.myautoo.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CarModel(
    val title: String = "",
    val description: String = "",
    val totalCapacity: String = "",
    val highestSpeed: String = "",
    val engineOutput: String = "",  // ← debe coincidir con Firebase
    val picUrl: String = "",
    val price: Double = 0.0,
    val rating: Double = 0.0
): Parcelable