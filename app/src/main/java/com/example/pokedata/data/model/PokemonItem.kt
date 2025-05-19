package com.example.pokedata.data.model

import com.example.pokedata.data.remote.responses.PokemonResponse
import com.example.pokedata.data.remote.responses.PokemonX
import com.example.pokedata.data.remote.responses.Result
import com.example.pokedata.util.Constants.DEFAULT_IMAGE_URL

data class PokemonItem(
    val name: String,
    val imageUrl: String,
    val number: Int,
    val types: List<String>
) {
    companion object {
        fun fromPokemonInfoResponse(response: PokemonResponse): PokemonItem {
            return PokemonItem(
                name = response.name,
                number = response.id,
                imageUrl = response.sprites.other.officialArtwork.frontDefault,
                types = response.types.map { it.type.name }
            )
        }

        fun fallback(listItem: Result): PokemonItem {
            return PokemonItem(
                name = listItem.name,
                number = extractNumberFromUrl(listItem.url),
                imageUrl = DEFAULT_IMAGE_URL,
                types = emptyList()
            )
        }

        fun fallback(listItem: PokemonX): PokemonItem {
            return PokemonItem(
                name = listItem.name,
                number = extractNumberFromUrl(listItem.url),
                imageUrl = DEFAULT_IMAGE_URL,
                types = emptyList()
            )
        }

        private fun extractNumberFromUrl(url: String): Int {
            return url.trimEnd('/')
                .takeLastWhile { it.isDigit() }
                .toIntOrNull() ?: 0
        }
    }
}

