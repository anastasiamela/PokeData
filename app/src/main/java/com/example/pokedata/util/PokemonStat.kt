package com.example.pokedata.util

enum class PokemonStat(val statApiName: String, val statLabel: String, val statMax: Long) {
    HP("hp", "HP", 255),
    ATTACK("attack", "Attack", 190),
    DEFENSE("defense", "Defense", 250),
    SPECIAL_ATTACK("special-attack", "Sp. Attack", 194),
    SPECIAL_DEFENSE("special-defense", "Sp. Defense", 250),
    SPEED("speed", "Speed", 200);

    companion object {
        fun fromApiName(statApiName: String): PokemonStat? {
            return entries.firstOrNull { it.statApiName == statApiName }
        }
    }
}