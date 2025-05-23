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
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.pokedata.R
import com.example.pokedata.ui.components.PokemonGenericError
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
    val error by viewModel.error.collectAsState()
    val appliedFilterType by viewModel.selectedType.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()

    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var showSheet by remember { mutableStateOf(false) }
    var selectedType by remember { mutableStateOf<PokemonType?>(null) }
    var textState by remember { mutableStateOf("") }

    // Sync on first composition or when query changes from ViewModel
    LaunchedEffect(searchQuery) {
        textState = searchQuery
    }

    if (showSheet) {
        // Sync selectedType with ViewModel when opening the sheet
        LaunchedEffect(Unit) {
            selectedType = appliedFilterType
        }

        ModalBottomSheet(
            onDismissRequest = {
                showSheet = false
                selectedType = null
            },
            sheetState = sheetState
        ) {
            TypeFilterMenu(
                selectedType = selectedType,
                onTypeSelected = { type ->
                    selectedType = if (selectedType == type) null else type
                },
                onClearSelection = {
                    selectedType = null
                    viewModel.selectType(null) // Reset in ViewModel
                    showSheet = false
                },
                onApply = {
                    viewModel.selectType(selectedType) // Apply in ViewModel
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
                    modifier = Modifier.weight(1f),
                    text = textState,
                    onTextChange = { textState = it },
                    onSearch = { query ->
                        viewModel.onSearchSubmit(query)
                    },
                    onClear = {
                        viewModel.onSearchCancel()
                    }
                )

                BadgedBox(
                    badge = {
                        if (appliedFilterType != null) {
                            Badge(
                                containerColor = MaterialTheme.colorScheme.primary,
                                contentColor = Color.White,
                                modifier = Modifier.size(20.dp)
                            ) {
                                Text(
                                    text = "1",
                                    fontSize = 12.sp,
                                    modifier = Modifier.padding(2.dp)
                                )
                            }
                        }
                    }
                ) {
                    IconButton(onClick = {
                        showSheet = true
                    }) {
                        Icon(
                            imageVector = Icons.Default.FilterList,
                            contentDescription = "Filter",
                            modifier = Modifier.size(50.dp),
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

            if (pokemonList.isEmpty() && error?.title?.isNotBlank() == true) {
                error?.let {
                    PokemonGenericError(
                        error = it,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(top = 64.dp, start = 24.dp, end = 24.dp),
                        showErrorImage = true,
                        onRetry = {
                            viewModel.loadPokemonListRetry()
                        }
                    )
                }
            } else {
                PokemonList(
                    pokemonList = pokemonList,
                    isLoading = isLoading,
                    error = error,
                    endReached = endReached,
                    loadNextPage = {
                        viewModel.loadNextPage()
                    },
                    onRetry = {
                        viewModel.loadPokemonListRetry()
                    },
                    navController = navController,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}


