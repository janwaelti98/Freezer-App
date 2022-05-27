package fhnw.emoba.freezerapp.ui

import androidx.compose.animation.Crossfade
import androidx.compose.runtime.Composable
import fhnw.emoba.freezerapp.model.FreezerModel
import fhnw.emoba.freezerapp.ui.screens.*
import fhnw.emoba.freezerapp.ui.theme.FreezerTheme

@Composable
fun AppUI(model: FreezerModel) {
    with(model) {
        FreezerTheme(isDarkTheme) {
            Crossfade(targetState = currentScreen) { screen ->
                when (screen) {
                    Screen.RADIOTRACKSSCREEN -> {
                        RadioTracksScreen(model)
                    }
                    Screen.ALBUMTRACKSSCREEN -> {
                        AlbumTracksScreen(model)
                    }
                    // Contains all screens in bottom navigation
                    Screen.MAINSCREEN -> {
                        MainScreen(model)
                    }
                }
            }
        }
    }
}
