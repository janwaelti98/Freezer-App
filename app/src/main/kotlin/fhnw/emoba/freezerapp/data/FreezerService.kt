package fhnw.emoba.freezerapp.data

import androidx.compose.ui.graphics.ImageBitmap
import fhnw.emoba.freezerapp.data.dataClasses.Album
import fhnw.emoba.freezerapp.data.dataClasses.RadioStation
import fhnw.emoba.freezerapp.data.dataClasses.Song

interface FreezerService {
    // Songs
    fun requestSearchedSong(searchText: String): List<Song>

    // Albums
    fun requestSearchedAlbum(searchText: String): List<Album>

    // RadioStations
    fun requestAllRadioStations(): List<RadioStation>

    // Tracks
    fun requestTracks(tracksURL: String): List<Song>

    // Load images
    fun requestImage(imageURL: String): ImageBitmap
}