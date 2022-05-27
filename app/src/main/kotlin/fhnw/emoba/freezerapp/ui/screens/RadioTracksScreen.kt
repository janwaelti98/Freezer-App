package fhnw.emoba.freezerapp.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import fhnw.emoba.freezerapp.model.FreezerModel
import fhnw.emoba.freezerapp.ui.BackBar
import fhnw.emoba.freezerapp.ui.LoadingScreen
import fhnw.emoba.freezerapp.ui.ScreenTitle
import fhnw.emoba.freezerapp.ui.TrackItem
import fhnw.emoba.freezerapp.ui.theme.FreezerTheme

@Composable
fun RadioTracksScreen(model: FreezerModel) {
    FreezerTheme(model.isDarkTheme) {
        Scaffold(content = {
            Column() {
                BackBar(model)
                ScreenTitle(title = "Tracks")
                RadioTracksBody(model = model)
            }
        })
    }
}

@Composable
fun RadioTracksBody(model: FreezerModel) {
    with(model) {
        when {
            isLoading -> {
                LoadingScreen(loadingMessage = "Loading tracks ...")
            }
            else -> {
                LazyColumn(modifier = Modifier.padding(start = 15.dp, end = 15.dp)) {
                    items(radioStationTracksList) { track -> TrackItem(track, model) }
                }
            }
        }
    }
}