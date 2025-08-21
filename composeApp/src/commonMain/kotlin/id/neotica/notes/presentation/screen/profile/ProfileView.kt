package id.neotica.notes.presentation.screen.profile

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import id.neotica.notes.presentation.components.topbar.TopAppBarNow
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ProfileView(
    navController: NavController,
    viewModel: ProfileViewModel = koinViewModel()
) {
    Scaffold(
        topBar = { TopAppBarNow(navController) }
    ) {
        LazyColumn(
            contentPadding = it
        ) {
            item {
                Button({viewModel.logout()}) { Text("Logout") }
            }
        }
    }
}