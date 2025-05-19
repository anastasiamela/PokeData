package com.example.pokedata.ui.pokemondetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedata.common.error.ErrorHandler
import com.example.pokedata.data.model.ErrorModel
import com.example.pokedata.data.model.PokemonDetailsItem
import com.example.pokedata.data.network.ConnectivityObserver
import com.example.pokedata.repository.PokemonRepository
import com.example.pokedata.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonDetailsViewModel @Inject constructor(
    private val repository: PokemonRepository,
    private val errorHandler: ErrorHandler,
    private val connectivityObserver: ConnectivityObserver,
) : ViewModel() {

    private var lastRequestedPokemonName: String? = null

    private val _pokemon = MutableStateFlow<PokemonDetailsItem?>(null)
    val pokemon: StateFlow<PokemonDetailsItem?> = _pokemon

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<ErrorModel?>(null)
    val error: StateFlow<ErrorModel?> = _error

    init {
        // Retry data loading when back online and previous load failed
        viewModelScope.launch {
            connectivityObserver.observe().collect { status ->
                if (status == ConnectivityObserver.Status.Available && _error.value?.shouldShowRetry == true) {
                    lastRequestedPokemonName?.let { name ->
                        loadPokemonInfo(name)
                    }
                }
            }
        }
    }

    fun loadPokemonInfo(pokemonName: String) {
        lastRequestedPokemonName = pokemonName
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null

            val result = repository.getPokemonInfo(pokemonName)
            when (result) {
                is Resource.Success -> {
                    _pokemon.value = result.data?.let { PokemonDetailsItem.fromPokemonInfoResponse(it) }
                    _error.value = null
                }
                is Resource.Error -> {
                    _error.value = errorHandler.handleError(result.throwable ?: Exception())
                }
            }
            _isLoading.value = false
        }
    }
}
