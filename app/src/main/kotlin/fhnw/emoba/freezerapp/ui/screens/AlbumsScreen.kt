package fhnw.emoba.freezerapp.ui.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import fhnw.emoba.freezerapp.data.dataClasses.Album
import fhnw.emoba.freezerapp.model.FreezerModel
import fhnw.emoba.freezerapp.ui.CardHeadline
import fhnw.emoba.freezerapp.ui.LoadingScreen
import fhnw.emoba.freezerapp.ui.NoResultsMessage
import fhnw.emoba.freezerapp.ui.ScreenTitle
import fhnw.emoba.freezerapp.ui.theme.FreezerTheme

@Composable
fun AlbumsScreen(model: FreezerModel) {
    FreezerTheme(model.isDarkTheme) {
        Scaffold(content = {
            Column() {
                ScreenTitle(title = "Discover Albums")
                SearchField(model = model)
                AlbumsBody(model = model)
            }
        })
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AlbumsBody(model: FreezerModel) {
    with(model) {
        when {
            isLoading -> {
                LoadingScreen(loadingMessage = "Loading albums ...")
            }
            searchAlbumsList.isEmpty() -> {
                NoResultsMessage("No albums found")
            }
            else -> {
                LazyVerticalGrid(
                    cells = GridCells.Adaptive(minSize = 170.dp),
                    contentPadding = PaddingValues(bottom = 70.dp)
                ) {
                    items(searchAlbumsList) { album ->
                        AlbumCard(album, model)
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AlbumCard(album: Album, model: FreezerModel) {
    Card(
        modifier = Modifier
            .padding(all = 15.dp)
            .size(width = 170.dp, height = 170.dp),
        shape = RoundedCornerShape(20.dp),
        elevation = 5.dp,
        onClick = {
            model.currentScreen = Screen.ALBUMTRACKSSCREEN
            model.loadTracksAlbumAsync(album = album)
        }
    ) {
        Box() {
            Image(
                bitmap = album.cover,
                "Album Cover",
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
            CardHeadline(text = album.title)
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun SearchField(model: FreezerModel) {
    with(model) {
        val keyboard = LocalSoftwareKeyboardController.current
        OutlinedTextField(
            value = albumSearchText,
            onValueChange = { albumSearchText = it },
            placeholder = { Text("Search Albums") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 15.dp, end = 15.dp, bottom = 15.dp),
            trailingIcon = {
                IconButton(onClick = {
                    albumSearchText = ""
                    model.searchAlbumsList = emptyList()
                }) {
                    Icon(Icons.Filled.Clear, "")
                }
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done,
                autoCorrect = false,
                keyboardType = KeyboardType.Ascii
            ),
            keyboardActions = KeyboardActions(onDone = {
                keyboard?.hide()
                loadSearchedAlbumAsync()
            }),
            shape = RoundedCornerShape(15.dp)
        )
    }
}