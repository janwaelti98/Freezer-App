package fhnw.emoba.freezerapp.data.impl

import android.graphics.BitmapFactory
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import fhnw.emoba.R
import fhnw.emoba.freezerapp.data.FreezerService
import fhnw.emoba.freezerapp.data.dataClasses.Album
import fhnw.emoba.freezerapp.data.dataClasses.RadioStation
import fhnw.emoba.freezerapp.data.dataClasses.Song
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.Exception
import java.net.URL
import javax.net.ssl.HttpsURLConnection

// Created together with Katrin Stutz and Alison Fersch

class RemoteFreezerService : FreezerService {
    private val baseURL = "https://api.deezer.com"

    // Songs
    override fun requestSearchedSong(searchText: String): List<Song> {
        return try {
            val url = URL("$baseURL/search/track?q=\"$searchText\"")
            val searchedSongsAsJSONString = createBufferedReader(url)

            val searchedSongList = mutableListOf<Song>()
            val searchSongsAsJSONObject = JSONObject(searchedSongsAsJSONString)
            val data = searchSongsAsJSONObject.getJSONArray("data")

            for (i in 0 until data.length()) {
                searchedSongList.add(Song(data.getJSONObject(i)))
            }

            searchedSongList

        } catch (e: Exception) {
            error("Unable to find any song by keyword: $searchText  ${e.stackTrace}")
        }
    }

    // Albums
    override fun requestSearchedAlbum(searchText: String): List<Album> {
        return try {
            val url = URL("$baseURL/search/album?q=\"$searchText\"")
            val searchedAlbumsAsJSONString = createBufferedReader(url)

            val searchedAlbumList = mutableListOf<Album>()
            val searchAlbumsAsJSONObject = JSONObject(searchedAlbumsAsJSONString)
            val data = searchAlbumsAsJSONObject.getJSONArray("data")

            for (i in 0 until data.length()) {
                searchedAlbumList.add(Album(data.getJSONObject(i)))
            }

            searchedAlbumList

        } catch (e: Exception) {
            error("Unable to find any album by keyword: $searchText")
        }
    }

    //RadioStations
    override fun requestAllRadioStations(): List<RadioStation> {
        return try {
            val url = URL("$baseURL/radio")
            val allRadioStationsAsJSONString = createBufferedReader(url)

            val radioStationList = mutableListOf<RadioStation>()
            val allRadioStationsAsJSONObject = JSONObject(allRadioStationsAsJSONString)
            val data = allRadioStationsAsJSONObject.getJSONArray("data")

            for (i in 0 until data.length()) {
                radioStationList.add(RadioStation(data.getString(i)))
            }

            radioStationList

        } catch (e: Exception) {
            error("Unable to request all radio stations")
        }
    }


    //Images
    override fun requestImage(imageURL: String): ImageBitmap {
        return try {
            val url = URL(imageURL)
            val conn = url.openConnection() as HttpsURLConnection
            conn.connect()

            val inputStream = conn.inputStream
            val allBytes = inputStream.readBytes()
            inputStream.close()

            val bitmap = BitmapFactory.decodeByteArray(allBytes, 0, allBytes.size)

            bitmap.asImageBitmap()

        } catch (e: Exception) {
            return ImageBitmap(R.drawable.dummyimage, height = 170);
        }
    }

    //Tracks
    override fun requestTracks(tracksURL: String): List<Song> {
        return try {
            val url = URL(tracksURL)
            val tracksAsJSONString = createBufferedReader(url)

            val trackList = mutableListOf<Song>()
            val tracksAsJSONObject = JSONObject(tracksAsJSONString)
            val data = tracksAsJSONObject.getJSONArray("data")


            // if json object artist doesn't have a cover_medium attribute, add one to be able to display track-cover in album
            for (i in 0 until data.length()) {
                if (!(data.getJSONObject(i).getJSONObject("artist").has("picture_medium"))) {
                    data.getJSONObject(i).getJSONObject("artist").put("picture_medium", "")
                }
            }

            for (i in 0 until data.length()) {
                trackList.add(Song(data.getJSONObject(i)))
            }

            trackList

        } catch (e: Exception) {
            error("Unable to request tracks from Radio / Album")
        }
    }

    // helper method to create a bufferedReader
    private fun createBufferedReader(baseURL: URL): String {
        val conn = baseURL.openConnection() as HttpsURLConnection
        conn.connect()

        val bufferedReader = BufferedReader(InputStreamReader(conn.inputStream))
        val objectAsJSONString = bufferedReader.readText()
        bufferedReader.close()

        return objectAsJSONString
    }
}