/*
 * Copyright (c) 2019. SCODEID
 */

package com.scodeid.scholarshipexpertscodeidev2019.submission.submission3


import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.scodeid.scholarshipexpertscodeidev2019.R
import com.scodeid.scholarshipexpertscodeidev2019.submission.submission3.adapter.MoviesTvShowApiAdapter
import com.scodeid.scholarshipexpertscodeidev2019.submission.submission3.model.MoviesTvShowApiData
import com.scodeid.scholarshipexpertscodeidev2019.submission.submission3.view.MovieTvShowViewModel
import com.scodeid.scholarshipexpertscodeidev2019.submission.submission4.database.ContractDatabase
import com.scodeid.scholarshipexpertscodeidev2019.submission.submission4.database.HelperDatabase
import kotlinx.android.synthetic.main.fragment_movies_tv_show.*

class MoviesTvWapiHomeFragment : androidx.fragment.app.Fragment() {

    private var movieTvShowViewModel = MovieTvShowViewModel()

    val adapter = MoviesTvShowApiAdapter(ArrayList())

    companion object {
        val TAG_LOG: String = MoviesTvWapiHomeFragment::class.java.simpleName

        // store to database
        fun initFavoriteTvParam(
            title: String, voteAverage: Int, poster: String, context: Context,
            bar: (title: String, voteAverage: Int, poster: String, context: Context) -> Unit
        ) {
            bar(title, voteAverage, poster, context)
        }

        // my function to pass into the other
        @SuppressLint("RestrictedApi")
        fun insertFavoriteTv(title: String, voteAverage: Int, poster: String, context: Context) {

            println(
                "\n\t $title" +
                        "\n\t $voteAverage" +
                        "\n\t $poster" +
                        "\n\t $context"
            )

            val helperDatabase = HelperDatabase(context)

            // Gets the data repository in write mode
            val db = helperDatabase.writableDatabase

            // Create a new map of values, where column names are the keys
            val values = ContentValues().apply {
                put(ContractDatabase.MovieColumns.TITLE, title)
                put(ContractDatabase.MovieColumns.VOTE_AVERAGE, voteAverage)
                put(ContractDatabase.MovieColumns.POSTER, poster)
            }

            val insert =  db?.insert(ContractDatabase.MovieColumns.TABLE_NAME_TV, null, values)
            if (insert != null) {
                if (insert > 0) {
                    Toast.makeText(context,context.getString(R.string.toast_sql_lite_insert_success), Toast.LENGTH_SHORT)
                        .show()
                }
                else {
                    Toast.makeText(context,context.getString(R.string.toast_sql_lite_insert_fail), Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
        // end of store to database
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movies_tv_show, container, false)
    }


    private val getMovieTvShow =
        Observer<ArrayList<MoviesTvShowApiData>> { movieItems ->
            if (movieItems != null) {
                adapter.setData(movieItems)
                frame_progress_tv_show.visibility = View.GONE
                card_tv_show.visibility = View.VISIBLE
            }
        }

    private fun movieTvShowHandle() {

        adapter.notifyDataSetChanged()

        recycler_view_tv_show.setHasFixedSize(true)
        recycler_view_tv_show.layoutManager = LinearLayoutManager(context)
        recycler_view_tv_show.adapter = adapter

        movieTvShowViewModel = ViewModelProviders.of(this).get(MovieTvShowViewModel::class.java)


        movieTvShowViewModel.getMoviesTvShow().observe(this, getMovieTvShow)
        if (adapter.itemCount == 0)
        {
            Log.d(TAG_LOG,"adapter tv_show fragment count is 0 , try request api [arrayList.TV_SHOW]")
            movieTvShowViewModel.setMovieTvShow(resources.getString(R.string.app_language), context)
        }
        else // for skip loading cause default is true on loading while back stack of fragment tv home and tv home_detail ,
        {
            // after else on check arrayList.isEmpty()
            Log.d(TAG_LOG,"adapter tv_show fragment is already have item , didn't try request api [arrayList.TV_SHOW]")
            frame_progress_tv_show.visibility = View.GONE
            card_tv_show.visibility = View.VISIBLE
        }

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG_LOG, "onViewCreated")

        movieTvShowHandle() // load data and set

    }

    override fun onResume() {
        super.onResume()
        Log.d(tag, "onResume Tabs")
        recycler_view_tv_show.adapter = adapter
    }
}
