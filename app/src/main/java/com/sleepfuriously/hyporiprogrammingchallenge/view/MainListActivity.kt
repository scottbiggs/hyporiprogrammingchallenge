package com.sleepfuriously.hyporiprogrammingchallenge.view

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.sleepfuriously.hyporiprogrammingchallenge.R

import com.sleepfuriously.hyporiprogrammingchallenge.model.SWMovie
import com.sleepfuriously.hyporiprogrammingchallenge.model.MovieRequester
import com.sleepfuriously.hyporiprogrammingchallenge.presenter.Presenter
import kotlinx.android.synthetic.main.activity_item_list.*
import kotlinx.android.synthetic.main.main_list.*

/**
 * An activity representing a list of Pings. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a [MovieDetailActivity] representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
class MainListActivity : AppCompatActivity() {

    //------------------------------
    //  constants
    //------------------------------

    @Suppress("PrivatePropertyName")
    private val TAG = "MainListActivity"

    //------------------------------
    //  widgets
    //------------------------------

    /** display for to users that app is waiting on something */
    private lateinit var mProgressBar: ProgressBar

    /** Adapter for our recyclerview */
    private lateinit var mMainListAdapter: MainRecyclerViewAdapter


    //------------------------------
    //  data
    //------------------------------

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private var mTwoPane: Boolean = false

    /** Will be false as long as no movies have been loaded */
    private var mMoviesFound = false


    //------------------------------
    //  life-cycle functions
    //------------------------------

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_list)

        setSupportActionBar(toolbar)
        toolbar.title = title

        // todo: do something with this or remove it
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        if (item_detail_container != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true
        }

        // a couple of widgets
        mProgressBar = findViewById(R.id.progress_bar)
    }


    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        Log.d(TAG, "onRestoreInstanceState()")
    }

    override fun onResume() {
        super.onResume()

        // keep trying until we actually find something (user may have left
        // the app to turn on internet, so try again once focus is regained).
        if (!mMoviesFound) {
            getMovies()
        }
    }


    override fun onDestroy() {
        if (!mMoviesFound) {
            MovieRequester.cancelAllDataRequests()
        }
        super.onDestroy()
    }


    //------------------------------
    //  functions
    //------------------------------

    private fun setupRecyclerView(recyclerView: RecyclerView, movieList: List<SWMovie>) {
        mMainListAdapter = MainRecyclerViewAdapter(this, movieList, mTwoPane)
        recyclerView.adapter = mMainListAdapter
    }

    /**
     * The details of getting a movie list and acting on the results
     * are kept here.
     */
    private fun getMovies() {
        if (mMoviesFound) {
            return  // already done
        }

        turnOnWaitingUI()
        Presenter.requestMovieList(applicationContext) { movies ->
            turnOffWaitingUI()
            if (movies != null) {
                setupRecyclerView(item_list, movies)
                mMoviesFound = true
            }
            else {
                Snackbar.make(item_list, R.string.internet_probs, Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
            }
        }

    }


    /**
     * preconditions:
     *      mProgressBar     setup and ready to go
     */
    public fun turnOnWaitingUI() {
        mProgressBar.visibility = View.VISIBLE
    }

    /**
     * preconditions:
     *      mProgressBar     setup and ready to go
     */
    public fun turnOffWaitingUI() {
        mProgressBar.visibility = View.GONE
    }


}
