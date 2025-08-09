package id.neotica.notes.presentation.screen.detail

import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import id.neotica.notes.domain.Note
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteDetailView(
    navController: NavController,
    viewModel: NoteDetailViewModel = koinViewModel()
) {
    val isLoading by viewModel.isLoading.collectAsState()
    val note by viewModel.notes.collectAsState()
    var noteState by remember { mutableStateOf(Note(
        title = note?.title?: "",
        content = note?.content?: ""
    )) }

    Scaffold(
        topBar = {
            TopAppBar({
                Row (
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Back",
                        modifier = Modifier.combinedClickable(
                            onClick = { navController.navigateUp() }
                        )
                    )
                    Text(
                        text = "Edit",
                        modifier = Modifier.combinedClickable(
                            onClick = {
                                viewModel.updateNote(noteState)
                            }
                        )
                    )
                }
            })
        },
    ) {
        LazyColumn(
            modifier = Modifier.padding(horizontal = 16.dp),
            contentPadding = it
        ) {
            item {
                note?.let { theNote ->
                    var title by remember { mutableStateOf(theNote.title) }
                    var content by remember { mutableStateOf(theNote.content) }
                    val updatedNote = Note(
                        id = theNote.id,
                        title = title,
                        content = content,
                        createdAt = theNote.createdAt,
                        updatedAt = theNote.updatedAt
                    )
                    noteState = updatedNote
                    TextField(
                        value = title.toString(),
                        onValueChange = {
                            title = it
                        },
                        label = {
                            Text("Title")
                        }
                    )
                    TextField(
                        value = content.toString(),
                        onValueChange = {
                            content = it
                        },
                        label = {
                            Text("Content")
                        }
                    )
                    Text(noteState.toString())
                }
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