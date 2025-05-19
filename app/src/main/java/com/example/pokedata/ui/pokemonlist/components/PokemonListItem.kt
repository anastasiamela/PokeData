package com.example.pokedata.ui.pokemonlist.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.graphics.toArgb
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.example.pokedata.data.model.PokemonItem
import com.example.pokedata.data.network.ConnectivityObserver
import com.example.pokedata.ui.pokemonlist.PokemonListViewModel
import com.example.pokedata.ui.theme.TypeUnknown
import com.example.pokedata.util.PokemonType

@Composable
fun PokemonListItem(
    item: PokemonItem,
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: PokemonListViewModel = hiltViewModel()
) {

    val dominantTypeColor = PokemonType
        .fromApiName(item.types.firstOrNull() ?: "")
        ?.color ?: TypeUnknown

    val networkStatus by viewModel.networkStatus.collectAsState()
    var retryKey by remember { mutableIntStateOf(0) }
    LaunchedEffect(networkStatus) {
        if (networkStatus == ConnectivityObserver.Status.Available &&
            viewModel.shouldRetry(item.imageUrl)
        ) {
            // Only apply retryKey if image isn't previously loaded
            retryKey++
        }
    }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(120.dp)
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .background(Color.Transparent)
            .shadow(elevation = 4.dp, shape = RoundedCornerShape(12.dp))
            .clickable {
                navController.navigate("pokemonDetailsScreen/${item.name}/${dominantTypeColor.toArgb()}")
            },
        shape = RoundedCornerShape(12.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(
                            dominantTypeColor.copy(alpha = 0.4f),
                            dominantTypeColor
                        )
                    )
                )
                .padding(12.dp)
                .background(Color.Transparent),
            verticalAlignment = Alignment.CenterVertically
        ) {
            SubcomposeAsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(item.imageUrl)
                    .setParameter("retry_key", retryKey)
                    .crossfade(true)
                    .build(),
                contentDescription = item.name,
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(12.dp)),
                loading = {
                    CircularProgressIndicator(
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.scale(0.5f)
                    )
                },
                onError = {
                    viewModel.markImageFailed(item.imageUrl)
                },
                onSuccess = {
                    viewModel.markImageLoaded(item.imageUrl)
                }
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(vertical = 4.dp)
            ) {
                Text(
                    text = item.name.replaceFirstChar { it.uppercase() },
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row {
                    item.types.forEach { type ->
                        TypeBadge(type = type)
                        Spacer(modifier = Modifier.width(4.dp))
                    }
                }
            }

            Text(
                text = "#${item.number.toString().padStart(3, '0')}",
                style = MaterialTheme.typography.labelMedium,
                color = Color.White,

                modifier = Modifier
                    .align(Alignment.Bottom)
                    .alpha(0.7f)
            )
        }
    }
}
