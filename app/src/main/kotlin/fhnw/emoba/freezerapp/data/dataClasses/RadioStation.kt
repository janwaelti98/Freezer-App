package fhnw.emoba.freezerapp.data.dataClasses

import android.graphics.Bitmap
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import fhnw.emoba.R
import org.json.JSONObject
import java.net.URL

class RadioStation(json: JSONObject) {
    val id = json.getInt("id")
    val title: String = json.getString("title")
    val coverMedium: String = json.getString("picture_medium") // URL

    var cover by mutableStateOf(
        Bitmap.createBitmap(
            30,
            30,
            Bitmap.Config.ALPHA_8
        ).asImageBitmap() // creates default bitmap image
    )
    val trackList: String = json.getString("tracklist") // URL

    constructor(jsonString: String) : this(JSONObject(jsonString))
}
