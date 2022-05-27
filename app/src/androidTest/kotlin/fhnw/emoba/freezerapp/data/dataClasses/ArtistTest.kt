package fhnw.emoba.freezerapp.data.dataClasses

import junit.framework.Assert.assertEquals
import org.junit.Test

internal class ArtistTest {
    @Test
    fun testConstructor() {
        //given
        val jsonString = "{\n" +
                "  \"id\": \"73301752\",\n" +
                "  \"name\": \"Eminem\"\n" +
                "}"

        //when
        val artist = Artist(jsonString)

        //then
        assertEquals("73301752", artist.id)
        assertEquals("Eminem", artist.name)
    }
}