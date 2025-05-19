package com.example.pokedata.ui.pokemondetails.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.pokedata.data.model.PokemonStatDisplay

@Composable
fun PokemonStatsSection(
    stats: List<PokemonStatDisplay>,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.padding(8.dp)) {
        Text(
            text = "Base Stats",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier
                .align(Alignment.Start)
                .padding(bottom = 8.dp)

        )
        stats.forEach { stat ->
            PokemonStatBar(statDisplay = stat)
            Spacer(modifier = Modifier.height(6.dp))
        }
    }
}
