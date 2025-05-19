package com.example.pokedata.ui.pokemondetails

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.example.pokedata.ui.pokemondetails.components.PokemonDetailStateWrapper
import com.example.pokedata.ui.pokemondetails.components.PokemonDetailTopSection
import com.example.pokedata.ui.pokemondetails.components.PokemonDetailsErrorSection

@Composable
fun PokemonDetailsScreen(
    dominantTypeColor: Color,
    pokemonName: String,
    navController: NavController,
    topPadding: Dp = 60.dp,
    pokemonImageSize: Dp = 200.dp,
    viewModel: PokemonDetailsViewModel = hiltViewModel()
) {
    val pokemon by viewModel.pokemon.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()

    LaunchedEffect(pokemonName) {
        viewModel.loadPokemonInfo(pokemonName.lowercase())
    }

    if (error != null) {
        PokemonDetailsErrorSection(
            navController = navController,
            error = error!!,
            onRetry = {
                viewModel.loadPokemonInfo(pokemonName.lowercase())
            }
        )
        return
    }

    Box(
        modifier = Modifier
            .statusBarsPadding()
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        dominantTypeColor,
                        dominantTypeColor.copy(alpha = 0.3f)
                    )
                )
            )
            .padding(bottom = 16.dp)
    ) {
        PokemonDetailTopSection(
            navController = navController,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.2f)
                .align(Alignment.TopCenter)
                .background(dominantTypeColor)
        )
        PokemonDetailStateWrapper(
            pokemon = pokemon,
            isLoading = isLoading,
            error = error,
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = topPadding + pokemonImageSize / 2f,
                    start = 16.dp,
                    end = 16.dp,
                    bottom = 16.dp
                )
                .shadow(10.dp, RoundedCornerShape(12.dp))
                .clip(RoundedCornerShape(12.dp))
                .background(MaterialTheme.colorScheme.surface)
                .padding(16.dp)
                .align(Alignment.BottomCenter),
            loadingModifier = Modifier
                .size(100.dp)
                .align(Alignment.Center)
                .padding(
                    top = topPadding + pokemonImageSize / 2f,
                    start = 16.dp,
                    end = 16.dp,
                    bottom = 16.dp
                )
        )
        Box(
            contentAlignment = Alignment.TopCenter,
            modifier = Modifier
                .fillMaxSize()
        ) {
            pokemon?.imageUrl?.let {
                SubcomposeAsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(it)
                        .crossfade(true)
                        .build(),
                    contentDescription = pokemon!!.name,
                    modifier = Modifier
                        .size(pokemonImageSize)
                        .offset(y = topPadding),
                )
            }
        }
    }
}
