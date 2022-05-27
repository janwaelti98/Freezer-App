package fhnw.emoba.freezerapp.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Album
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.Radio
import androidx.compose.ui.graphics.vector.ImageVector

// inspired by https://www.youtube.com/watch?v=gg-KBGH9T8s

sealed class BottomBarScreen(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    object Radio : BottomBarScreen(
        route = "Radio",
        title = "Radio",
        icon = Icons.Default.Radio
    )

    object Albums : BottomBarScreen(
        route = "Albums",
        title = "Albums",
        icon = Icons.Default.Album
    )

    object Songs : BottomBarScreen(
        route = "Songs",
        title = "Songs",
        icon = Icons.Default.MusicNote
    )

    object Favorites : BottomBarScreen(
        route = "Favorites",
        title = "Favorites",
        icon = Icons.Default.FavoriteBorder
    )
}
