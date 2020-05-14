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

    private const val TAG = "Presenter"


    /**
     * Anything that wants a list of [Movie]s should call this.
     *
     * @param   ctx     Nothing much can happen without this
     *
     * @param   movieListCallback   Callback function to receive the list of movies.
     *                              It will receive the list as a parameter.
     */
    fun requestMovieList(ctx: Context, movieListCallback: (movieList: List<Movie>?) -> Unit) {

        MovieRequester.getMovies(ctx) { movies ->
            Log.d(TAG, "movies: ${movies?.size}")
            movieListCallback.invoke(movies)
        }
    }

    /**
     * Change your mind (or is the context about to die)?  Please
     * call this function to cancel the request for movies.
     */
    fun cancelMovieListRequest() {
        MovieRequester.cancelMovieRequest()
    }


    fun requestMovieData(ctx: Context, movieUrl: String, movieDataCallback: (movie: Movie?) -> Unit) {

        MovieRequester.requestMovieData(ctx, movieUrl) { movie ->
            movieDataCallback.invoke(movie)
        }
    }

    fun cancelMovieDataRequest() {
        MovieRequester.cancelMovieDataRequest()
    }
}