package com.winton.nationapp.ui.nation_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.winton.nationapp.data.Nation
import com.winton.nationapp.data.NationRepository
import com.winton.nationapp.util.Routes
import com.winton.nationapp.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NationListViewModel @Inject constructor(
    private val repository: NationRepository
): ViewModel(){

    val nations = repository.getNations()

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private var deletedNation: Nation? = null

    fun onEvent(event: NationListEvent) {
        when(event) {
            is NationListEvent.OnNationClick -> {
                sendUiEvent(UiEvent.Navigate(Routes.ADD_EDIT_NATION + "?nationId=${event.nation.id}"))
            }
            is NationListEvent.OnAddNationClick -> {
                sendUiEvent(UiEvent.Navigate(Routes.ADD_EDIT_NATION))

            }
            is NationListEvent.OnUndoDeleteClick -> {
                deletedNation?.let {nation->
                    viewModelScope.launch {
                        repository.insertNation(nation)
                    }
                }
            }
            is NationListEvent.OnDeleteNationClick -> {
                viewModelScope.launch {
                    deletedNation = event.nation
                    repository.deleteNation(event.nation)
                    sendUiEvent(UiEvent.ShowSnackbar(
                        message = "País apagado",
                        action = "Desfeito"
                    ))
                }
            }
            is NationListEvent.OnDoneChange -> {
                viewModelScope.launch {
                    repository.insertNation(
                        event.nation.copy(
                            isDone = event.isDone
                        )
                    )
                }
            }
        }
    }

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}
