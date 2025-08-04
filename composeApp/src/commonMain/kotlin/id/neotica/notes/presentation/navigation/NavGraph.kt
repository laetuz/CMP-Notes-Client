package id.neotica.notes.presentation.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import id.neotica.notes.presentation.NoteView

@Composable
fun NavGraph(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        enterTransition = { fadeIn(tween(100)) },
        popEnterTransition = { EnterTransition.None },
        exitTransition = { fadeOut(tween(100)) },
        popExitTransition = { ExitTransition.None },
        startDestination = Screen.MainScreen
    ) {
        composable<Screen.MainScreen>{ NoteView() }
    }
}