package com.example.pokedata.repository

import com.example.pokedata.data.remote.PokeApi
import com.example.pokedata.data.remote.responses.Pokemon
import com.example.pokedata.data.remote.responses.PokemonListResponse
import com.example.pokedata.data.remote.responses.PokemonResponse
import com.example.pokedata.data.remote.responses.PokemonTypeResponse
import com.example.pokedata.util.Resource
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@ActivityScoped
class PokemonRepository @Inject constructor(
    private val api: PokeApi
) {
    suspend fun getPokemonList(limit: Int, offset: Int) : Resource<PokemonListResponse> {
        val response = try {
            api.getPokemonList(limit, offset)
        } catch (e: Exception) {
            return Resource.Error("An unknown error occurred.", e)
        }
        return Resource.Success(response)
    }

    suspend fun getPokemonInfo(pokemonName: String)  : Resource<PokemonResponse> {
        val response = try {
            api.getPokemonInfo(pokemonName)
        } catch (e: Exception) {
            return Resource.Error("An unknown error occurred.", e)
        }
        return Resource.Success(response)
    }

    suspend fun getPokemonType(pokemonTypeName: String)  : Resource<PokemonTypeResponse> {
        val response = try {
            api.getPokemonType(pokemonTypeName)
        } catch (e: Exception) {
            return Resource.Error("An unknown error occurred.", e)
        }
        return Resource.Success(response)
    }

    suspend fun getPokemonListByType(pokemonTypeName: String)  : Resource<List<Pokemon>> {
        val response = try {
            api.getPokemonType(pokemonTypeName).pokemon
        } catch (e: Exception) {
            return Resource.Error("An unknown error occurred.", e)
        }
        return Resource.Success(response)
    }
}