package com.example.pokedata.ui.pokemondetails.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pokedata.ui.theme.TypeUnknown
import com.example.pokedata.util.PokemonType

@Composable
fun PokemonTypeSection(types: List<String>) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(16.dp)
    ) {
        for(type in types) {
            val backgroundColor = PokemonType
                .fromApiName(type)
                ?.color ?: TypeUnknown
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp)
                    .clip(CircleShape)
                    .background(backgroundColor)
                    .height(35.dp)
            ) {
                Text(
                    text = type.replaceFirstChar { it.uppercase() },
                    color = Color.White,
                    fontSize = 18.sp
                )
            }
        }
    }
}