package com.sleepfuriously.hyporiprogrammingchallenge.model

import org.json.JSONArray
import org.json.JSONObject

/**
 * Describes a person
 */

data class People (val json: JSONObject) {

    val name = json.getString("name")
    val height = json.getString("height")
    val mass = json.getString("mass")
    val hairColor = json.getString("hair_color")
    val skinColor = json.getString("skin_color")
    val eyeColor = json.getString("eye_color")
    val birthYear = json.getString("birth_year")
    val gender = json.getString("gender")
    val homeworldUrl = json.getString("homeworld")
    val dateCreated = json.getString("created")
    val dateEdited = json.getString("edited")
    val url = json.getString("url")

    val films: JSONArray = json.getJSONArray("films")
    val species: JSONArray = json.getJSONArray("species")
    val starships: JSONArray = json.getJSONArray("starships")

}