package com.shop.ecommerce.ui.main

import androidx.lifecycle.viewModelScope
import com.shop.utils.coroutine.ContextProvider
import com.shop.utils.mvi.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import com.shop.ecommerce.ui.main.HomeViewContract.Action
import com.shop.ecommerce.ui.main.HomeViewContract.Effect
import com.shop.ecommerce.ui.main.HomeViewContract.State
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val contextProvider: ContextProvider,
) : MviViewModel<State, Action, Effect>(State()) {

    override fun onAction(action: Action) {
        when (action) {
            Action.OnCountButtonClick -> {
                // increase counter to update number on the Send toast button
                updateState { copy(counter = counter + 1) }
                // inform ui for new toast message with payload (count)
                sendEffect(Effect.ShowToastMessage(count = currentState.counter))
            }

            Action.OnOpenDetailButtonClick -> {
                sendEffect(Effect.NavigateDetailScreen)
            }
        }
    }

    init {
        // we need to fetch main content of dashboard screen on VM initialization from data source
        // (remote or local) and update ui state accordingly.
        fetchInitialData()
    }

    private fun fetchInitialData() {
        viewModelScope.launch {
            val initialState = withContext(contextProvider.io) {
                // Do IO scope call to fetch screen data
                State(message = "Welcome to Dashboard Screen")
            }

            // update UI state with fetched initial state
            updateState { initialState }
        }
    }
}
