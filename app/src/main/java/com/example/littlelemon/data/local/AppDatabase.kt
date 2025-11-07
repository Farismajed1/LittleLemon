package com.example.littlelemon.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.littlelemon.data.model.Dish
import com.example.littlelemon.data.local.DishDao


@Database(entities = [Dish::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun dishDao(): DishDao

    companion object {
        @Volatile var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(true){
                val instance = Room.databaseBuilder(
                    context,
                    AppDatabase::class.java,
                    "AppDatabase"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}