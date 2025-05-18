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
import kotlin.math.log

@HiltViewModel
class PokemonListViewModel @Inject constructor(
    private val repository: PokemonRepository
) : ViewModel() {

    private var currentPage = 0

    private val _pokemonList = MutableStateFlow<List<PokemonItem>>(emptyList())
    val pokemonList: StateFlow<List<PokemonItem>> = _pokemonList

    private val _loadError = MutableStateFlow("")
    val loadError: StateFlow<String> = _loadError

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _endReached = MutableStateFlow(false)
    val endReached: StateFlow<Boolean> = _endReached

    private var cachedPokemonList = mutableListOf<PokemonItem>()

    init {
        loadNextPage()
    }

    fun loadNextPage() {
        viewModelScope.launch {
            _isLoading.value = true
            _loadError.value = ""

            val offset = currentPage * PAGE_SIZE
            val result = repository.getPokemonList(limit = PAGE_SIZE, offset = offset)

            when (result) {
                is Resource.Success -> {
                    val entries = result.data?.results ?: emptyList()
                    val totalCount = result.data?.count ?: 0
                    _endReached.value = offset + PAGE_SIZE >= totalCount

                    val detailedEntries = entries.map { entry ->
                        async {
                            val detailResult = repository.getPokemonInfo(entry.name)
                            when (detailResult) {
                                is Resource.Success -> {
                                    val detail = detailResult.data
                                    PokemonItem(
                                        name = detail!!.name,
                                        number = detail.id,
                                        imageUrl = detail.sprites.other.officialArtwork.frontDefault,
                                        types = detail.types.map { typeSlot ->
                                            typeSlot.type.name
                                        }
                                    )
                                }

                                is Resource.Error -> {
                                    null // Could log error if needed
                                }
                            }
                        }
                    }.awaitAll().filterNotNull()

                    _pokemonList.value = _pokemonList.value + detailedEntries
                    currentPage++
                }

                is Resource.Error -> {
                    _loadError.value = result.message ?: "Unknown error"
                }
            }
            _isLoading.value = false
        }
    }

}