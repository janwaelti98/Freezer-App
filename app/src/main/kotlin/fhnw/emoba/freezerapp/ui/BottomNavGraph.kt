package fhnw.emoba.freezerapp.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import fhnw.emoba.freezerapp.model.FreezerModel
import fhnw.emoba.freezerapp.ui.screens.AlbumsScreen
import fhnw.emoba.freezerapp.ui.screens.FavoritesScreen
import fhnw.emoba.freezerapp.ui.screens.RadioScreen
import fhnw.emoba.freezerapp.ui.screens.SongsScreen

// inspired by https://www.youtube.com/watch?v=gg-KBGH9T8s

@Composable
fun BottomNavGraph(navController: NavHostController, model: FreezerModel) {
    NavHost(
        navController = navController,
        startDestination = BottomBarScreen.Radio.route
    ) {
        composable(route = BottomBarScreen.Radio.route) {
            RadioScreen(model = model)
        }
        composable(route = BottomBarScreen.Albums.route) {
            AlbumsScreen(model = model)
        }
        composable(route = BottomBarScreen.Songs.route) {
            SongsScreen(model = model)
        }
        composable(route = BottomBarScreen.Favorites.route) {
            FavoritesScreen(model = model)
        }
    }
}