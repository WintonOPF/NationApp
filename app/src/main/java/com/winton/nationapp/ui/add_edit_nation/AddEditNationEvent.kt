package com.winton.nationapp.ui.add_edit_nation

sealed class AddEditNationEvent{
    data class OnNameChange(val name: String): AddEditNationEvent()
    object OnSaveNationClick: AddEditNationEvent()
}
