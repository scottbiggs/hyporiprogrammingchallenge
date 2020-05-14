package com.sleepfuriously.hyporiprogrammingchallenge.model

import org.json.JSONArray
import org.json.JSONObject
import java.lang.StringBuilder

/**
 * Describes a person
 */

@Suppress("HasPlatformType", "MemberVisibilityCanBePrivate")
data class SWCharacter (val json: JSONObject) {

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
    val vehicles: JSONArray = json.getJSONArray("vehicles")

    /**
     * Returns a human-readable version of this person's data.
     *
     * @param   newLines    If TRUE, each item is separated by
     *                      a newline.  Otherwise, a comma and
     *                      a space.
     */
    fun toString(newLines: Boolean): String {
        val strBuilder = StringBuilder()
        var insertStr = ", "
        if (newLines) {
            insertStr = "\n"
        }
        strBuilder.append(name)
            .append(insertStr)
            .append(height)
            .append(insertStr)
            .append(mass)
            .append(insertStr)
            .append(hairColor)
            .append(insertStr)
            .append(skinColor)
            .append(insertStr)
            .append(eyeColor)
            .append(insertStr)
            .append(birthYear)
            .append(insertStr)
            .append(gender)
            .append(insertStr)
            .append(homeworldUrl)
        return strBuilder.toString()
    }
}