package com.example.exchangerates.di

import com.example.exchangerates.data.core.api.ExchangeRatesApi
import com.example.exchangerates.data.core.db.FavoriteDao
import com.example.exchangerates.data.currencies.CurrenciesRepositoryImpl
import com.example.exchangerates.domain.currencies.CurrenciesRepository
import com.example.exchangerates.domain.currencies.GetFavoritesByFirstCurrencyUseCase
import com.example.exchangerates.domain.currencies.LoadCurrenciesUseCase
import com.example.exchangerates.domain.currencies.LoadRatesByCurrencyUseCase
import com.example.exchangerates.domain.currencies.SaveFavoriteUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CurrenciesModule {

    @Provides
    @Singleton
    fun provideCurrenciesRepository(
        api: ExchangeRatesApi,
        db: FavoriteDao
    ): CurrenciesRepository {
        return CurrenciesRepositoryImpl(api, db)
    }

    @Provides
    @Singleton
    fun provideLoadCurrenciesUseCase(
        currenciesRepository: CurrenciesRepository
    ): LoadCurrenciesUseCase {
        return LoadCurrenciesUseCase(currenciesRepository)
    }

    @Provides
    @Singleton
    fun provideLoadRatesByCurrencyUseCase(
        currenciesRepository: CurrenciesRepository
    ): LoadRatesByCurrencyUseCase {
        return LoadRatesByCurrencyUseCase(currenciesRepository)
    }

    @Provides
    @Singleton
    fun provideSaveFavoriteUseCase(
        currenciesRepository: CurrenciesRepository
    ): SaveFavoriteUseCase {
        return SaveFavoriteUseCase(currenciesRepository)
    }

    @Provides
    @Singleton
    fun provideGetFavoritesByFirstCurrencyUseCase(
        currenciesRepository: CurrenciesRepository
    ): GetFavoritesByFirstCurrencyUseCase {
        return GetFavoritesByFirstCurrencyUseCase(currenciesRepository)
    }
}