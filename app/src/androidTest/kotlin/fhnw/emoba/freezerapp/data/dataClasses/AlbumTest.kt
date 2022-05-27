package fhnw.emoba.freezerapp.data.dataClasses

import android.graphics.Bitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import fhnw.emoba.freezerapp.data.impl.RemoteFreezerService
import junit.framework.Assert.*
import org.junit.Test

internal class AlbumTest {
    @Test
    fun testConstructor() {
        //given
        val jsonString = "{\n" +
                "  \"id\": \"103248\",\n" +
                "  \"title\": \"The Eminem Show\",\n" +
                "  \"cover_medium\": \"https://e-cdns-images.dzcdn.net/images/cover/ec3c8ed67427064c70f67e5815b74cef/250x250-000000-80-0-0.jpg\",\n" +
                "  \"tracklist\": \"https://api.deezer.com/album/103248/tracks\"\n" +
                "}"

        //when
        val album = Album(jsonString)

        //then
        assertEquals(103248, album.id)
        assertEquals("The Eminem Show", album.title)
        assertEquals(
            "https://e-cdns-images.dzcdn.net/images/cover/ec3c8ed67427064c70f67e5815b74cef/250x250-000000-80-0-0.jpg",
            album.coverMedium
        )
        assertEquals("https://api.deezer.com/album/103248/tracks", album.trackList)

        assertTrue(
            imagesAreEqual(
                Bitmap.createBitmap(
                    30,
                    30,
                    Bitmap.Config.ALPHA_8
                ),
                album.cover.asAndroidBitmap()
            )
        )
    }

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