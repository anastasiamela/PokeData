package com.example.pokedata.ui.pokemonlist.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.unit.dp

@Composable
fun TypeBadge(type: String) {

    Box(
        modifier = Modifier
            .background(
                Color.White.copy(alpha = 0.2f),
                RoundedCornerShape(8.dp)
            )
            .padding(
                horizontal = 8.dp,
                vertical = 4.dp
            )
    ) {
        Text(
            text = type.capitalize(Locale.current),
            style = MaterialTheme.typography.labelSmall,
            color = Color.White
        )
    }
}