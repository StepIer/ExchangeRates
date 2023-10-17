package com.example.exchangerates.di

import com.example.exchangerates.data.core.api.ExchangeRatesApi
import com.example.exchangerates.data.core.db.FavoriteDao
import com.example.exchangerates.data.favorites.FavoritesRepositoryImpl
import com.example.exchangerates.domain.favorites.DeleteFavoritePairUseCase
import com.example.exchangerates.domain.favorites.FavoritesRepository
import com.example.exchangerates.domain.favorites.GetFavoriteUseCase
import com.example.exchangerates.domain.favorites.LoadRatesByCurrenciesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FavoriteModule {

    @Provides
    @Singleton
    fun provideFavoritesRepository(
        favoriteDao: FavoriteDao,
        exchangeRatesApi: ExchangeRatesApi
    ): FavoritesRepository {
        return FavoritesRepositoryImpl(
            favoriteDao,
            exchangeRatesApi
        )
    }

    @Provides
    @Singleton
    fun provideGetFavoriteUseCase(
        favoritesRepository: FavoritesRepository
    ): GetFavoriteUseCase {
        return GetFavoriteUseCase(favoritesRepository)
    }

    @Provides
    @Singleton
    fun provideLoadRatesByCurrencies(
        favoritesRepository: FavoritesRepository
    ): LoadRatesByCurrenciesUseCase {
        return LoadRatesByCurrenciesUseCase(favoritesRepository)
    }

    @Provides
    @Singleton
    fun provideDeleteFavoritePairUseCase(
        favoritesRepository: FavoritesRepository
    ): DeleteFavoritePairUseCase {
        return DeleteFavoritePairUseCase(favoritesRepository)
    }
}