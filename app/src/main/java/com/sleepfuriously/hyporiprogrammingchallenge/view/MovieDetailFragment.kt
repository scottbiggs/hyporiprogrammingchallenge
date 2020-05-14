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
import androidx.core.view.marginBottom
import com.google.android.material.snackbar.Snackbar
import com.sleepfuriously.hyporiprogrammingchallenge.R
import com.sleepfuriously.hyporiprogrammingchallenge.dummy.DummyContent
import com.sleepfuriously.hyporiprogrammingchallenge.model.SWCharacter
import com.sleepfuriously.hyporiprogrammingchallenge.model.SWMovie
import com.sleepfuriously.hyporiprogrammingchallenge.presenter.Presenter
import kotlinx.android.synthetic.main.movie_detail.*
import kotlinx.android.synthetic.main.movie_detail.view.*
import java.lang.StringBuilder

/**
 * A fragment representing detail of a single movie.
 * This fragment is either contained in a [MainListActivity]
 * in two-pane mode (on tablets) or a [ItemDetailActivity]
 * on handsets.
 */
class MovieDetailFragment : Fragment() {

    //-----------------------------
    //  constants
    //-----------------------------

    @Suppress("PrivatePropertyName")
    private val TAG = "MovieDetailFragment"

    companion object {
        /**
         * key for url for the movie data
         */
        const val MOVIE_URL_KEY = "movie_url"
    }

    /** Holds all the info on this movie that we're displaying */
    var mMovieData: SWMovie? = null

    //-----------------------------
    //  data
    //-----------------------------

    /**
     * The dummy content this fragment is presenting.
     */
    private var item: DummyContent.DummyItem? = null

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


    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {

        val rootView = inflater.inflate(R.layout.movie_detail, container, false)

        // grab the context.  Sorry about the unnecessary checks, but this is KOTLIN!
        val ctx = context ?: return rootView
        val url = mMovieUrl ?: return rootView


        // set the button listeners

        val characterButton = rootView.findViewById<Button>(R.id.characters_butt)
        characterButton.setOnClickListener(View.OnClickListener {
            val parent = characterButton.parent as ViewGroup
            val buttonPos = parent.indexOfChild(characterButton)
            grabCharacters(ctx, characterButton.parent as ViewGroup, buttonPos + 1)
        })


//        characters_butt.setOnClickListener(object : View.OnClickListener {
//            override fun onClick(v: View?) {
//                grabCharacters()
//            }
//        })

//        planets_butt.setOnClickListener(object : View.OnClickListener {
//            override fun onClick(v: View?) {
//                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//            }
//        })
//
//        starships_butt.setOnClickListener(object : View.OnClickListener {
//            override fun onClick(v: View?) {
//                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//            }
//        })
//
//        vehicles_butt.setOnClickListener(object : View.OnClickListener {
//            override fun onClick(v: View?) {
//                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//            }
//        })
//
//        species_butt.setOnClickListener(object : View.OnClickListener {
//            override fun onClick(v: View?) {
//                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//            }
//        })

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

                // todo characters

                // todo planets

                // todo starships

                // todo vehicles

                // todo species
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
     * @param   parent  The viewGroup that this belongs to
     *
     * @param   startingPos     The position in the viewgroup that the first
     *                          element should be put.
     */
    private fun grabCharacters(ctx: Context, parent: ViewGroup, startingPos: Int) {
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
        layoutParams.bottomMargin = 8

        for (i in 0 until urlList.size) {
            Presenter.requestCharacterData(ctx, urlList[i]) { characterData ->
                val tv = TextView(ctx)
                tv.text = characterData?.toString(true)
                parent.addView(tv, layoutParams)
            }
        }
    }

}
