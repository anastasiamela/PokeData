package com.example.pokedata

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.pokedata.ui.pokemondetails.PokemonDetailsScreen
import com.example.pokedata.ui.pokemonlist.PokemonListScreen
import com.example.pokedata.ui.theme.PokeDataTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PokeDataTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "pokemonListScreen") {
                    composable("pokemonListScreen") {
                        PokemonListScreen(navController = navController)
                    }
                    composable(
                        "pokemonDetailsScreen/{pokemonName}/{dominantTypeColor}",
                        arguments = listOf(
                            navArgument("pokemonName") {
                                type = NavType.StringType
                            },
                            navArgument("dominantTypeColor") {
                                type = NavType.IntType
                            }
                        )
                    ) {
                        val pokemonName = remember {
                            it.arguments?.getString("pokemonName")
                        }
                        val dominantTypeColor = remember {
                            val color = it.arguments?.getInt("dominantTypeColor")
                            color?.let { Color(it) } ?: Color.White
                        }
                        PokemonDetailsScreen(
                            dominantTypeColor = dominantTypeColor,
                            pokemonName = pokemonName?.lowercase() ?: "",
                            navController = navController
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    PokeDataTheme {
        Greeting("Android")
    }
}