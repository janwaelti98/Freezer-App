package fhnw.emoba.freezerapp.data.impl

import android.graphics.Bitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.asImageBitmap
import fhnw.emoba.freezerapp.data.dataClasses.Song
import junit.framework.Assert.*
import org.junit.Test

class RemoteFreezerServiceTest {
    @Test
    fun testRequestSearchedSong() {
        //given
        val service = RemoteFreezerService()

        //when
        val searchedSongList = service.requestSearchedSong("eminem")

        //then
        assertEquals(916424, searchedSongList[0].id)
        assertEquals("Without Me", searchedSongList[0].title)
        assertEquals("Eminem", searchedSongList[0].artistName)
        assertEquals(290, searchedSongList[0].duration)
        assertEquals(
            "https://cdns-preview-c.dzcdn.net/stream/c-cca63b2c92773d54e61c5b4d17695bd2-8.mp3",
            searchedSongList[0].preview
        )
        assertEquals(
            "https://e-cdns-images.dzcdn.net/images/artist/19cc38f9d69b352f718782e7a22f9c32/250x250-000000-80-0-0.jpg",
            searchedSongList[0].coverMedium
        )
        assertTrue(
            imagesAreEqual(
                Bitmap.createBitmap(
                    30,
                    30,
                    Bitmap.Config.ALPHA_8
                ).asImageBitmap().asAndroidBitmap(), searchedSongList[0].cover.asAndroidBitmap()
            )
        )
        assertEquals(25, searchedSongList.size)
        assertEquals(false, searchedSongList[0].isFavorite)
    }

    @Test
    fun testRequestSearchedAlbum() {
        //given
        val service = RemoteFreezerService()

        //when
        val searchAlbumList = service.requestSearchedAlbum("eminem")

        //then
        assertEquals(103248, searchAlbumList[0].id)
        assertEquals("The Eminem Show", searchAlbumList[0].title)
        assertEquals(
            "https://e-cdns-images.dzcdn.net/images/cover/ec3c8ed67427064c70f67e5815b74cef/250x250-000000-80-0-0.jpg",
            searchAlbumList[0].coverMedium
        )
        assertTrue(
            imagesAreEqual(
                Bitmap.createBitmap(
                    30,
                    30,
                    Bitmap.Config.ALPHA_8
                ).asImageBitmap().asAndroidBitmap(), searchAlbumList[0].cover.asAndroidBitmap()
            )
        )
        assertEquals(
            "https://api.deezer.com/album/103248/tracks",
            searchAlbumList[0].trackList
        )
        assertEquals(25, searchAlbumList.size)
    }

    @Test
    fun testRequestAllRadioStations() {
        //given
        val service = RemoteFreezerService()

        //when
        val radioStationList = service.requestAllRadioStations()

        //then
        assertEquals(37151, radioStationList[0].id)
        assertEquals("Hits", radioStationList[0].title)
        assertEquals(
            "https://e-cdns-images.dzcdn.net/images/misc/235ec47f2b21c3c73e02fce66f56ccc5/250x250-000000-80-0-0.jpg",
            radioStationList[0].coverMedium
        )
        assertTrue(
            imagesAreEqual(
                Bitmap.createBitmap(
                    30,
                    30,
                    Bitmap.Config.ALPHA_8
                ).asImageBitmap().asAndroidBitmap(), radioStationList[0].cover.asAndroidBitmap()
            )
        )
        assertEquals("https://api.deezer.com/radio/37151/tracks", radioStationList[0].trackList)
        assertEquals(138, radioStationList.size)
    }

    @Test
    fun testRequestImages() {
        //given
        val service = RemoteFreezerService()

        //when
        val defaultImage1 = service.requestAllRadioStations()[0].cover.asAndroidBitmap()
        val defaultImage2 = service.requestAllRadioStations()[1].cover.asAndroidBitmap()
        val defaultImage3 = service.requestAllRadioStations()[2].cover.asAndroidBitmap()
        val defaultImage4 = service.requestAllRadioStations()[3].cover.asAndroidBitmap()
        val defaultImage5 = service.requestAllRadioStations()[4].cover.asAndroidBitmap()

        val cover1 = service.requestImage(service.requestAllRadioStations()[0].coverMedium)
            .asAndroidBitmap()
        val cover2 = service.requestImage(service.requestAllRadioStations()[1].coverMedium)
            .asAndroidBitmap()
        val cover3 = service.requestImage(service.requestAllRadioStations()[2].coverMedium)
            .asAndroidBitmap()
        val cover4 = service.requestImage(service.requestAllRadioStations()[3].coverMedium)
            .asAndroidBitmap()
        val cover5 = service.requestImage(service.requestAllRadioStations()[4].coverMedium)
            .asAndroidBitmap()

        // then (tests if cover is no longer the default image)
        assertFalse(imagesAreEqual(defaultImage1, cover1))
        assertFalse(imagesAreEqual(defaultImage2, cover2))
        assertFalse(imagesAreEqual(defaultImage3, cover3))
        assertFalse(imagesAreEqual(defaultImage4, cover4))
        assertFalse(imagesAreEqual(defaultImage5, cover5))
    }

    @Test
    fun testRequestTracks() {
        //given
        val service = RemoteFreezerService()

        //when
        val trackList = service.requestTracks("https://api.deezer.com/radio/37151/tracks")
        var trackToTest: Song? = null

        // pick to tested track, since trackList does not always have the same order
        for (track in trackList) {
            if (track.id == 1437070342) { // test track with id: 1437070342
                trackToTest = track
            }
        }

        //then
        if (trackToTest != null) {
            assertEquals(1437070342, trackToTest.id)
            assertEquals("abcdefu", trackToTest.title)
            assertEquals("GAYLE", trackToTest.artistName)
            assertEquals(168, trackToTest.duration)
            assertEquals(
                "https://cdns-preview-b.dzcdn.net/stream/c-b7612e9d96095179123d14af443b0bba-3.mp3",
                trackToTest.preview
            )
            assertEquals(
                "https://e-cdns-images.dzcdn.net/images/artist/1dbf4b101753aac8efca9276d4419531/250x250-000000-80-0-0.jpg",
                trackToTest.coverMedium
            )
            assertTrue(
                imagesAreEqual(
                    Bitmap.createBitmap(
                        30,
                        30,
                        Bitmap.Config.ALPHA_8
                    ).asImageBitmap().asAndroidBitmap(), trackToTest.cover.asAndroidBitmap()
                )
            )
            assertEquals(false, trackToTest.isFavorite)
            assertEquals(25, trackList.size)
        }
    }


    // helper method to check if two images are equal
    private fun imagesAreEqual(image1: Bitmap, image2: Bitmap): Boolean {
        if (image1.height != image2.height) return false
        if (image1.width != image2.width) return false

        for (y in 0 until image1.height) {
            for (x in 0 until image1.width) {
                if (image1.getPixel(x, y) != image2.getPixel(x, y)) {
                    return false
                }
            }
        }
        return true
    }
}