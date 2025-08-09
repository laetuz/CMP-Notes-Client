package id.neotica.notes.presentation.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.touchlab.kermit.Logger
import id.neotica.notes.data.NoteRepositoryImpl
import id.neotica.notes.domain.Note
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class NoteViewModel(
    private val noteRepo: NoteRepositoryImpl
): ViewModel() {

    private val _notes: MutableStateFlow<List<Note>> = MutableStateFlow(emptyList())
    val notes = _notes.asStateFlow()

    init {
        getNotes()
    }

    private fun getNotes() = viewModelScope.launch {
        noteRepo.getNotes().collect {
            _notes.value = it
            Logger.d("✨Its Working ✨") { it.toString() }
        }
    }
}