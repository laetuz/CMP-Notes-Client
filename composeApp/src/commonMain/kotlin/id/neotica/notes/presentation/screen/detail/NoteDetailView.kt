package id.neotica.notes.presentation.screen.detail

import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteDetailView(
    navController: NavController,
    viewModel: NoteDetailViewModel = koinViewModel()
) {
    val note by viewModel.notes.collectAsState()
    Scaffold(
        topBar = {
            TopAppBar({
                Column {
                    Text(
                        text = "Back",
                        modifier = Modifier.combinedClickable(
                            onClick = { navController.navigateUp() }
                        )
                    )
                }
            })
        }
    ) {
        LazyColumn(
            modifier = Modifier.padding(horizontal = 16.dp),
            contentPadding = it
        ) {
            item {
                note?.let {
                    Text(it.title.toString())
                    Text(it.content.toString())
                }
            }
        }
    }
}