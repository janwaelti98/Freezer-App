package fhnw.emoba.freezerapp.model

import fhnw.emoba.freezerapp.data.dataClasses.Song
import fhnw.emoba.freezerapp.data.impl.RemoteFreezerService
import junit.framework.Assert.assertEquals
import org.junit.Test

internal class FreezerModelTest {
    //given
    private val service = RemoteFreezerService()
    private val model = FreezerModel(service)

    private val song1 = Song(
        "{\"id\": 916424," +
                "\"title\": \"Without Me\"," +
                "\"duration\": \"290\"," +
                "\"preview\": \"https://cdns-preview-c.dzcdn.net/stream/c-cca63b2c92773d54e61c5b4d17695bd2-8.mp3\"," +
                "\"artist\": {" +
                "    \"name\": \"Eminem\"," +
                "    \"picture_medium\": \"https://e-cdns-images.dzcdn.net/images/artist/19cc38f9d69b352f718782e7a22f9c32/250x250-000000-80-0-0.jpg\"" +
                "}" +
                "}"
    )
    private val song2 = Song(
        "{\"id\": 916424," +
                "\"title\": \"Without Me\"," +
                "\"duration\": \"290\"," +
                "\"preview\": \"https://cdns-preview-c.dzcdn.net/stream/c-cca63b2c92773d54e61c5b4d17695bd2-8.mp3\"," +
                "\"artist\": {" +
                "    \"name\": \"Eminem\"," +
                "    \"picture_medium\": \"https://e-cdns-images.dzcdn.net/images/artist/19cc38f9d69b352f718782e7a22f9c32/250x250-000000-80-0-0.jpg\"" +
                "}" +
                "}"
    )
    private val song3 = Song(
        "{\"id\": 916424," +
                "\"title\": \"Without Me\"," +
                "\"duration\": \"290\"," +
                "\"preview\": \"https://cdns-preview-c.dzcdn.net/stream/c-cca63b2c92773d54e61c5b4d17695bd2-8.mp3\"," +
                "\"artist\": {" +
                "    \"name\": \"Eminem\"," +
                "    \"picture_medium\": \"https://e-cdns-images.dzcdn.net/images/artist/19cc38f9d69b352f718782e7a22f9c32/250x250-000000-80-0-0.jpg\"" +
                "}" +
                "}"
    )

    private val listOfSongs = listOf(song1, song2, song3)

    @Test
    fun testCalculateNextIndex() {
        //when & then
        model.currentSong = song1
        assertEquals(1, model.calculateNextIndex(listOfSongs))

        model.currentSong = song2
        assertEquals(2, model.calculateNextIndex(listOfSongs))

        model.currentSong = song3
        assertEquals(0, model.calculateNextIndex(listOfSongs))
    }

    @Test
    fun testCalculatePreviousIndex() {
        //when & then
        model.currentSong = song1
        assertEquals(2, model.calculatePreviousIndex(listOfSongs))

        model.currentSong = song2
        assertEquals(0, model.calculatePreviousIndex(listOfSongs))

        model.currentSong = song3
        assertEquals(1, model.calculatePreviousIndex(listOfSongs))
    }
}