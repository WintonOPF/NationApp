package com.winton.nationapp.ui.add_edit_nation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.winton.nationapp.data.Nation
import com.winton.nationapp.data.NationRepository
import com.winton.nationapp.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditNationViewModel @Inject constructor(
    private val repository: NationRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    var nation by mutableStateOf<Nation?>(null)
            private set

    var name by mutableStateOf("")
            private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        val nationId = savedStateHandle.get<Int>("nationId")!!
        if(nationId != -1) {
            viewModelScope.launch {
                repository.getNationById(nationId)?.let { nation ->
                    name = nation.name
                    this@AddEditNationViewModel.nation = nation
                }
            }
        }
    }

    fun onEvent(event: AddEditNationEvent) {
        when(event) {
            is AddEditNationEvent.OnNameChange -> {
                name = event.name
            }
            is AddEditNationEvent.OnSaveNationClick -> {
                viewModelScope.launch {
                    if(name.isBlank()) {
                        sendUiEvent(UiEvent.ShowSnackbar(
                            message = "O nome do país não pode estar em branco"
                        ))
                        return@launch
                    }
                    repository.insertNation(
                        Nation(
                            name = name,
                            isDone = nation?.isDone ?: false,
                            id = nation?.id
                        )
                    )
                    sendUiEvent(UiEvent.PopBackStack)
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