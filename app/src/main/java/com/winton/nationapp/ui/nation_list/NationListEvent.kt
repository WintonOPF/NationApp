package com.winton.nationapp.ui.nation_list

import com.winton.nationapp.data.Nation

sealed class NationListEvent {
    data class OnDeleteNationClick(val nation: Nation): NationListEvent()
    data class OnDoneChange(val nation: Nation, val isDone: Boolean): NationListEvent()
    object OnUndoDeleteClick: NationListEvent()
    data class OnNationClick(val nation: Nation): NationListEvent()
    object OnAddNationClick: NationListEvent()
}
