package fhnw.emoba.freezerapp.model

import android.media.AudioAttributes
import android.media.MediaPlayer
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import fhnw.emoba.freezerapp.data.dataClasses.Album
import fhnw.emoba.freezerapp.data.FreezerService
import fhnw.emoba.freezerapp.data.dataClasses.RadioStation
import fhnw.emoba.freezerapp.data.dataClasses.Song
import fhnw.emoba.freezerapp.ui.screens.Screen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class FreezerModel(val freezerService: FreezerService) {
    private val modelScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    val appTitle = "Freezer App"
    var isDarkTheme by mutableStateOf(true)
    var currentScreen by mutableStateOf(Screen.MAINSCREEN)

    var albumSearchText by mutableStateOf("")
    var songSearchText by mutableStateOf("")
    var isLoading by mutableStateOf(false)

    var currentSong by mutableStateOf<Song?>(null)
    var currentPlayList by mutableStateOf<List<Song>>(emptyList())

    var searchedSongsList: List<Song> by mutableStateOf(emptyList())
    var searchAlbumsList: List<Album> by mutableStateOf(emptyList())
    var favoriteSongsList: List<Song> by mutableStateOf(emptyList())
    var allRadioStationList: List<RadioStation> by mutableStateOf(emptyList())
    var radioStationTracksList: List<Song> by mutableStateOf(emptyList())
    var albumsTracksList: List<Song> by mutableStateOf(emptyList())

    // Player
    var playerIsReady by mutableStateOf(true)
    private var currentlyPlaying = ""

    private val player = MediaPlayer().apply {
        setOnCompletionListener {
            playerIsReady = true
            playNextSong()
            playerIsReady = false
        }
        setAudioAttributes(
            AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .build()
        )
        setOnPreparedListener {
            start()
        }
    }

    fun startPlayer() {
        playerIsReady = false
        try {
            if (currentSong == null) return
            else {
                if (currentlyPlaying == currentSong!!.preview && !player.isPlaying) {
                    player.start()
                } else {
                    currentlyPlaying = currentSong!!.preview
                    player.reset()
                    player.setDataSource(currentlyPlaying)
                    player.prepareAsync()
                }
            }
        } catch (e: Exception) {
            playerIsReady = true
        }
    }

    fun pausePlayer() {
        player.pause()
        playerIsReady = true
    }

    fun fromStart() {
        player.seekTo(0)
        player.start()
        playerIsReady = false
    }

    fun playNextSong() {
        pausePlayer()
        currentSong = currentPlayList[calculateNextIndex(currentPlayList)]
        startPlayer()
    }

    fun playPreviousSong() {
        pausePlayer()
        currentSong = currentPlayList[calculatePreviousIndex(currentPlayList)]
        startPlayer()
    }

    // inspired by Alison Fersch
    fun calculateNextIndex(currentPlaylist: List<Song>): Int {
        if (currentSong == null) return -1
        val currentIndex = currentPlaylist.indexOf(currentSong)
        val nextIndex: Int =
            if (currentPlaylist.size == currentIndex + 1) { // if last index, skip to start
                0
            } else {
                currentIndex + 1
            }
        return nextIndex
    }

    fun calculatePreviousIndex(currentPlaylist: List<Song>): Int {
        if (currentSong == null) return -1
        val currentIndex = currentPlaylist.indexOf(currentSong)
        val previousIndex: Int = if (-1 == currentIndex - 1) { // if first index, skip to end
            currentPlaylist.size - 1
        } else {
            currentIndex - 1
        }
        return previousIndex
    }

    // load resources
    fun loadSearchedSongAsync() {
        isLoading = true
        modelScope.launch {
            searchedSongsList = freezerService.requestSearchedSong(searchText = songSearchText)
            loadSongImagesAsync()
            isLoading = false
        }

    }

    fun loadSearchedAlbumAsync() {
        isLoading = true;
        modelScope.launch {
            searchAlbumsList = freezerService.requestSearchedAlbum(searchText = albumSearchText)
            loadAlbumImagesAsync()
            isLoading = false
        }
    }

    fun loadAllRadioStationsAsync() {
        if (allRadioStationList.isEmpty()) {
            isLoading = true
            modelScope.launch {
                allRadioStationList = freezerService.requestAllRadioStations()
                loadRadioStationImagesAsync()
                isLoading = false
            }
        } else {
            return
        }
    }

    fun loadTracksRadioStationsAsync(radioStation: RadioStation) {
        isLoading = true
        modelScope.launch {
            radioStationTracksList =
                freezerService.requestTracks(tracksURL = radioStation.trackList)
            loadTracksRadioStationImagesAsync()
            isLoading = false
        }
    }

    fun loadTracksAlbumAsync(album: Album) {
        isLoading = true
        modelScope.launch {
            albumsTracksList = freezerService.requestTracks(tracksURL = album.trackList)
            loadTracksAlbumImagesAsync(album)
            isLoading = false
        }
    }

    // load all images
    private fun loadRadioStationImagesAsync() {
        for (radioStation in allRadioStationList) {
            radioStation.cover = freezerService.requestImage(radioStation.coverMedium);
        }
    }

    private fun loadTracksRadioStationImagesAsync() {
        for (track in radioStationTracksList) {
            track.cover = freezerService.requestImage(track.coverMedium)
        }
    }

    private fun loadAlbumImagesAsync() {
        for (album in searchAlbumsList) {
            album.cover = freezerService.requestImage(album.coverMedium)
        }
    }

    private fun loadTracksAlbumImagesAsync(album: Album) {
        for (track in albumsTracksList) {
            if (track.coverMedium == "") {
                track.cover = freezerService.requestImage(album.coverMedium)
            } else {
                track.cover = freezerService.requestImage(track.coverMedium)
            }
        }
    }

    private fun loadSongImagesAsync() {
        for (track in searchedSongsList) {
            track.cover = freezerService.requestImage(track.coverMedium)
        }
    }

    fun addFavoriteSong(track: Song) {
        favoriteSongsList = favoriteSongsList.plus(track)
    }

    fun removeFavoriteSong(track: Song) {
        favoriteSongsList = favoriteSongsList.minus(track)
    }
}