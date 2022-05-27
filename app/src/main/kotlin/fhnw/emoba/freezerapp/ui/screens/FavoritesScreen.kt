package fhnw.emoba.freezerapp.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import fhnw.emoba.freezerapp.model.FreezerModel
import fhnw.emoba.freezerapp.ui.LoadingScreen
import fhnw.emoba.freezerapp.ui.NoResultsMessage
import fhnw.emoba.freezerapp.ui.ScreenTitle
import fhnw.emoba.freezerapp.ui.TrackItem
import fhnw.emoba.freezerapp.ui.theme.FreezerTheme

@Composable
fun FavoritesScreen(model: FreezerModel) {
    FreezerTheme(model.isDarkTheme) {
        Scaffold(content = {
            Column() {
                ScreenTitle(title = "Your Favorites")
                FavoritesBody(model = model)
            }
        })
    }
}

@Composable
fun FavoritesBody(model: FreezerModel) {
    with(model) {
        when {
            isLoading -> {
                LoadingScreen(loadingMessage = "Loading favorites ...")
            }
            favoriteSongsList.isEmpty() -> {
                NoResultsMessage(message = "No favorites")
            }
            else -> {
                LazyColumn(
                    modifier = Modifier.padding(start = 15.dp, end = 15.dp),
                    contentPadding = PaddingValues(bottom = 55.dp)
                ) {
                    items(favoriteSongsList) { track -> TrackItem(track, model) }
                }
            }
        }
    }
}