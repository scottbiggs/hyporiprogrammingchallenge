package com.sleepfuriously.hyporiprogrammingchallenge.presenter

import android.content.Context
import com.sleepfuriously.hyporiprogrammingchallenge.model.SWMovie

import com.sleepfuriously.hyporiprogrammingchallenge.model.MovieRequester
import com.sleepfuriously.hyporiprogrammingchallenge.model.SWCharacter
import com.sleepfuriously.hyporiprogrammingchallenge.model.SWPlanet

/**
 * Intermediary between the model and the view classes.
 *
 * Implemented as a singleton--for obvious reasons.
 */

object Presenter {

    private const val TAG = "Presenter"


    /**
     * Anything that wants a list of [SWMovie]s should call this.
     *
     * @param   ctx     Nothing much can happen without this
     *
     * @param   movieListCallback   Callback function to receive the list of movies.
     *                              It will receive the list as a parameter.
     */
    fun requestMovieList(ctx: Context, movieListCallback: (movieList: List<SWMovie>?) -> Unit) {
        MovieRequester.getMovies(ctx) { movies ->
            movieListCallback.invoke(movies)
        }
    }


    /**
     * Gets the data for a specific movie.
     *
     * @param   ctx     always
     *
     * @param   movieUrl    The url of the movie's database
     *
     * @param   movieDataCallback   Will be called when data is ready.
     *                              The param holds the data, which will
     *                              be null if there was a problem.
     */
    fun requestMovieData(ctx: Context, movieUrl: String, movieDataCallback: (movie: SWMovie?) -> Unit) {
        MovieRequester.requestMovieData(ctx, movieUrl) { movie ->
            movieDataCallback.invoke(movie)
        }
    }


    /**
     * Gets data for a specific character
     *
     * @param   ctx     yup
     *
     * @param   charUrl Where to find the data
     *
     * @param   charDataCallback    Called when data is ready.
     *                              The param holds the data (holds
     *                              null on error).
     */
    fun requestCharacterData(ctx: Context, charUrl: String, charDataCallback: (charData: SWCharacter?) -> Unit) {
        MovieRequester.requestCharacter(ctx, charUrl) { character ->
            charDataCallback.invoke(character)
        }
    }

    fun requestPlanetData(ctx: Context, planetUrl: String, planetDataCallback: (planetData: SWPlanet?) -> Unit) {
        MovieRequester.requestPlanet(ctx, planetUrl) { planet ->
            planetDataCallback.invoke(planet)
        }
    }



    /**
     * Change your mind (or is the context about to die)?  Please
     * call this function to cancel the request for movies.
     */
    fun cancelAllDataRequests() {
        MovieRequester.cancelAllDataRequests()
    }
}