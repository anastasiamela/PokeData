package com.example.pokedata.util

enum class PokemonType(val apiName: String) {
    FIRE("fire"),
    WATER("water"),
    GRASS("grass"),
    ELECTRIC("electric"),
    DRAGON("dragon"),
    PSYCHIC("psychic"),
    GHOST("ghost"),
    DARK("dark"),
    STEEL("steel"),
    FAIRY("fairy");

    companion object {
        fun fromApiName(apiName: String): PokemonType? {
            return entries.find { it.apiName == apiName.lowercase() }
        }
    }
}
