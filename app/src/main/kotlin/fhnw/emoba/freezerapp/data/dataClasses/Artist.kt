package fhnw.emoba.freezerapp.data.dataClasses

import org.json.JSONObject
import java.net.URL

class Artist(json: JSONObject) {
    val id: String = json.getString("id")
    val name: String = json.getString("name")


    constructor(jsonString: String) : this(JSONObject(jsonString))
}
