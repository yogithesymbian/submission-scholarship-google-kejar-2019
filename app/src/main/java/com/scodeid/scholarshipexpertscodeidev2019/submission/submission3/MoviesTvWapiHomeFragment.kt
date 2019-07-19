/*
 * Copyright (c) 2019. SCODEID
 */

package com.scodeid.scholarshipexpertscodeidev2019.submission.submission3


import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.scodeid.scholarshipexpertscodeidev2019.R
import com.scodeid.scholarshipexpertscodeidev2019.submission.submission3.adapter.MoviesTvShowApiAdapter
import com.scodeid.scholarshipexpertscodeidev2019.submission.submission3.model.MoviesTvShowApiData
import com.scodeid.scholarshipexpertscodeidev2019.submission.submission3.view.MovieTvShowViewModel
import kotlinx.android.synthetic.main.fragment_movies_tv_show.*

class MoviesTvWapiHomeFragment : androidx.fragment.app.Fragment() {

    private var movieTvShowViewModel = MovieTvShowViewModel()

    val adapter = MoviesTvShowApiAdapter(ArrayList())


    companion object {
        @JvmStatic
        var TAG_LOG: String = MoviesTvWapiHomeFragment::class.java.simpleName

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.d(TAG_LOG, "onAttach")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG_LOG, "onCreate")
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


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log.d(TAG_LOG, "onActivityCreated")

    }


    override fun onStart() {
        super.onStart()
        Log.d(TAG_LOG, "onStart")
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

    override fun onPause() {
        super.onPause()
        Log.d(TAG_LOG, "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG_LOG, "onStop")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d(TAG_LOG, "onDestroyView")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(tag, "on Destroy")
    }

    override fun onDetach() {
        super.onDetach()
        Log.d(TAG_LOG, "onDetach")
    }

}
