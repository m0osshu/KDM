package com.example.myautoo.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_photos")
data class UserPhotoEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val userId: String,
    val photoUri: String
)