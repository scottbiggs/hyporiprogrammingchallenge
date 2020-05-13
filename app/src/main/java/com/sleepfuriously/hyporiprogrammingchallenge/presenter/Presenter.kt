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


    fun requestMovieList(ctx: Context, movieCallback: (movieList: List<Movie>?) -> Unit) {

        MovieRequester.getMovies(ctx) { movies ->
            Log.d(TAG, "movies: ${movies?.size}")
            movieCallback.invoke(movies)
        }
    }


}