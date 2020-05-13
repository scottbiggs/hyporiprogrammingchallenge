package com.sleepfuriously.hyporiprogrammingchallenge.presenter

import android.content.Context
import android.util.Log
import com.sleepfuriously.hyporiprogrammingchallenge.model.Movie

import com.sleepfuriously.hyporiprogrammingchallenge.model.MovieRequester

/**
 * Intermediary between the model and the view classes.
 *
 * Implemented as a singleton--for obvious reasons.
 */

object Presenter {

    val TAG = "Presenter"


    /**
     * Anything that wants a list of [Movie]s should call this.
     *
     * @param   ctx     Nothing much can happen without this
     *
     * @param   movieCallback   Callback function to receive the list of movies.
     *                          It will receive the list as a parameter.
     */
    fun requestMovieList(ctx: Context, movieCallback: (movieList: List<Movie>?) -> Unit) {

        MovieRequester.getMovies(ctx) { movies ->
            Log.d(TAG, "movies: ${movies?.size}")
            movieCallback.invoke(movies)
        }
    }

    /**
     * Change your mind (or is the context about to die)?  Please
     * call this function to cancel the request for movies.
     */
    fun cancelMovieListRequest() {
        MovieRequester.cancelMovieRequest()
    }

}