package com.sleepfuriously.hyporiprogrammingchallenge.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.snackbar.Snackbar
import com.sleepfuriously.hyporiprogrammingchallenge.R
import com.sleepfuriously.hyporiprogrammingchallenge.dummy.DummyContent
import com.sleepfuriously.hyporiprogrammingchallenge.presenter.Presenter
import kotlinx.android.synthetic.main.activity_item_detail.*
import kotlinx.android.synthetic.main.main_list.*
import kotlinx.android.synthetic.main.movie_detail.view.*

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

    //-----------------------------
    //  data
    //-----------------------------

    /**
     * The dummy content this fragment is presenting.
     */
    private var item: DummyContent.DummyItem? = null

    /**
     * Id for the movie we're displaying. This number is the suffix for its url.
     */
//    private var mMovieID: Int? = null

    /**
     * Url for this movie data. Will be sent in by whatever starts this Fragment.
     */
    private var mMovieUrl: String? = null

    //-----------------------------
    //  functions
    //-----------------------------

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        mMovieID = arguments?.getInt(ARG_MOVIE_ID)
        mMovieUrl = arguments?.getString(ARG_MOVIE_URL)

        // todo: remove this--it doesn't do anything (I think)
        arguments?.let {
            if (it.containsKey(ARG_ITEM_ID)) {
                // Load the dummy content specified by the fragment
                // arguments. In a real-world scenario, use a Loader
                // to load content from a content provider.
                item = DummyContent.ITEM_MAP[it.getString(ARG_ITEM_ID)]
                activity?.toolbar_layout?.title = item?.content
            }
        }
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {

        val rootView = inflater.inflate(R.layout.movie_detail, container, false)

        rootView.movie_detail_sv.title_tv.text = "test this is just a test"

        // grab the context.  Sorry about the unnecessary checks, but this is KOTLIN!
        val ctx = context ?: return rootView
        val url = mMovieUrl ?: return rootView


        // request data for this movie
        Presenter.requestMovieData(ctx, url) { movieData ->
            if (movieData != null) {
                rootView.title_tv.text = movieData?.title
            }
            else {
                Log.e(TAG, "error getting movie data")
                Snackbar.make(rootView, R.string.internet_probs, Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
            }
        }

        return rootView
    }

    companion object {
        /**
         * The fragment argument representing the item ID that this fragment
         * represents.
         */
        const val ARG_ITEM_ID = "movie_id"

        /**
         * The id for the movie to display
         */
        const val ARG_MOVIE_ID = "movie_id"

        /**
         * Url for the movie data
         */
        const val ARG_MOVIE_URL = "movie_url"
    }
}
