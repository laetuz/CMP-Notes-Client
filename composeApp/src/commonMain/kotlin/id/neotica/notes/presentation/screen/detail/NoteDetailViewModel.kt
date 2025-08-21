package id.neotica.notes.presentation.screen.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import co.touchlab.kermit.Logger
import id.neotica.notes.data.NoteRepositoryImpl
import id.neotica.notes.domain.model.ApiResult
import id.neotica.notes.domain.model.Note
import id.neotica.notes.presentation.navigation.Screen
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class NoteDetailViewModel(
    private val noteRepo: NoteRepositoryImpl,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    private val _notes: MutableStateFlow<Note?> = MutableStateFlow(null)
    val notes = _notes
    val args = savedStateHandle.toRoute<Screen.NoteDetailScreen>()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _message = MutableStateFlow("")
    val message = _message.asStateFlow()

    init {
        if (args.id.isNotEmpty()) getNotes(args.id)
    }

    private fun getNotes(noteId: String) = viewModelScope.launch {
        noteRepo.getNote(noteId).collect {
            when (it) {
                is ApiResult.Loading -> _isLoading.value = true
                is ApiResult.Success -> {
                    _isLoading.value = false
                    _notes.value = it.data
                }
                is ApiResult.Error -> {
                    _isLoading.value = false
                    _message.value = it.errorMessage.toString()
                }
            }
        }
    }

    fun updateNote(note: Note) = viewModelScope.launch {
        noteRepo.updateNote(note.id.toString(), note).collect {
            when (it) {
                is ApiResult.Loading -> _isLoading.value = true
                is ApiResult.Success -> {
                    _isLoading.value = false
                    _message.value = it.data.toString()
                    Logger.d("✨Its Working ✨") { it.data.toString() }
                }
                is ApiResult.Error -> {
                    _isLoading.value = false
                    _message.value = it.errorMessage.toString()
                    Logger.e("✨Its Not Working ✨") { it.toString() }
                }
            }
        }
    }

    fun postNote(note: Note) = viewModelScope.launch {
        noteRepo.postNote(note).collect {
            when (it) {
                is ApiResult.Loading -> _isLoading.value = true
                is ApiResult.Success -> {
                    _isLoading.value = false
                    _message.value = it.data.toString()
                    Logger.d("✨Its Working ✨") { it.data.toString() }
                }
                is ApiResult.Error -> {
                    _isLoading.value = false
                    _message.value = it.errorMessage.toString()
                    Logger.e("✨Its Not Working ✨") { it.toString() }
                }
            }
        }
    }

    fun deleteNote(noteId: String) = viewModelScope.launch {
        noteRepo.deleteNote(noteId).collect {
            when (it) {
                is ApiResult.Loading -> _isLoading.value = true
                is ApiResult.Success -> {
                    _isLoading.value = false
                    _message.value = it.data.toString()
                }
                is ApiResult.Error -> {
                    _isLoading.value = false
                    _message.value = it.errorMessage.toString()
                }
            }
        }
    }
}