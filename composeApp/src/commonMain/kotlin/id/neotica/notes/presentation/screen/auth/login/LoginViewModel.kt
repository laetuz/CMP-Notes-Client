package id.neotica.notes.presentation.screen.auth.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.touchlab.kermit.Logger
import id.neotica.notes.data.AuthRepositoryImpl
import id.neotica.notes.data.SessionManager
import id.neotica.notes.domain.model.ApiResult
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class LoginViewModel(
    private val authRepo: AuthRepositoryImpl,
    private val sessionManager: SessionManager,
): ViewModel() {

    private val _loginEvents = Channel<LoginEvent>()
    val loginEvents = _loginEvents.receiveAsFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    init {
        viewModelScope.launch {
            if (sessionManager.getToken.first().token != null)
                _loginEvents.trySend(LoginEvent.NavigateToHome)
        }
    }

    fun login(username: String, password: String) = viewModelScope.launch {
        authRepo.login(username, password).collect {
            when (it) {
                is ApiResult.Error -> {
                    Logger.e("💀 error: ${it.errorMessage}")
                    _isLoading.value = false
                    _loginEvents.send(LoginEvent.Message(it.errorMessage.toString()))
                }
                is ApiResult.Loading -> {
                    Logger.e("⏳ loading")
                    _isLoading.value = true
                }
                is ApiResult.Success -> {
                    _isLoading.value = false
                    sessionManager.saveToken(it.data!!)
                    _loginEvents.send(LoginEvent.NavigateToHome)
                }
            }

        }
    }

    fun check() = viewModelScope.launch {
        sessionManager.getToken.collect {
            Logger.e("✨ token: $it")
        }
    }
}

sealed interface LoginEvent {
    object NavigateToHome: LoginEvent
    data class Message(val message: String): LoginEvent
}