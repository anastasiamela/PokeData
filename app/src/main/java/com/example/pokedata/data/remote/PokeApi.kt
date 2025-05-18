package com.example.pokedata.data.remote

import com.example.pokedata.data.remote.responses.PokemonListResponse
import com.example.pokedata.data.remote.responses.PokemonResponse
import com.example.pokedata.data.remote.responses.PokemonTypeResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokeApi {

    @GET("pokemon")
    suspend fun getPokemonList(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): PokemonListResponse

    @GET("pokemon/{pokemonName}")
    suspend fun getPokemonInfo(
        @Path("pokemonName") pokemonName: String
    ): PokemonResponse

    @GET("type/{typeName}")
    suspend fun getPokemonType(
        @Path("typeName") typeName: String
    ): PokemonTypeResponse
}