package com.sleepfuriously.hyporiprogrammingchallenge.view

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.sleepfuriously.hyporiprogrammingchallenge.R

import com.sleepfuriously.hyporiprogrammingchallenge.dummy.DummyContent
import com.sleepfuriously.hyporiprogrammingchallenge.model.Movie
import com.sleepfuriously.hyporiprogrammingchallenge.presenter.Presenter
import kotlinx.android.synthetic.main.activity_item_list.*
import kotlinx.android.synthetic.main.item_list.*

/**
 * An activity representing a list of Pings. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a [ItemDetailActivity] representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
class MainListActivity : AppCompatActivity() {

    //------------------------------
    //  constants
    //------------------------------

    //------------------------------
    //  widgets
    //------------------------------

    /** debug messages go here */
    private lateinit var debugTv: TextView

    /** display for to users that app is waiting on something */
    private lateinit var progressBar: ProgressBar


    //------------------------------
    //  data
    //------------------------------

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private var twoPane: Boolean = false


    //------------------------------
    //  functions
    //------------------------------

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_list)

        setSupportActionBar(toolbar)
        toolbar.title = title

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        if (item_detail_container != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            twoPane = true
        }

        // a couple of widgets
        debugTv = findViewById(R.id.debug_tv)
        progressBar = findViewById(R.id.progress_bar)

        setupRecyclerView(item_list)


        // Request some movies
        turnOnWaitingUI()
        Presenter.requestMovieList(applicationContext) { movies ->
            // display this text once the movies happen
            debugTv.text = "I promise to display ${movies?.size} movies next time"

            turnOffWaitingUI()
            if (movies != null) {
                fillMainRecyclerView(movies)
            }
            else {
                Snackbar.make(item_list, R.string.internet_probs, Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
            }
        }

    }


    private fun setupRecyclerView(recyclerView: RecyclerView) {
        recyclerView.adapter =
            MainRecyclerViewAdapter(
                this,
                DummyContent.ITEMS,
                twoPane
            )
    }

    /**
     * preconditions:
     *      progressBar     setup and ready to go
     */
    private fun turnOnWaitingUI() {
        progressBar.visibility = View.VISIBLE
    }

    /**
     * preconditions:
     *      progressBar     setup and ready to go
     */
    private fun turnOffWaitingUI() {
        progressBar.visibility = View.GONE
    }


    private fun fillMainRecyclerView(movieList: List<Movie>) {
        // todo
    }

}
