package com.example.exchangerates.data.core.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.exchangerates.data.core.db.model.FavoritePair
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDao {

    @Insert
    fun insert(favoritePair: FavoritePair)

    @Query("SELECT * FROM favourite_pairs")
    fun getAll(): Flow<List<FavoritePair>>

    @Query("SELECT * FROM favourite_pairs WHERE first_currency = :firstCurrency")
    fun getByFirstCurrency(firstCurrency: String): Flow<List<FavoritePair>>

    @Query("Delete FROM favourite_pairs WHERE first_currency = :firstCurrency AND second_currency = :secondCurrency")
    fun delete(firstCurrency: String, secondCurrency: String)
}