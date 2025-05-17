package com.example.pokedata.ui.pokemonlist.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.pokedata.data.model.PokemonItem

@Composable
fun PokemonList(
    pokemonList: List<PokemonItem>,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(2.dp)
    ) {
        items(pokemonList) { pokemon ->
            PokemonListItem(
                item = pokemon,
                navController = navController,
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
    }
}