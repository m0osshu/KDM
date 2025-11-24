package com.example.myautoo.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.myautoo.data.local.entity.UserPhotoEntity

@Dao
interface UserPhotoDao {
    @Query("SELECT * FROM user_photos WHERE userId = :userId LIMIT 1")
    suspend fun getPhotoByUser(userId: String): UserPhotoEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPhoto(photo: UserPhotoEntity)

    @Delete
    suspend fun deletePhoto(photo: UserPhotoEntity)
}