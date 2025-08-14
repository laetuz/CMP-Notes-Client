package id.neotica.notes

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import id.neotica.toast.setComposeWindowProvider
import id.neotica.notes.di.initializeKoin

fun main() = application {
    initializeKoin()

    Window(
        onCloseRequest = ::exitApplication,
        title = "Notes",
    ) {
        setComposeWindowProvider {
            window
        }
        App()
    }
}