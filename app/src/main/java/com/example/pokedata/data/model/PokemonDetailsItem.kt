package com.example.pokedata.data.model

import com.example.pokedata.data.remote.responses.PokemonResponse
import com.example.pokedata.util.PokemonStat

data class PokemonDetailsItem(
    val name: String,
    val imageUrl: String,
    val number: Int,
    val types: List<String>,
    val weight: Int,
    val height: Int,
    val stats: List<PokemonStatDisplay>
) {
    companion object {
        fun fromPokemonInfoResponse(response: PokemonResponse): PokemonDetailsItem {
            val mappedStats = response.stats.mapNotNull { statEntry ->
                PokemonStat.fromApiName(statEntry.stat.name)?.let { matchedStat ->
                    PokemonStatDisplay(stat = matchedStat, value = statEntry.baseStat)
                }
            }

            return PokemonDetailsItem(
                name = response.name,
                number = response.id,
                imageUrl = response.sprites.other.officialArtwork.frontDefault,
                types = response.types.map { it.type.name },
                weight = response.weight,
                height = response.height,
                stats = mappedStats
            )
        }
    }
}
