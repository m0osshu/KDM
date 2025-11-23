package com.example.myautoo.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.myautoo.data.local.dao.CartDao
import com.example.myautoo.data.local.entity.CartItemEntity

@Database(entities = [CartItemEntity::class], version = 1, exportSchema = false)
abstract class CarDatabase : RoomDatabase() {
    abstract fun cartDao(): CartDao

    companion object {
        @Volatile
        private var INSTANCE: CarDatabase? = null

        fun getDatabase(context: Context): CarDatabase {
            // Devuelve la instancia si ya existe; si no, la crea.
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CarDatabase::class.java,
                    "car_database" // Nombre del archivo de la base de datos
                ).build()
                INSTANCE = instance
                // Devuelve la instancia recién creada
                instance
            }
        }
    }
}