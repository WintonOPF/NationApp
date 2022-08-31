package com.winton.nationapp.ui.add_edit_nation

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.winton.nationapp.util.UiEvent
import kotlinx.coroutines.flow.collect

@Composable
fun AddEditNationScreen(
    onPopBackStack: () -> Unit,
    viewModel: AddEditNationViewModel = hiltViewModel(),
    showToast: ((String) -> Unit)? = null
) {
    val scaffoldState:ScaffoldState = rememberScaffoldState()
    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event:UiEvent ->
            when(event) {
                is UiEvent.PopBackStack -> {
                    onPopBackStack()
                    showToast?.invoke("Adicionado")
                }
                is UiEvent.ShowSnackbar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message,
                        actionLabel = event.action
                    )
                }
                else -> Unit
            }
        }
    }
    Scaffold(
        scaffoldState = scaffoldState,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        floatingActionButton = {
            FloatingActionButton(onClick = {
                viewModel.onEvent(AddEditNationEvent.OnSaveNationClick)
            }) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "Salva"
                )
            }
        }
    ) {
        Column (
            modifier = Modifier.fillMaxSize()
        ) {
            TextField(
                value = viewModel.name,
                onValueChange = {
                    viewModel.onEvent(AddEditNationEvent.OnNameChange(it))
                },
                placeholder = {
                    Text(text = "Nome do país")
                },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}