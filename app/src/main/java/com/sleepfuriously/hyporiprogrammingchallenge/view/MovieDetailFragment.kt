package com.sleepfuriously.hyporiprogrammingchallenge.view

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar
import com.sleepfuriously.hyporiprogrammingchallenge.R
import com.sleepfuriously.hyporiprogrammingchallenge.model.SWMovie
import com.sleepfuriously.hyporiprogrammingchallenge.presenter.Presenter
import kotlinx.android.synthetic.main.movie_detail.view.*

/**
 * A fragment representing detail of a single movie.
 * This fragment is either contained in a [MainListActivity]
 * in two-pane mode (on tablets) or a [MovieDetailActivity]
 * on handsets.
 */
class MovieDetailFragment : Fragment() {

    //-----------------------------
    //  constants
    //-----------------------------

    @Suppress("PrivatePropertyName")
    private val TAG = "MovieDetailFragment"

    /** pixels separating the various elements */
    @Suppress("PrivatePropertyName")
    private val SPACING_FOR_LISTS = 16

    companion object {
        /**
         * key for url for the movie data
         */
        const val MOVIE_URL_KEY = "movie_url"
    }

    /** Holds all the info on this movie that we're displaying */
    private var mMovieData: SWMovie? = null

    //-----------------------------
    //  data
    //-----------------------------

    /**
     * Url for this movie data. Sent into this Fragment's arguments
     * via [MOVIE_URL_KEY].
     */
    private var mMovieUrl: String? = null

    //-----------------------------
    //  functions
    //-----------------------------

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mMovieUrl = arguments?.getString(MOVIE_URL_KEY)

//        arguments?.let {
//            if (it.containsKey(ARG_ITEM_ID)) {
//                // Load the dummy content specified by the fragment
//                // arguments. In a real-world scenario, use a Loader
//                // to load content from a content provider.
//                item = DummyContent.ITEM_MAP[it.getString(ARG_ITEM_ID)]
//                activity?.toolbar_layout?.title = item?.content
//            }
//        }
    }


    @Suppress("RedundantSamConstructor")
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {

        val rootView = inflater.inflate(R.layout.movie_detail, container, false)

        // grab the context.  Sorry about the unnecessary checks, but this is KOTLIN!
        val ctx = context ?: return rootView
        val url = mMovieUrl ?: return rootView


        // set the button listeners

        run {
            val characterButton = rootView.findViewById<Button>(R.id.characters_butt)
            characterButton.setOnClickListener(View.OnClickListener {
                grabCharacters(ctx, characterButton, characterButton.parent as ViewGroup)
            })
        }


        run {
            val planetButton = rootView.findViewById<Button>(R.id.planets_butt)
            planetButton.setOnClickListener(View.OnClickListener {
                grabPlanets(ctx, planetButton, planetButton.parent as ViewGroup)
            })
        }

        run {
            val starshipsButton = rootView.findViewById<Button>(R.id.starships_butt)
            starshipsButton.setOnClickListener(View.OnClickListener {
                Snackbar.make(rootView, R.string.not_implemented, Snackbar.LENGTH_SHORT)
                    .setAction("Action", null).show()
            })
        }

        run {
            val vehiclesButton = rootView.findViewById<Button>(R.id.vehicles_butt)
            vehiclesButton.setOnClickListener(View.OnClickListener {
                Snackbar.make(rootView, R.string.not_implemented, Snackbar.LENGTH_SHORT)
                    .setAction("Action", null).show()
            })
        }

        run {
            val speciesButton = rootView.findViewById<Button>(R.id.species_butt)
            speciesButton.setOnClickListener(View.OnClickListener {
                Snackbar.make(rootView, R.string.not_implemented, Snackbar.LENGTH_SHORT)
                    .setAction("Action", null).show()
            })
        }

        // todo:
//        run {
//            val starshipsButton = rootView.findViewById<Button>(R.id.starships_butt)
//            starshipsButton.setOnClickListener(View.OnClickListener {
//                val parent = starshipsButton.parent as ViewGroup
//                val buttonPos = parent.indexOfChild(starshipsButton)
//                grabStarships(ctx, starshipsButton.parent as ViewGroup, buttonPos + 1)
//            })
//        }
//
//        run {
//            val vehiclesButton = rootView.findViewById<Button>(R.id.vehicles_butt)
//            vehiclesButton.setOnClickListener(View.OnClickListener {
//                val parent = vehiclesButton.parent as ViewGroup
//                val buttonPos = parent.indexOfChild(vehiclesButton)
//                grabVehicles(ctx, vehiclesButton.parent as ViewGroup, buttonPos + 1)
//            })
//        }
//
//        run {
//            val speciesButton = rootView.findViewById<Button>(R.id.species_butt)
//            speciesButton.setOnClickListener(View.OnClickListener {
//                val parent = speciesButton.parent as ViewGroup
//                val buttonPos = parent.indexOfChild(speciesButton)
//                grabSpecies(ctx, speciesButton.parent as ViewGroup, buttonPos + 1)
//            })
//        }


        // request data for this movie
        turnOnWaitingUI()
        Presenter.requestMovieData(ctx, url) { movieData ->
            mMovieData = movieData
            if (movieData != null) {
                rootView.title_tv.text = movieData.title
                rootView.crawl_tv.text = movieData.openingCrawl
                rootView.director_tv.text = movieData.director
                rootView.producer_tv.text = movieData.producer

                rootView.release_tv.text = movieData.dateReleased
                rootView.create_tv.text =movieData.dateCreated
                rootView.edited_tv.text = movieData.dateEdited

                turnOffWaitingUI()
            }
            else {
                turnOffWaitingUI()
                Log.e(TAG, "error getting movie data")
                Snackbar.make(rootView, R.string.internet_probs, Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
            }
        }

        return rootView
    }


    override fun onDestroyView() {
        Presenter.cancelAllDataRequests()
        super.onDestroyView()
    }


    /**
     * Turns on the progressbar--should work no matter which activity we're in
     */
    private fun turnOnWaitingUI() {
        activity ?: return      // kotlin verbosity

        if (activity is MainListActivity) {
            val parentActivity = activity as MainListActivity
            parentActivity.turnOnWaitingUI()
        }
        else {
            val parentActivity = activity as MovieDetailActivity
            parentActivity.turnOnWaitingUI()
        }
    }

    /**
     * Turns off the progress bar
     */
    private fun turnOffWaitingUI() {
        activity ?: return      // kotlin verbosity

        if (activity is MainListActivity) {
            val parentActivity = activity as MainListActivity
            parentActivity.turnOffWaitingUI()
        }
        else {
            val parentActivity = activity as MovieDetailActivity
            parentActivity.turnOffWaitingUI()
        }
    }


    /**
     * User has decided to get more character info.
     * Let 'em have it.
     *
     * @param   ctx     check
     *
     * @param   button  the button that was pressed
     *
     * @param   parent  The viewGroup that this belongs to
     */
    private fun grabCharacters(ctx: Context, button: Button, parent: ViewGroup) {
        val urlList = arrayListOf<String>()

        // make character list
        for (i in 0 until mMovieData?.characters?.length()!!) {     // ew!! I'm so glad Kotlin doesn't have so much boilerplate code!
            val url: String? = mMovieData?.characters?.getString(i)
            if (url != null) {
                urlList.add(url)
            }
        }

        // go through each character, getting its data
        // and putting that data into a new TextView.
        // Add that TextView to the layout.

        val layoutParams = LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
        layoutParams.bottomMargin = SPACING_FOR_LISTS

        var countdown = urlList.size    // used to know when to turn off the progress bar (when this is zero!)
        turnOnWaitingUI()
        for (i in 0 until urlList.size) {
            Presenter.requestCharacterData(ctx, urlList[i]) { characterData ->
                if (characterData != null) {
                    // success
                    val tv = TextView(ctx)
                    tv.text = characterData.toString(true)
                    parent.addView(tv, layoutParams)

                    countdown--
                    if (countdown == 0) {
                        turnOffWaitingUI()
                        button.isEnabled = false    // way counter-intuitive!
                    }
                }
                else {
                    // error
                    turnOffWaitingUI()
                    Log.e(TAG, "error getting character data")
                    Snackbar.make(parent, R.string.internet_probs, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show()
                    Presenter.cancelAllDataRequests()
                }

            }
        }
    }

    /**
     * User has decided to get more planets info.
     * Let 'em have it.
     *
     * Very similar to [grabCharacters].
     *
     * @param   ctx     check
     *
     * @param   button  the button that was pressed
     *
     * @param   parent  The viewGroup that this belongs to
     */
    private fun grabPlanets(ctx: Context, button: Button, parent: ViewGroup) {
        val urlList = arrayListOf<String>()

        for (i in 0 until mMovieData?.planets?.length()!!) {     // ew!! I'm so glad Kotlin doesn't have so much boilerplate code!
            val url: String? = mMovieData?.planets?.getString(i)
            if (url != null) {
                urlList.add(url)
            }
        }

        // go through each planet, getting its data
        // and put that data into a new TextView.
        // Add that TextView to the layout.

        val layoutParams = LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
        layoutParams.bottomMargin = SPACING_FOR_LISTS

        var countdown = urlList.size    // used to know when to turn off the progress bar (when this is zero!)
        turnOnWaitingUI()
        for (i in 0 until urlList.size) {
            Presenter.requestPlanetData(ctx, urlList[i]) { planetData ->

                if (planetData != null) {
                    // success
                    val tv = TextView(ctx)
                    tv.text = planetData.toString(true)
                    parent.addView(tv, layoutParams)

                    countdown--
                    if (countdown == 0) {
                        turnOffWaitingUI()
                        button.isEnabled = false    // way counter-intuitive!
                    }
                }
                else {
                    // error
                    turnOffWaitingUI()
                    Log.e(TAG, "error getting planet data")
                    Snackbar.make(parent, R.string.internet_probs, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show()
                    Presenter.cancelAllDataRequests()
                }

            }
        }
    }


}
