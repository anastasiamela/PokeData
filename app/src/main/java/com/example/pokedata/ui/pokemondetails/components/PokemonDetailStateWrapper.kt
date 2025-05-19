package com.example.pokedata.ui.pokemondetails.components

import androidx.compose.foundation.layout.offset
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.pokedata.data.model.ErrorModel
import com.example.pokedata.data.model.PokemonDetailsItem

@Composable
fun PokemonDetailStateWrapper(
    pokemon: PokemonDetailsItem?,
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
            PokemonDetailSection(
                pokemon = pokemon,
                modifier = modifier
                    .offset(y = (-20).dp)
            )
        }
    }
}