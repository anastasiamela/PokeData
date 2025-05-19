package com.example.pokedata.ui.pokemondetails.components

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.pokedata.data.model.ErrorModel
import com.example.pokedata.data.remote.responses.PokemonResponse

@Composable
fun PokemonDetailStateWrapper(
    pokemon: PokemonResponse?,
    isLoading: Boolean,
    error: ErrorModel?,
    modifier: Modifier = Modifier,
    loadingModifier: Modifier = Modifier
) {
    when {
        isLoading -> {
            CircularProgressIndicator(
                color = MaterialTheme.colorScheme.primary,
                modifier = loadingModifier
            )
        }

        error != null -> {
            Text(
                text = error.title,
                color = Color.Red,
                modifier = modifier
            )
        }

        pokemon != null -> {
            // TODO: Render actual Pokemon details here
            Text(
                text = "Pokemon Name: ${pokemon.name}",
                modifier = modifier
            )
        }
    }
}