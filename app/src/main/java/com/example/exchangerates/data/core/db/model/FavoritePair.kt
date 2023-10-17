package com.example.exchangerates.data.core.db.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favourite_pairs")
data class FavoritePair(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val id: Int? = null,
    @ColumnInfo(name = "first_currency") val firstCurrency: String,
    @ColumnInfo(name = "second_currency") val secondCurrency: String,
)
