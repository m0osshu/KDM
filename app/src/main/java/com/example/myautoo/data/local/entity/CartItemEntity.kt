package com.example.myautoo.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cart_items")
data class CartItemEntity(

    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val carId: Int,
    val title: String,
    val price: Double
)