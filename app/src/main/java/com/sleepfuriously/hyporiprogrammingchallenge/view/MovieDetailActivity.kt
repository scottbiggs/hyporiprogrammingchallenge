package com.sleepfuriously.hyporiprogrammingchallenge.view

import android.content.Intent
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import com.sleepfuriously.hyporiprogrammingchallenge.R
import kotlinx.android.synthetic.main.activity_item_detail.*

/**
 * An activity representing a single movie detail screen. This
 * activity is only used on narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a [MainListActivity].
 */
class MovieDetailActivity : AppCompatActivity() {

    //-----------------------
    //  constants
    //-----------------------

    //-----------------------
    //  data
    //-----------------------

    /** display for to users that app is waiting on something */
    private lateinit var mProgressBar: ProgressBar

    //-----------------------
    //  functions
    //-----------------------

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_detail)
        setSupportActionBar(detail_toolbar)

        // todo: do something with this or remove it
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own detail action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        // Show the Up button in the action bar.
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        mProgressBar = findViewById(R.id.progress_bar)

        // savedInstanceState is non-null when there is fragment state
        // saved from previous configurations of this activity
        // (e.g. when rotating the screen from portrait to landscape).
        // In this case, the fragment will automatically be re-added
        // to its container so we don't need to manually add it.
        // For more information, see the Fragments API guide at:
        //
        // http://developer.android.com/guide/components/fragments.html
        //
        if (savedInstanceState == null) {

            val movieUrl = intent.getStringExtra(MovieDetailFragment.ARG_MOVIE_URL)

            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            val bundle = Bundle()
            bundle.putString(MovieDetailFragment.ARG_MOVIE_URL, movieUrl)

            val movieDetailFragment = MovieDetailFragment()
            movieDetailFragment.arguments = bundle

            supportFragmentManager.beginTransaction()
                .add(R.id.item_detail_container, movieDetailFragment)
                .commit()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem) =
        when (item.itemId) {
            android.R.id.home -> {
                // This ID represents the Home or Up button. In the case of this
                // activity, the Up button is shown. For
                // more details, see the Navigation pattern on Android Design:
                //
                // http://developer.android.com/design/patterns/navigation.html#up-vs-back

                navigateUpTo(Intent(this, MainListActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
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
