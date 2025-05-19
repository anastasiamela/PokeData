package com.example.pokedata.di

import android.content.Context
import com.example.pokedata.common.error.DefaultErrorHandler
import com.example.pokedata.common.error.ErrorHandler
import com.example.pokedata.data.network.ConnectivityObserver
import com.example.pokedata.data.network.NetworkConnectivityObserver
import com.example.pokedata.data.remote.PokeApi
import com.example.pokedata.repository.PokemonRepository
import com.example.pokedata.util.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun providePokemonRepository(
        api: PokeApi
    ) = PokemonRepository(api)

    @Singleton
    @Provides
    fun providePokeApi(): PokeApi {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(PokeApi::class.java)
    }

    @Provides
    @Singleton
    fun provideConnectivityObserver(
        @ApplicationContext context: Context
    ): ConnectivityObserver {
        return NetworkConnectivityObserver(context)
    }

    @Provides
    @Singleton
    fun provideErrorHandler(): ErrorHandler = DefaultErrorHandler()
}