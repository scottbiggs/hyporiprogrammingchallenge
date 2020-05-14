package com.sleepfuriously.hyporiprogrammingchallenge.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sleepfuriously.hyporiprogrammingchallenge.R
import com.sleepfuriously.hyporiprogrammingchallenge.model.SWMovie
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


    //-----------------------
    //  functions
    //-----------------------

    init {
        onClickListener = View.OnClickListener { v ->

            val movie = v.tag as SWMovie

            if (mTwoPane) {
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
            } else {
                val intent = Intent(v.context, MovieDetailActivity::class.java).apply {
                    putExtra(MovieDetailFragment.MOVIE_URL_KEY, movie.url)
                }
                v.context.startActivity(intent)
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.main_list_content, parent, false)
        return ViewHolder(view)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movieData = mMovieList[position]

        holder.titleTv.text = movieData.title
        holder.crawlTv.text = movieData.openingCrawl

        with(holder.itemView) {
            tag = movieData
            setOnClickListener(onClickListener)
        }
    }


    override fun getItemCount() = mMovieList.size


    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    //  classes
    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    inner class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val titleTv: TextView = v.title_tv
        val crawlTv: TextView = v.crawl_tv
    }

}

