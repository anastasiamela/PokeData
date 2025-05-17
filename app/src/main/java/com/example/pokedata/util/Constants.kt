package com.example.pokedata.util

import com.example.pokedata.data.model.PokemonItem

object Constants {
    const val BASE_URL = "https://pokeapi.co/api/v2/"
    const val PAGE_SIZE = 10

    val samplePokemonList = listOf(
        PokemonItem(
            name = "Bulbasaur",
            imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/1.png"
        ,
            number = 1,
            types = listOf("GRASS")
        ),
        PokemonItem(
            name = "Charmander",
            imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/4.png",
            number = 4,
            types = listOf("FIRE", "DRAGON")
        ),
        PokemonItem(
            name = "Squirtle",
            imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/7.png",
            number = 7,
            types = listOf("WATER")
        ),
        PokemonItem(
            name = "Pikachu",
            imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/25.png",
            number = 25,
            types = listOf("ELECTRIC")
        ),
        PokemonItem(
            name = "Jigglypuff",
            imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/39.png",
            number = 39,
            types = listOf("FAIRY")
        ),
        PokemonItem(
            name = "Gengar",
            imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/94.png",
            number = 94,
            types = listOf("GHOST", "POISON")
        ),
        PokemonItem(
            name = "Lucario",
            imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/448.png",
            number = 448,
            types = listOf("STEEL", "FIGHTING")
        )
    )

}