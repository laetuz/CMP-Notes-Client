package id.neotica.notes.presentation.screen.auth.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.touchlab.kermit.Logger
import id.neotica.notes.data.AuthRepositoryImpl
import id.neotica.notes.data.SessionManager
import id.neotica.notes.domain.ApiResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoginViewModel(
    private val authRepo: AuthRepositoryImpl,
    private val sessionManager: SessionManager,
): ViewModel() {
    private val _token = MutableStateFlow<String?>(null)
    val token = _token.asStateFlow()

    fun login(username: String, password: String) = viewModelScope.launch {
        authRepo.login(username, password).collect {
            when (it) {
                is ApiResult.Error -> {
                    Logger.e("💀 error: ${it.errorMessage}")
                }
                is ApiResult.Loading -> {
                    Logger.e("⏳ loading")
                }
                is ApiResult.Success -> {
                    _token.value = it.data?.token
                    Logger.e("✨ success: ${it.data}")
                    sessionManager.saveToken(it.data!!)
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