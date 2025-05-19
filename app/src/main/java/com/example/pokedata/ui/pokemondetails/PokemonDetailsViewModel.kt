package com.example.pokedata.ui.pokemondetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedata.common.error.ErrorHandler
import com.example.pokedata.data.model.ErrorModel
import com.example.pokedata.data.remote.responses.PokemonResponse
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
) : ViewModel() {

    private val _pokemon = MutableStateFlow<PokemonResponse?>(null)
    val pokemon: StateFlow<PokemonResponse?> = _pokemon

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<ErrorModel?>(null)
    val error: StateFlow<ErrorModel?> = _error

    fun loadPokemonInfo(pokemonName: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null

            val result = repository.getPokemonInfo(pokemonName)
            when (result) {
                is Resource.Success -> {
                    _pokemon.value = result.data
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
