package fhnw.emoba.freezerapp.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import fhnw.emoba.freezerapp.model.FreezerModel
import fhnw.emoba.freezerapp.ui.LoadingScreen
import fhnw.emoba.freezerapp.ui.NoResultsMessage
import fhnw.emoba.freezerapp.ui.ScreenTitle
import fhnw.emoba.freezerapp.ui.TrackItem
import fhnw.emoba.freezerapp.ui.theme.FreezerTheme

@Composable
fun SongsScreen(model: FreezerModel) {
    FreezerTheme(model.isDarkTheme) {
        Scaffold(content = {
            Column() {
                ScreenTitle(title = "Discover Songs")
                SearchField(model = model)
                SongsBody(model = model)
            }
        })
    }
}

@Composable
fun SongsBody(model: FreezerModel) {
    with(model) {
        when {
            isLoading -> {
                LoadingScreen(loadingMessage = "Loading songs ...")
            }
            searchedSongsList.isEmpty() -> {
                NoResultsMessage("No songs found")
            }
            else -> {
                LazyColumn(
                    modifier = Modifier.padding(start = 15.dp, end = 15.dp),
                    contentPadding = PaddingValues(bottom = 55.dp)
                ) {
                    items(searchedSongsList) { track -> TrackItem(track, model) }
                }
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun SearchField(model: FreezerModel) {
    with(model) {
        val keyboard = LocalSoftwareKeyboardController.current
        OutlinedTextField(
            value = songSearchText,
            onValueChange = { songSearchText = it },
            placeholder = { Text("Search Songs") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 15.dp, end = 15.dp, bottom = 15.dp),
            trailingIcon = {
                IconButton(onClick = {
                    songSearchText = ""
                    model.searchedSongsList = emptyList()
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
                loadSearchedSongAsync()
            }),
            shape = RoundedCornerShape(15.dp)
        )
    }
}