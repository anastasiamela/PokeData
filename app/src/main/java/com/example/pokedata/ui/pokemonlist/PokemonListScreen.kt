package com.example.pokedata.ui.pokemonlist

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.pokedata.R
import com.example.pokedata.ui.pokemonlist.components.PokemonList
import com.example.pokedata.ui.pokemonlist.components.SearchBar

@Composable
fun PokemonListScreen(
    navController: NavController,
    viewModel: PokemonListViewModel = hiltViewModel()
) {
    val pokemonList by viewModel.pokemonList.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val endReached by viewModel.endReached.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()

    Surface(
        color = MaterialTheme.colorScheme.background,
        modifier = Modifier.fillMaxSize()
    ) {
        Column {
            Spacer(modifier = Modifier.height(60.dp))
            Image(
                painter = painterResource(R.drawable.ic_international_pokemon_logo),
                contentDescription = "international_pokemon_logo",
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.height(4.dp))
            SearchBar(
                hint = "Search Pokémon...",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
//                viewModel.searchPokemonList(it)
            }
            PokemonList(
                pokemonList = pokemonList,
                isLoading = isLoading,
                errorMessage = errorMessage,
                endReached = endReached,
                loadNextPage = {
                    viewModel.loadNextPage()
                },
                onRetry = {
                    viewModel.loadNextPage()
                },
                navController = navController,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

