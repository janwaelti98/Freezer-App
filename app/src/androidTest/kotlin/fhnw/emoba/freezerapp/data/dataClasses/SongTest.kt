package fhnw.emoba.freezerapp.data.dataClasses

import android.graphics.Bitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.asImageBitmap
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertTrue
import org.junit.Test

internal class SongTest {
    @Test
    fun testConstructor() {
        //given
        val jsonString = "{\"id\": 916424," +
                "\"title\": \"Without Me\"," +
                "\"duration\": \"290\"," +
                "\"preview\": \"https://cdns-preview-c.dzcdn.net/stream/c-cca63b2c92773d54e61c5b4d17695bd2-8.mp3\"," +
                "\"artist\": {" +
                "    \"name\": \"Eminem\"," +
                "    \"picture_medium\": \"https://e-cdns-images.dzcdn.net/images/artist/19cc38f9d69b352f718782e7a22f9c32/250x250-000000-80-0-0.jpg\"" +
                "}" +
                "}"

        //when
        val song = Song(jsonString)

        //then
        assertEquals(916424, song.id)
        assertEquals("Without Me", song.title)
        assertEquals(290, song.duration)
        assertEquals(
            "https://cdns-preview-c.dzcdn.net/stream/c-cca63b2c92773d54e61c5b4d17695bd2-8.mp3",
            song.preview
        )
        assertEquals("Eminem", song.artistName)
        assertEquals(
            "https://e-cdns-images.dzcdn.net/images/artist/19cc38f9d69b352f718782e7a22f9c32/250x250-000000-80-0-0.jpg",
            song.coverMedium
        )
        assertTrue(
            imagesAreEqual(
                Bitmap.createBitmap(
                    30,
                    30,
                    Bitmap.Config.ALPHA_8
                ).asImageBitmap().asAndroidBitmap(), song.cover.asAndroidBitmap()
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