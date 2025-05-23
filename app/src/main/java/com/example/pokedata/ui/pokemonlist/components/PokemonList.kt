package com.example.pokedata.ui.pokemonlist.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.pokedata.data.model.ErrorModel
import com.example.pokedata.data.model.PokemonItem
import com.example.pokedata.ui.components.PokemonGenericError

@Composable
fun PokemonList(
    pokemonList: List<PokemonItem>,
    isLoading: Boolean,
    error: ErrorModel?,
    endReached: Boolean,
    loadNextPage: () -> Unit,
    onRetry: () -> Unit,
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(2.dp)
    ) {
        itemsIndexed(pokemonList) { index, pokemon ->
            if (index >= pokemonList.size - 1 && !isLoading && !endReached) {
                loadNextPage()
            }
            PokemonListItem(
                item = pokemon,
                navController = navController,
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
        if (isLoading) {
            item {
                PokemonListLoading()
            }
        }
        if (error?.title?.isNotBlank() == true) {
            item {
                PokemonGenericError(
                    error = error,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    onRetry = { onRetry() }
                )
            }
        }
    }

}

@Composable
fun PokemonListLoading() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.scale(0.5f)
        )
    }
}
