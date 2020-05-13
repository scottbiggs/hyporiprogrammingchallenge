package com.sleepfuriously.hyporiprogrammingchallenge.model

import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.util.ArrayList

/**
 * Use this to fetch movie data
 */

object MovieRequester {

    //---------------------------------
    //  constants
    //---------------------------------

    val TAG = "MovieRequester"
    val MOVIELIST_URL = "https://swapi.dev/api/films/"

    //---------------------------------
    //  data
    //---------------------------------

    //---------------------------------
    //  functions
    //---------------------------------

    fun getMovies(ctx: Context, movieCallback: (movieList: List<Movie>?) -> Unit) {

        // setup a volley request
        val q = Volley.newRequestQueue(ctx)
        val request = JsonObjectRequest(
            Request.Method.GET, MOVIELIST_URL, null,

            // successful response
            Response.Listener { response ->
                Log.d(TAG, "success!")
                val movieList = mutableListOf<Movie>()

                val jsonMovieList = response.getJSONArray("results")
                val length = response.getInt("count")

                for (i in 0 until jsonMovieList.length()) {
                    try {
                        val jsonObject = jsonMovieList.getJSONObject(i)
                        movieList.add(Movie(jsonObject))
                    }
                    catch (e: JSONException) {
                        Log.e(TAG, "Unable to parse json array in getMovies()")
                        e.printStackTrace()
                    }
                }

                movieCallback.invoke(movieList)     // return the movie list
            },

            // error response
            Response.ErrorListener { error ->
                Log.e(TAG, "Volley error in getMovies()")
                error.printStackTrace()
                movieCallback.invoke(null)
            })
        q.add(request)

    }

}