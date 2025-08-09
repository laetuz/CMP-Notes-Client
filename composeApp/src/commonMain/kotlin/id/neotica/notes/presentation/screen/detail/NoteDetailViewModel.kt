package id.neotica.notes.presentation.screen.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import co.touchlab.kermit.Logger
import id.neotica.notes.data.NoteRepositoryImpl
import id.neotica.notes.domain.Note
import id.neotica.notes.presentation.navigation.Screen
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class NoteDetailViewModel(
    private val noteRepo: NoteRepositoryImpl,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    private val _notes: MutableStateFlow<Note?> = MutableStateFlow(null)
    val notes = _notes.asStateFlow()
    val args = savedStateHandle.toRoute<Screen.NoteDetailScreen>()

    init {
        getNotes(args.id)
    }

    private fun getNotes(noteId: String) = viewModelScope.launch {
        noteRepo.getNote(noteId).collect {
            _notes.value = it
            Logger.d("✨Its Working ✨") { it.toString() }
        }
    }
}