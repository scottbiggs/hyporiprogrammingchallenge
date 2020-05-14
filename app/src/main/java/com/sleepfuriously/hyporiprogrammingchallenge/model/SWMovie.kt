package com.sleepfuriously.hyporiprogrammingchallenge.model

import org.json.JSONArray
import org.json.JSONObject

/**
 * Describes the contents of a Movie.  Corresponds to
 * the equivalent JSON listing.
 */

@Suppress("HasPlatformType")
data class SWMovie (val json: JSONObject){

    val title = json.getString("title")
    val openingCrawl = json.getString("opening_crawl")
    val director = json.getString("director")
    val producer = json.getString("producer")
    val dateReleased = json.getString("release_date")
    val dateCreated = json.getString("created")
    val dateEdited = json.getString("edited")
    val url = json.getString("url")

    val episodeID: Int = json.getInt("episode_id")

    val characters: JSONArray = json.getJSONArray("characters")
    val planets: JSONArray = json.getJSONArray("planets")
    val starships: JSONArray = json.getJSONArray("starships")
    val vehicles: JSONArray = json.getJSONArray("vehicles")
    val species: JSONArray = json.getJSONArray("species")

}
