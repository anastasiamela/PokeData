package com.example.pokedata.ui.pokemonlist.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pokedata.ui.theme.ApplyDarkColor
import com.example.pokedata.ui.theme.ApplyLightColor
import com.example.pokedata.ui.theme.ClearDarkColor
import com.example.pokedata.ui.theme.ClearLightColor
import com.example.pokedata.util.PokemonType


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun TypeFilterMenu(
    selectedType: PokemonType?,
    onTypeSelected: (PokemonType) -> Unit,
    onClearSelection: () -> Unit,
    onApply: () -> Unit
) {
    val chipPairs = PokemonType.entries.chunked(2)

    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = "Filter by Type",
            style = MaterialTheme.typography.titleLarge
        )
        Spacer(modifier = Modifier.height(20.dp))

        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            chipPairs.forEach { pair ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    pair.forEach { type ->
                        val isSelected = selectedType == type
                        FilterChip(
                            selected = isSelected,
                            onClick = { onTypeSelected(type) },
                            label = {
                                Text(
                                    text = type.typeName.replaceFirstChar { it.uppercase() },
                                    style = MaterialTheme.typography.labelMedium.copy(fontSize = 20.sp)
                                )
                            },
                            modifier = Modifier
                                .weight(1f)
                                .height(48.dp),
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = type.color,
                                containerColor = type.color.copy(alpha = 0.3f),
                                labelColor = Color.White
                            ),
                            shape = RoundedCornerShape(16.dp)
                        )
                    }

                    // Fill the second column if this is an odd pair
                    if (pair.size == 1) {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
            }
        }


        Spacer(modifier = Modifier.height(20.dp))

        FilterButtons(
            onClearSelection = onClearSelection,
            onApply = onApply
        )
    }
}

@Composable
fun FilterButtons(
    onClearSelection: () -> Unit,
    onApply: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        GradientButton(
            text = "Clear",
            gradient = Brush.horizontalGradient(
                listOf(
                    ClearLightColor,
                    ClearDarkColor
                ) // orange
            ),
            onClick = onClearSelection
        )

        GradientButton(
            text = "Apply",
            gradient = Brush.horizontalGradient(
                listOf(
                    ApplyLightColor,
                    ApplyDarkColor
                )
            ),
            onClick = onApply
        )
    }
}

@Composable
fun RowScope.GradientButton(
    text: String,
    gradient: Brush,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .weight(1f)
            .height(48.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(brush = gradient)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = Color.White,
            style = MaterialTheme.typography.labelMedium.copy(fontSize = 20.sp),
            fontWeight = FontWeight.Bold
        )
    }
}
