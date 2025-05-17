package com.example.pokedata.data.model

data class PokemonItem(
    val name: String,
    val imageUrl: String,
    val number: Int,
    val types: List<String>
)

