package id.neotica.notes.presentation.screen.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.neotica.notes.data.SessionManager
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val sessionManager: SessionManager
): ViewModel() {
    private val _profileEvents = Channel<ProfileEvents>()
    val profileEvents = _profileEvents.receiveAsFlow()

    private val _isLoggedIn = MutableStateFlow(false)
    val isLoggedIn = _isLoggedIn.asStateFlow()

    init {
        checkLoginState()
    }

    private fun checkLoginState() = viewModelScope.launch {
        sessionManager.getToken.collect {
            _isLoggedIn.value = it.token != null
        }
    }

    fun logout() = viewModelScope.launch {
        sessionManager.clearToken()
        _profileEvents.send(ProfileEvents.NavigateToHome)

    }
}

sealed interface ProfileEvents {
    object NavigateToHome: ProfileEvents
}