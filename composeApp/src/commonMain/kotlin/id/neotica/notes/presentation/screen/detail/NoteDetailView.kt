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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import id.neotica.notes.domain.model.Note
import id.neotica.notes.presentation.components.DotsMenuItem
import id.neotica.notes.presentation.components.MenuDropDown
import id.neotica.toast.ToastManager
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteDetailView(
    navController: NavController,
    viewModel: NoteDetailViewModel = koinViewModel()
) {
    val isLoading by viewModel.isLoading.collectAsState()
    val note by viewModel.notes.collectAsStateWithLifecycle()
    val message by viewModel.message.collectAsStateWithLifecycle()

    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }

    val toastManager by remember { mutableStateOf(ToastManager()) }

    LaunchedEffect(note) {
        note?.let {
            title = it.title.toString()
            content = it.content.toString()
        }
    }

    LaunchedEffect(message) {
        if (message.isNotEmpty()) {
            toastManager.showToast(message)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar({
                Row (
                    modifier = Modifier.fillMaxWidth().padding(end = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Back",
                        modifier = Modifier.combinedClickable(
                            onClick = { navController.navigateUp() }
                        )
                    )
                    MenuDropDown(
                        listOf(
                            DotsMenuItem("Save") {
                                val savedNote = note?.copy(
                                    title = title,
                                    content = content
                                )?: Note(
                                    title = title,
                                    content = content
                                )
                                if (note != null) {
                                    viewModel.updateNote(savedNote)
                                } else {
                                    viewModel.postNote(savedNote)
                                }
                            },
                            DotsMenuItem("Delete") {
                                viewModel.deleteNote(note?.id.toString())
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
                if (!isLoading) {
                    Text("id: ${note?.id}")
                    Text("userId: ${note?.userId}")
                    TextField(
                        value = title,
                        onValueChange = {
                            title = it
                        },
                        label = {
                            Text("Title")
                        }
                    )
                    TextField(
                        value = content,
                        onValueChange = {
                            content = it
                        },
                        label = {
                            Text("Content")
                        }
                    )
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