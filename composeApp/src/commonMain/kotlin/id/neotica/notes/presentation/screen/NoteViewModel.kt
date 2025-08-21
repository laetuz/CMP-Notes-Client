package id.neotica.notes.presentation.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.touchlab.kermit.Logger
import id.neotica.notes.data.SessionManager
import id.neotica.notes.domain.NoteRepository
import id.neotica.notes.domain.model.ApiResult
import id.neotica.notes.domain.model.Note
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch

class NoteViewModel(
    private val noteRepo: NoteRepository,
    private val sessionManager: SessionManager
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

    @OptIn(ExperimentalCoroutinesApi::class)
    fun getNotes() = viewModelScope.launch {

        sessionManager.getToken
            .flatMapLatest { token ->
                if (token.token.isNullOrEmpty()) noteRepo.getPublicNotes() else noteRepo.getNotes()
            }
            .collect {
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