/*
 * Copyright (c) 2019. SCODEID
 */

package com.scodeid.scholarshipexpertscodeidev2019.homeFirstView


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
import com.scodeid.scholarshipexpertscodeidev2019.adapter.MoviesTvShowApiAdapter
import com.scodeid.scholarshipexpertscodeidev2019.database.ContractDatabase.MovieColumns.CONTENT_URI_TV
import com.scodeid.scholarshipexpertscodeidev2019.database.ContractDatabase.MovieColumns.POSTER
import com.scodeid.scholarshipexpertscodeidev2019.database.ContractDatabase.MovieColumns.TITLE
import com.scodeid.scholarshipexpertscodeidev2019.database.ContractDatabase.MovieColumns.VOTE_AVERAGE
import com.scodeid.scholarshipexpertscodeidev2019.model.MoviesTvShowApiData
import com.scodeid.scholarshipexpertscodeidev2019.view.MovieTvShowViewModel
import kotlinx.android.synthetic.main.fragment_movies_tv_show_recycler.*

class MoviesTvWapiHomeFragment : androidx.fragment.app.Fragment() {

    companion object {

        val TAG_LOG: String = MoviesTvWapiHomeFragment::class.java.simpleName
        var movieTvShowViewModel = MovieTvShowViewModel()
        val adapter = MoviesTvShowApiAdapter(ArrayList())

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
            val values = ContentValues()

            values.put(TITLE, title)
            values.put(VOTE_AVERAGE, voteAverage)
            values.put(POSTER, poster)

            context.contentResolver.insert(CONTENT_URI_TV, values)
            Toast.makeText(
                context,
                context.resources.getString(R.string.toast_sql_lite_insert_success),
                Toast.LENGTH_SHORT
            )
                .show()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movies_tv_show_recycler, container, false)
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
        if (adapter.itemCount == 0) {
            Log.d(TAG_LOG, "adapter tv_show fragment count is 0 , try request api [arrayList.TV_SHOW]")
            movieTvShowViewModel.setMovieTvShow(resources.getString(R.string.app_language), context)
        } else {
            // after else on check arrayList.isEmpty()
            Log.d(TAG_LOG, "adapter tv_show fragment is already have item , didn't try request api [arrayList.TV_SHOW]")
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
