package fhnw.emoba.freezerapp.ui.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import fhnw.emoba.freezerapp.data.dataClasses.RadioStation
import fhnw.emoba.freezerapp.model.FreezerModel
import fhnw.emoba.freezerapp.ui.CardHeadline
import fhnw.emoba.freezerapp.ui.LoadingScreen
import fhnw.emoba.freezerapp.ui.ScreenTitle
import fhnw.emoba.freezerapp.ui.theme.FreezerTheme

@Composable
fun RadioScreen(model: FreezerModel) {
    FreezerTheme(model.isDarkTheme) {
        Scaffold(content = {
            Column() {
                ScreenTitle(title = "Discover Radios")
                RadioBody(model = model)
            }
        })
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun RadioBody(model: FreezerModel) {
    with(model) {
        when {
            isLoading -> {
                LoadingScreen(loadingMessage = "Loading radios ...")
            }
            else -> {
                LazyVerticalGrid(
                    cells = GridCells.Adaptive(minSize = 170.dp),
                    contentPadding = PaddingValues(bottom = 70.dp)
                ) {
                    items(allRadioStationList) { radioStation ->
                        RadioCard(radioStation, model)
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun RadioCard(radioStation: RadioStation, model: FreezerModel) {
    Card(
        modifier = Modifier
            .padding(all = 15.dp)
            .size(width = 170.dp, height = 170.dp),
        shape = RoundedCornerShape(20.dp),
        elevation = 5.dp,
        onClick = {
            model.currentScreen = Screen.RADIOTRACKSSCREEN
            model.loadTracksRadioStationsAsync(radioStation = radioStation)
        }
    ) {
        Box() {
            Image(
                bitmap = radioStation.cover,
                "Radio cover",
                contentScale = ContentScale.Crop, modifier = Modifier.fillMaxWidth()
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                MaterialTheme.colors.background
                            ),
                            startY = 100f
                        )
                    )
            )
            CardHeadline(text = radioStation.title)
        }
    }
}