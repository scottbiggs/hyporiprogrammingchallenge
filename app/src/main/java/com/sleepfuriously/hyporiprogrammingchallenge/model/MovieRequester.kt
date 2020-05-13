package com.sleepfuriously.hyporiprogrammingchallenge.model

import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException

/**
 * Use this to fetch movie data
 */

object MovieRequester {

    //---------------------------------
    //  constants
    //---------------------------------

    private const val TAG = "MovieRequester"
    private const val MOVIELIST_URL = "https://swapi.dev/api/films/"

    //---------------------------------
    //  data
    //---------------------------------

    /**
     * Holds the request queue.  Needed to properly cancel a
     * request in progress.
     *
     * Should be null when not in use.
     */
    private var movieRequestQ: RequestQueue? = null


    //---------------------------------
    //  functions
    //---------------------------------

    /**
     * Use this to get a list of movies.
     *
     * @param   ctx     Can't do anything without this!
     *
     * @param   movieCallback   The function to call once the list is ready.
     *                          It will have a parameter movieList that
     *                          holds a list [Movie]s.  If there is an error,
     *                          movieList will be null.
     */
    fun getMovies(ctx: Context, movieCallback: (movieList: List<Movie>?) -> Unit) {

        // setup a volley request
        movieRequestQ = Volley.newRequestQueue(ctx)
        val request = JsonObjectRequest(
            Request.Method.GET, MOVIELIST_URL, null,

            // successful response
            Response.Listener { response ->
                val movieList = mutableListOf<Movie>()

                // parse the json until we get to the movies (have to dig 1 level).
                val jsonMovieList = response.getJSONArray("results")

                val length = response.getInt("count")
                if (length != jsonMovieList.length()) {
                    Log.e(TAG, "incompatible lengths in jsonMovieList. Expected $length, but found ${jsonMovieList.length()}")
                    movieCallback.invoke(null)
                }

                // add each movie to return value
                for (i in 0 until length) {
                    try {
                        val jsonObject = jsonMovieList.getJSONObject(i)
                        movieList.add(Movie(jsonObject))    // return error condition
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
        movieRequestQ?.add(request)
    }


    /**
     * Cancels a request for movies.  Most likely use is for a configuration
     * change where the requester will be destroyed before the request can
     * be completed.
     */
    fun cancelMovieRequest() {
        movieRequestQ?.cancelAll(this)
    }

}