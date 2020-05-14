package com.sleepfuriously.hyporiprogrammingchallenge.model

import org.json.JSONObject
import java.lang.StringBuilder

/**
 * Describes the contents of a Movie.  Corresponds to
 * the equivalent JSON listing.
 */

@Suppress("HasPlatformType", "MemberVisibilityCanBePrivate")
data class SWPlanet (val json: JSONObject){

    val name = json.getString("name")
    val diameter = json.getString("diameter")
    val rotationPeriod = json.getString("rotation_period")
    val orbitalPeriod = json.getString("orbital_period")
    val gravity = json.getString("gravity")
    val population = json.getString("population")
    val climate = json.getString("climate")
    val terrain = json.getString("terrain")
    val surfaceWater = json.getString("surface_water")
    val url = json.getString("url")
    val created = json.getString("created")
    val edited = json.getString("edited")

    val residents = json.getJSONArray("residents")
    val films = json.getJSONArray("films")

    /**
     * Returns a human-readable string of this data.
     *
     * @param   newLines    When TRUE, add newlines between each
     *                      data item.
     */
    fun toString(newLines: Boolean): String {
        val strBuilder = StringBuilder()
        var insertStr = ", "
        if (newLines) {
            insertStr = "\n"
        }
        strBuilder.append(name)
            .append(insertStr)
            .append(diameter)
            .append(insertStr)
            .append(rotationPeriod)
            .append(insertStr)
            .append(orbitalPeriod)
            .append(insertStr)
            .append(gravity)
            .append(insertStr)
            .append(population)
            .append(insertStr)
            .append(climate)
            .append(insertStr)
            .append(terrain)
            .append(insertStr)
            .append(surfaceWater)
            .append(insertStr)
            .append("contains ${residents.length()} resident species)")
            .append(insertStr)
            .append("appears in ${films.length()} films")
        return strBuilder.toString()
    }

}
