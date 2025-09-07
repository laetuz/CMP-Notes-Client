package id.neotica.notes.presentation.screen

import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import id.neotica.toast.ToastManager
import id.neotica.notes.domain.model.Note
import id.neotica.notes.presentation.components.DotsMenuItem
import id.neotica.notes.presentation.components.MenuDropDown
import id.neotica.notes.presentation.navigation.Screen
import id.neotica.notes.presentation.theme.NeoColor
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteView(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: NoteViewModel = koinViewModel()
) {
    val notes by viewModel.notes.collectAsState()
    val isRefresh by viewModel.isLoading.collectAsState()
    val searchText = remember { mutableStateOf("") }
    val nestedBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

    val toastManager by remember { mutableStateOf(ToastManager()) }

    Scaffold(
        modifier.nestedScroll(nestedBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        modifier= Modifier.fillMaxWidth().padding(end = 16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        SearchField(searchText)
                        MenuDropDown(
                            listOf(
                                DotsMenuItem("Profile") { navController.navigate(Screen.ProfileScreen) },
                            )
                        )
                    }
                },
                scrollBehavior = nestedBehavior,
                colors = TopAppBarDefaults.topAppBarColors().copy(
                    containerColor = Color.Transparent,
                    scrolledContainerColor = NeoColor.transparent
                )
            )
        },
        floatingActionButton = {
            Text(
                text = "Add Note",
                modifier = Modifier.combinedClickable(
                    onClick = {
                        navController.navigate(Screen.NoteDetailScreen())
                    }
                )
            )
        }
    ) {
        val layoutDirection = LocalLayoutDirection.current

        PullToRefreshBox(
            onRefresh = {
                toastManager.showToast("Refreshing...")
                viewModel.getNotes()
            },
            isRefreshing = isRefresh
        ) {
            LazyVerticalGrid(
                modifier = Modifier.padding(horizontal = 16.dp),
                columns = GridCells.Adaptive(180.dp),
                contentPadding = PaddingValues(
                    start = it.calculateStartPadding(layoutDirection),
                    end = it.calculateEndPadding(layoutDirection),
                    top = it.calculateTopPadding(),
                    bottom = WindowInsets.safeDrawing.asPaddingValues().calculateBottomPadding()
                ),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                notes?.let { noteList ->
                    items(items = noteList) { note ->
                        NoteCard(note, onLongClick = {
                            toastManager.showToast("Note clicked: ${note.id}")
                        }) {
                            navController.navigate(Screen.NoteDetailScreen(note.id.toString()))
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SearchField(searchText: MutableState<String>) {
    BasicTextField(
        value = searchText.value,
        onValueChange = {
            searchText.value = it
        },
        decorationBox = {
            if (searchText.value.isEmpty()) {
                Text("Search")
            }
            it()
        },
        textStyle = TextStyle.Default.copy(
            color = MaterialTheme.colorScheme.onSurface
        )
    )
}

@Composable
fun NoteCard(note: Note, onLongClick: () -> Unit ? = {}, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .combinedClickable(
                onLongClick = { onLongClick.invoke() },
                onClick = {
                    onClick()
                }
            )
    ) {
        Column (
            modifier = Modifier.padding(16.dp)
        ) {
            Text(note.title.toString())
            Text(
                text = note.content.toString(),
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}