package com.sleepfuriously.hyporiprogrammingchallenge.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.sleepfuriously.hyporiprogrammingchallenge.R
import com.sleepfuriously.hyporiprogrammingchallenge.model.SWMovie
import com.sleepfuriously.hyporiprogrammingchallenge.view.MovieDetailActivity.Companion.MOVIE_TITLE_KEY
import kotlinx.android.synthetic.main.main_list_content.view.*

/**
 * Adapter for the primary list of this app.
 */
class MainRecyclerViewAdapter(
    private val mParentActivity: MainListActivity,

    /** Holds the data to display */
    private val mMovieList: List<SWMovie>,
//    private val values: List<DummyContent.DummyItem>,

    private val mTwoPane: Boolean
    ) : RecyclerView.Adapter<MainRecyclerViewAdapter.ViewHolder>() {


    //-----------------------
    //  constants
    //-----------------------

    @Suppress("PrivatePropertyName", "unused")
    private val TAG = "MainRecyclerViewAdapter"

    //-----------------------
    //  data
    //-----------------------

    /** click listener for each item in the main list */
    private val onClickListener: View.OnClickListener

    /** The currently selected item in the RV--needed for highlighting. */
    private var mSelected = -1

    /** Normal background color for an Album list item  */
    private var mNormalBackground = -1

    /** The color to use for an Album list item that is currently selected  */
    private var mHighlightBackground = -1


    //-----------------------
    //  functions
    //-----------------------

    init {
        // first, setup the click listener
        onClickListener = View.OnClickListener { v ->

            val pos = v.tag as Int
            val movie = mMovieList[pos]

            if (mTwoPane) {

                // highlight the current button
                // redraw the selection
                notifyItemChanged(mSelected)
                notifyItemChanged(pos)
                mSelected = pos


                val fragment = MovieDetailFragment()
                    .apply {
                        arguments = Bundle().apply {
                            putString(MovieDetailFragment.MOVIE_URL_KEY, movie.url)
                        }
                    }
                mParentActivity.supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.item_detail_container, fragment)
                    .commit()
            }

            else {
                val intent = Intent(v.context, MovieDetailActivity::class.java).apply {
                    putExtra(MovieDetailFragment.MOVIE_URL_KEY, movie.url)
                    putExtra(MOVIE_TITLE_KEY, movie.title)
                }
                v.context.startActivity(intent)
            }
        }


        // Calculate the colors to avoid slowing down onBindViewHolder
        mHighlightBackground = mParentActivity.resources.getColor(R.color.highlightBackgroundColor)

        // the default background color of a cardview is a little trickier
//        val cardView: CardView = mParentActivity.findViewById(R.id.cardview_cv)
//        mNormalBackground = cardView.cardBackgroundColor.defaultColor
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.main_list_content, parent, false)

        // may need to set the normal background color
        if (mNormalBackground == -1) {
            val cardView: CardView = view.findViewById(R.id.cardview_cv)
            mNormalBackground = cardView.cardBackgroundColor.defaultColor
        }

        return ViewHolder(view)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movieData = mMovieList[position]

        holder.titleTv.text = movieData.title
        holder.crawlTv.text = movieData.openingCrawl

        with(holder.itemView) {
            tag = position
            setOnClickListener(onClickListener)
        }

        // Possibly highlight this as a selected item
        if (position == mSelected) {
            holder.cardView.setBackgroundColor(mHighlightBackground)
        } else {
            holder.cardView.setBackgroundColor(mNormalBackground)
        }

    }


    override fun getItemCount() = mMovieList.size


    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    //  classes
    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    inner class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val cardView: CardView = v.cardview_cv
        val titleTv: TextView = v.title_tv
        val crawlTv: TextView = v.crawl_tv
    }

}

