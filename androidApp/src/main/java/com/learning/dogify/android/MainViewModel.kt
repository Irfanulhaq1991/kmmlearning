package com.learning.dogify.android

import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.learning.dogify.model.Breed
import com.learning.dogify.repository.BreedsRepository
import com.learning.dogify.usecase.FetchBreedsUseCase
import com.learning.dogify.usecase.GetBreedsUseCase
import com.learning.dogify.usecase.ToggleFavouriteStateUseCase
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.lang.Exception

class MainViewModel(
    breedsRepository: BreedsRepository,
    private val getBreeds: GetBreedsUseCase,
    private val fetchBreads: FetchBreedsUseCase,
    private val onToggleFavouriteState: ToggleFavouriteStateUseCase,
    ) : ViewModel() {
    private val _state = MutableStateFlow(State.LOADING)
    val state: StateFlow<State> = _state

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing

    private val _events = MutableSharedFlow<Event>()
    val event: SharedFlow<Event> = _events

    private val _shouldFilterFavorites = MutableStateFlow(false)
    val shouldFilterFavorites: StateFlow<Boolean> = _shouldFilterFavorites



    val breeds =
        breedsRepository.breeds.combine(shouldFilterFavorites) { breeds, shouldFilterFavorites ->
            if (shouldFilterFavorites) {
                breeds.filter { it.isFavorite }
            } else {
                breeds
            }.also {
                _state.value =
                    if (it.isEmpty())
                        State.EMPTY
                    else
                        State.NORMAL
            }

        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(),
            emptyList()
        )

    fun onToggleFavouriteFilter() {
        _shouldFilterFavorites.value =
            !shouldFilterFavorites.value
    }

    fun onFavouriteTapped(breed: Breed) {
        viewModelScope.launch {
            try {
                onToggleFavouriteState(breed)
            } catch (e: Exception) {
                _events.emit(Event.Error)
            }
        }
    }


    fun refresh() {
        loadData(true)
    }

    private fun loadData(isForcedRefresh: Boolean) {
        val getData: suspend () -> List<Breed> = {
            if (isForcedRefresh) fetchBreads.invoke() else getBreeds.invoke()
        }

        if (isForcedRefresh) {
            _isRefreshing.value = true
        } else {
            _state.value = State.LOADING
        }

        viewModelScope.launch {
            _state.value = try {
                getData()
                State.NORMAL
            } catch (e: Exception) {
                State.ERROR
            }
            _isRefreshing.value = false
        }
    }


}

enum class State {
    LOADING,
    NORMAL,
    ERROR,
    EMPTY
}

enum class Event {
    Error
}