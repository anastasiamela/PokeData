package com.example.pokedata.ui.pokemonlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedata.data.model.PokemonItem
import com.example.pokedata.repository.PokemonRepository
import com.example.pokedata.util.Constants.PAGE_SIZE
import com.example.pokedata.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonListViewModel @Inject constructor(
    private val repository: PokemonRepository
) : ViewModel() {

    private var currentPage = 0

    private val _pokemonList = MutableStateFlow<List<PokemonItem>>(emptyList())
    val pokemonList: StateFlow<List<PokemonItem>> = _pokemonList

    private val _errorMessage = MutableStateFlow("")
    val errorMessage: StateFlow<String> = _errorMessage

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _endReached = MutableStateFlow(false)
    val endReached: StateFlow<Boolean> = _endReached

    init {
        loadNextPage()
    }

    fun loadNextPage() {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = ""

            val offset = currentPage * PAGE_SIZE
            val result = repository.getPokemonList(limit = PAGE_SIZE, offset = offset)

            when (result) {
                is Resource.Success -> {
                    val pokemonListItems = result.data?.results ?: emptyList()
                    val totalCount = result.data?.count ?: 0
                    _endReached.value = offset + PAGE_SIZE >= totalCount

                    val pokemonItems = pokemonListItems.map { pokemonListItem ->
                        async {
                            val pokemonWithInfo = repository.getPokemonInfo(pokemonListItem.name)
                            when (pokemonWithInfo) {
                                is Resource.Success -> {
                                    val info = pokemonWithInfo.data
                                    PokemonItem.fromPokemonInfoResponse(info!!)
                                }

                                is Resource.Error -> {
                                    PokemonItem.fallbackFromPokemonListResponse(pokemonListItem)
                                }
                            }
                        }
                    }.awaitAll().filterNotNull()

                    _pokemonList.value = _pokemonList.value + pokemonItems
                    currentPage++
                }

                is Resource.Error -> {
                    _errorMessage.value = result.message ?: "Unknown error"
                }
            }
            _isLoading.value = false
        }
    }

}