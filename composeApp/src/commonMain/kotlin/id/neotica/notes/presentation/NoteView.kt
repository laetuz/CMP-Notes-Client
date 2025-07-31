package id.neotica.notes.presentation

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun NoteView(
    modifier: Modifier = Modifier,
    viewModel: NoteViewModel = koinViewModel()
) {
    val notes = viewModel.notes.collectAsState()
    Scaffold {
        LazyColumn(
            contentPadding = it
        ) {
            item {
                notes.value.forEach { note ->
                    Text(note.title.toString())
                }
            }
        }
    }
}