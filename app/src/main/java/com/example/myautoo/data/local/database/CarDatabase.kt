package com.example.myautoo.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.myautoo.data.local.dao.CartDao
import com.example.myautoo.data.local.dao.UserPhotoDao
import com.example.myautoo.data.local.entity.CartItemEntity
import com.example.myautoo.data.local.entity.UserPhotoEntity

@Database(
    entities = [CartItemEntity::class, UserPhotoEntity::class], //Aca podríamos ir sumando diferentes entidades para guardarlas en el cache local de la app
    version = 2,
    exportSchema = false
)
abstract class CarDatabase : RoomDatabase() {
    abstract fun cartDao(): CartDao
    abstract fun userPhotoDao(): UserPhotoDao

    companion object {
        @Volatile
        private var INSTANCE: CarDatabase? = null

        fun getDatabase(context: Context): CarDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CarDatabase::class.java,
                    "car_database"
                )
                    // Si no quieres escribir migraciones, usa esto para evitar crash al cambiar versión
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
