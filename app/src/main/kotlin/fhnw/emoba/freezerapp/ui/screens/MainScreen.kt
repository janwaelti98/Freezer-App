package fhnw.emoba.freezerapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import fhnw.emoba.freezerapp.model.FreezerModel
import fhnw.emoba.freezerapp.ui.Bar
import fhnw.emoba.freezerapp.ui.BottomBar
import fhnw.emoba.freezerapp.ui.BottomNavGraph
import fhnw.emoba.freezerapp.ui.SongPlayer
import fhnw.emoba.freezerapp.ui.theme.FreezerTheme

@OptIn(ExperimentalComposeUiApi::class)
@Composable
// Contains TopBar, BottomNavBar and all screens in Navigation
fun MainScreen(model: FreezerModel) {
    val navController = rememberNavController()

    FreezerTheme(model.isDarkTheme) {
        Scaffold(topBar = {
            Bar(model)
        }, bottomBar = {
            BottomBar(navController = navController)
        }, content = {
            BottomNavGraph(
                navController = navController,
                model = model
            )

            Box(Modifier.fillMaxSize()) {
                Column(
                    verticalArrangement = Arrangement.Bottom,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(bottom = 75.dp)
                ) {
                    model.currentSong?.let { SongPlayer(track = it, model = model) }
                }
            }
        })
    }
}