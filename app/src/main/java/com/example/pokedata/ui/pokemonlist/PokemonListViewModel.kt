package com.example.pokedata.ui.pokemonlist

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedata.common.error.ErrorHandler
import com.example.pokedata.common.exception.NotFoundException
import com.example.pokedata.data.model.ErrorModel
import com.example.pokedata.data.model.PokemonItem
import com.example.pokedata.data.network.ConnectivityObserver
import com.example.pokedata.data.remote.responses.Pokemon
import com.example.pokedata.repository.PokemonRepository
import com.example.pokedata.util.Constants.PAGE_SIZE
import com.example.pokedata.util.PokemonType
import com.example.pokedata.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonListViewModel @Inject constructor(
    private val repository: PokemonRepository,
    private val errorHandler: ErrorHandler,
    private val connectivityObserver: ConnectivityObserver
) : ViewModel() {

    private var currentPage = 0

    private val _pokemonList = MutableStateFlow<List<PokemonItem>>(emptyList())
    val pokemonList: StateFlow<List<PokemonItem>> = _pokemonList

    private val _error = MutableStateFlow<ErrorModel?>(null)
    val error: StateFlow<ErrorModel?> = _error

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _endReached = MutableStateFlow(false)
    val endReached: StateFlow<Boolean> = _endReached

    private val _selectedType = MutableStateFlow<PokemonType?>(null)
    val selectedType: StateFlow<PokemonType?> = _selectedType

    private val _filteredByTypeList = mutableListOf<Pokemon>()

    private val _failedImageUrls = mutableStateListOf<String>()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    private val _searchResult = MutableStateFlow<List<PokemonItem>>(emptyList())
    val searchResult: StateFlow<List<PokemonItem>> = _searchResult

    val networkStatus: StateFlow<ConnectivityObserver.Status> =
        connectivityObserver.observe()
            .stateIn(viewModelScope, SharingStarted.Lazily, ConnectivityObserver.Status.Unavailable)

    init {
        loadNextPage()
        // Retry data loading when back online and previous load failed
        viewModelScope.launch {
            connectivityObserver.observe().collect { status ->
                if (status == ConnectivityObserver.Status.Available && _error.value?.shouldShowRetry == true) {
                    onRetry()
                }
            }
        }
    }

    fun onRetry() {
        if (_error.value?.shouldShowRetry == false) return
        _error.value = null
        if (!_searchQuery.value.isBlank()) {
            searchPokemon(_searchQuery.value)
        }
        loadNextPage()
    }

    fun onSearchSubmit(query: String) {
        if (query.isBlank()) return
        _searchQuery.value = query
        searchPokemon(query)
    }

    fun searchPokemon(query: String) {
        if (_selectedType.value == null) {
            searchPokemonByName(query)
        } else {
//            searchPokemonByQuery(query)
        }
    }

    fun selectType(type: PokemonType?) {
        _selectedType.value = type
        resetPagination()
        if (type == null) {
            loadNextPage()
        } else {
            loadAllFilteredPokemonsByType(type)
        }
    }

    fun loadNextPage() {
        if (_selectedType.value != null) {
            if (_filteredByTypeList.isEmpty()) {
                loadAllFilteredPokemonsByType(_selectedType.value!!)
                return
            }
            loadNextFilteredPage()
        } else {
            loadNextUnfilteredPage()
        }
    }

    private fun resetPagination() {
        currentPage = 0
        _endReached.value = false
        _error.value = null
        _pokemonList.value = emptyList()
        _filteredByTypeList.clear()
    }

    fun loadNextUnfilteredPage() {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null

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
                    }.awaitAll()

                    _pokemonList.value = _pokemonList.value + pokemonItems
                    currentPage++
                }

                is Resource.Error -> {
                    _error.value = errorHandler.handleError(result.throwable ?: Exception())
                }
            }
            _isLoading.value = false
        }
    }

    private fun loadAllFilteredPokemonsByType(type: PokemonType) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null

            val result = repository.getPokemonListByType(type.typeName)

            when (result) {
                is Resource.Success -> {
                    val pokemons = result.data ?: emptyList()
                    if (pokemons.isEmpty()) {
                        _error.value = errorHandler.handleError(NotFoundException())
                        _isLoading.value = false
                        return@launch
                    }
                    _filteredByTypeList.clear()
                    _filteredByTypeList.addAll(pokemons)
                    _isLoading.value = false
                    loadNextFilteredPage()
                }

                is Resource.Error -> {
                    _error.value = errorHandler.handleError(result.throwable ?: Exception())
                }
            }
            _isLoading.value = false
        }
    }

    private fun loadNextFilteredPage() {
        if (_isLoading.value || _endReached.value) return
        viewModelScope.launch {
            _isLoading.value = true
            val start = currentPage * PAGE_SIZE
            val end = minOf(start + PAGE_SIZE, _filteredByTypeList.size)

            if (start >= _filteredByTypeList.size) {
                _endReached.value = true
                _isLoading.value = false
                return@launch
            }

            val pokemonsOfThePage = _filteredByTypeList.subList(start, end)
            val pokemonItems = pokemonsOfThePage.map { item ->
                async {
                    val info = repository.getPokemonInfo(item.pokemon.name)
                    when (info) {
                        is Resource.Success -> PokemonItem.fromPokemonInfoResponse(info.data!!)
                        is Resource.Error -> PokemonItem.fallbackFromPokemonTypeListResponse(item.pokemon)
                    }
                }
            }.awaitAll()

            _pokemonList.value = _pokemonList.value + pokemonItems
            currentPage++
            _endReached.value = end >= _filteredByTypeList.size
            _isLoading.value = false
        }
    }

    fun searchPokemonByName(name: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null

            val result = repository.getPokemonInfo(name.lowercase())

            when (result) {
                is Resource.Success -> {
                    val info = result.data
                    val item = info?.let { PokemonItem.fromPokemonInfoResponse(it) }
                    _searchResult.value = item?.let { listOf(it) } ?: emptyList()
                    _pokemonList.value = _searchResult.value
                    _endReached.value = true // Because we show only 1 item
                }

                is Resource.Error -> {
                    _searchResult.value = emptyList()
                    _pokemonList.value = emptyList()
                    _error.value = errorHandler.handleError(result.throwable ?: Exception())
                }
            }
            _isLoading.value = false
        }
    }


    fun markImageFailed(url: String) {
        if (!_failedImageUrls.contains(url)) _failedImageUrls.add(url)
    }

    fun markImageLoaded(url: String) {
        _failedImageUrls.remove(url)
    }

    fun shouldRetry(url: String): Boolean {
        return _failedImageUrls.contains(url)
    }

}