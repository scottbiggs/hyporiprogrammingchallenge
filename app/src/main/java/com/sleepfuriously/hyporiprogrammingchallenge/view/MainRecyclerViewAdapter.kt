package com.sleepfuriously.hyporiprogrammingchallenge.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sleepfuriously.hyporiprogrammingchallenge.R
import com.sleepfuriously.hyporiprogrammingchallenge.model.Movie
import kotlinx.android.synthetic.main.item_list_content.view.*

/**
 * Adapter for the primary list of this app.
 */
class MainRecyclerViewAdapter(
    private val mParentActivity: MainListActivity,

    /** Holds the data to display */
    private val mMovieList: List<Movie>,
//    private val values: List<DummyContent.DummyItem>,

    private val mTwoPane: Boolean
    ) : RecyclerView.Adapter<MainRecyclerViewAdapter.ViewHolder>() {


    //-----------------------
    //  constants
    //-----------------------

    private val TAG = "MainRecyclerViewAdapter"

    //-----------------------
    //  data
    //-----------------------


    /** todo */
    private val onClickListener: View.OnClickListener


    //-----------------------
    //  functions
    //-----------------------

    init {
        onClickListener = View.OnClickListener { v ->

            val movie = v.tag as Movie
//            val item = v.tag as DummyContent.DummyItem

            if (mTwoPane) {
                val fragment = ItemDetailFragment()
                    .apply {
                        arguments = Bundle().apply {
                            putString(ItemDetailFragment.ARG_ITEM_ID, movie.title)
                        }
                    }
                mParentActivity.supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.item_detail_container, fragment)
                    .commit()
            } else {
                val intent = Intent(v.context, ItemDetailActivity::class.java).apply {
                    putExtra(ItemDetailFragment.ARG_ITEM_ID, movie.title)
                }
                v.context.startActivity(intent)
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_list_content, parent, false)
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

