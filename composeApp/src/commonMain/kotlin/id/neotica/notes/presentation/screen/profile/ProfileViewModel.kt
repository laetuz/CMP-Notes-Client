package id.neotica.notes.presentation.screen.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.neotica.notes.data.SessionManager
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val sessionManager: SessionManager
): ViewModel() {
    fun logout() = viewModelScope.launch { sessionManager.clearToken() }
}