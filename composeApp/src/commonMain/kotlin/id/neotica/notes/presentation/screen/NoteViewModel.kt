package id.neotica.notes.presentation.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.touchlab.kermit.Logger
import id.neotica.notes.domain.NoteRepository
import id.neotica.notes.domain.model.ApiResult
import id.neotica.notes.domain.model.Note
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class NoteViewModel(
    private val noteRepo: NoteRepository
): ViewModel() {

    private val _notes: MutableStateFlow<List<Note>?> = MutableStateFlow(emptyList())
    val notes = _notes.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _message = MutableStateFlow("")
    val message = _message.asStateFlow()

    init {
        getNotes()
    }

    fun getNotes() = viewModelScope.launch {
        _isLoading.value = true
        noteRepo.getNotes().collect {
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
            Logger.d("✨ isloading") { _isLoading.value.toString() }
            Logger.d("✨Its Working ✨") { it.data.toString() }
        }
    }
}