package id.neotica.notes.presentation.screen.auth.login

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import id.neotica.notes.presentation.components.topbar.TopAppBarNow
import id.neotica.notes.presentation.navigation.Screen
import id.neotica.toast.ToastManager
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun LoginView(
    navController: NavController,
    viewModel: LoginViewModel = koinViewModel()
) {
    val toastManager by remember { mutableStateOf(ToastManager()) }

    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.loginEvents.collect {
            when (it) {
                is LoginEvent.NavigateToHome -> {
                    navController.navigate(Screen.MainScreen)
                }

                is LoginEvent.Message -> toastManager.showToast(it.message)
            }
        }
    }


    Scaffold (
        topBar = { TopAppBarNow(navController) }
    ) {
        LazyColumn(
            contentPadding = it
        ) {
            item {
                TextField(
                    value = username,
                    onValueChange = {
                        username = it
                    },
                    label = {
                        Text("Username")
                    }
                )
                TextField(
                    value = password,
                    onValueChange = {
                        password = it
                    },
                    label = {
                        Text("Password")
                    }
                )
                Button(
                    { viewModel.login(username, password) }
                ) { Text("Login") }

                Button(
                    { viewModel.check() }
                ) { Text("Check") }
            }
        }
    }
    if (isLoading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }
}