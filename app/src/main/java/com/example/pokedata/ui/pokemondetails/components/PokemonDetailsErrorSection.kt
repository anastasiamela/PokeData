package com.example.pokedata.ui.pokemondetails.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.pokedata.data.model.ErrorModel
import com.example.pokedata.ui.components.PokemonListError

@Composable
fun PokemonDetailsErrorSection(
    navController: NavController,
    error: ErrorModel,
    onRetry: (() -> Unit)? = null
) {
    Box(
        modifier = Modifier
            .statusBarsPadding()
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
            .padding(bottom = 16.dp)
    ) {
        PokemonDetailTopSection(
            navController = navController,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.2f)
                .align(Alignment.TopCenter)
                .background(color = MaterialTheme.colorScheme.background)
        )
        PokemonListError(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            error = error,
            showErrorImage = true,
            onRetry = onRetry
        )
        return
    }
}
