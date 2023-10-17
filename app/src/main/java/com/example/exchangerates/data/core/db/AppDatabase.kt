package com.example.exchangerates.data.core.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.exchangerates.data.core.db.model.FavoritePair

@Database(entities = [FavoritePair::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favoriteDao(): FavoriteDao
}