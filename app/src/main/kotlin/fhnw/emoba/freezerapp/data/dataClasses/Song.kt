package fhnw.emoba.freezerapp.data.dataClasses

import android.graphics.Bitmap
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.asImageBitmap
import org.json.JSONObject
import java.net.URL

class Song(json: JSONObject) {
    val id = json.getInt("id")
    val title: String = json.getString("title")
    val artistName: String = json.getJSONObject("artist").getString("name")
    val duration = json.getInt("duration")
    val preview: String = json.getString("preview")
    val coverMedium: String = json.getJSONObject("artist").getString("picture_medium") // URL
    var cover by mutableStateOf(
        Bitmap.createBitmap(
            30,
            30,
            Bitmap.Config.ALPHA_8
        ).asImageBitmap() // creates default bitmap image
    )

    var isFavorite by mutableStateOf(false)

    constructor(jsonString: String) : this(JSONObject(jsonString))
}
