package id.neotica.notes.presentation.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class Screen {
    @Serializable
    data object MainScreen : Screen()
    @Serializable
    data class NoteDetailScreen(val id: String = ""): Screen()
    @Serializable
    data object LoginScreen : Screen()
    @Serializable
    data object RegisterScreen : Screen()
}