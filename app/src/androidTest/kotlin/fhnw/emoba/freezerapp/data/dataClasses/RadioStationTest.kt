package fhnw.emoba.freezerapp.data.dataClasses

import android.graphics.Bitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.asImageBitmap
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertTrue
import org.junit.Test

internal class RadioStationTest {
    @Test
    fun testConstructor() {
        //given
        val jsonString = "{\n" +
                "  \"id\": \"37151\",\n" +
                "  \"title\": \"Hits\",\n" +
                "  \"picture_medium\": \"https://e-cdns-images.dzcdn.net/images/misc/235ec47f2b21c3c73e02fce66f56ccc5/250x250-000000-80-0-0.jpg\",\n" +
                "  \"tracklist\": \"https://api.deezer.com/radio/37151/tracks\"\n" +
                "}"

        //when
        val radioStation = RadioStation(jsonString)

        //then
        assertEquals(37151, radioStation.id)
        assertEquals("Hits", radioStation.title)
        assertEquals(
            "https://e-cdns-images.dzcdn.net/images/misc/235ec47f2b21c3c73e02fce66f56ccc5/250x250-000000-80-0-0.jpg",
            radioStation.coverMedium
        )
        assertEquals("https://api.deezer.com/radio/37151/tracks", radioStation.trackList)
        assertTrue(
            imagesAreEqual(
                Bitmap.createBitmap(
                    30,
                    30,
                    Bitmap.Config.ALPHA_8
                ).asImageBitmap().asAndroidBitmap(), radioStation.cover.asAndroidBitmap()
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
