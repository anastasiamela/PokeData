package com.example.pokedata.ui.pokemonlist

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.pokedata.R
import com.example.pokedata.ui.pokemonlist.components.PokemonList
import com.example.pokedata.ui.pokemonlist.components.SearchBar
import com.example.pokedata.ui.pokemonlist.components.TypeFilterMenu
import com.example.pokedata.util.PokemonType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PokemonListScreen(
    navController: NavController,
    viewModel: PokemonListViewModel = hiltViewModel()
) {
    val pokemonList by viewModel.pokemonList.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val endReached by viewModel.endReached.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()

    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val scope = rememberCoroutineScope()
    var showSheet by remember { mutableStateOf(false) }
    var selectedType by remember { mutableStateOf<PokemonType?>(null) }


    if (showSheet) {
        ModalBottomSheet(
            onDismissRequest = { showSheet = false },
            sheetState = sheetState
        ) {
            TypeFilterMenu(
                selectedType = selectedType,
                onTypeSelected = { type ->
                    selectedType = if (selectedType == type) null else type
                },
                onClearSelection = {
                    selectedType = null
                    showSheet = false
                },
                onApply = {
                    showSheet = false
                }
            )
        }
    }

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
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                SearchBar(
                    hint = "Search Pokémon...",
                    modifier = Modifier.weight(1f)
                ) {
                    // viewModel.searchPokemonList(it)
                }

                IconButton(onClick = {
                    showSheet = true
                }) {
                    Icon(
                        Icons.Default.FilterList,
                        contentDescription = "Filter",
                        modifier = Modifier.size(50.dp),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
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


