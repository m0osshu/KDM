package com.example.myautoo.data.repository

import com.example.myautoo.data.local.dao.UserPhotoDao
import com.example.myautoo.data.local.entity.UserPhotoEntity

class UserPhotoRepository(
    private val userPhotoDao: UserPhotoDao
) {

    suspend fun getPhoto(userId: String): UserPhotoEntity? {
        return userPhotoDao.getPhotoByUser(userId)
    }

    suspend fun savePhoto(photo: UserPhotoEntity) {
        userPhotoDao.insertPhoto(photo)
    }

    suspend fun deletePhoto(photo: UserPhotoEntity) {
        userPhotoDao.deletePhoto(photo)
    }
}