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
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isEmpty
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.scodeid.scholarshipexpertscodeidev2019.R
import com.scodeid.scholarshipexpertscodeidev2019.submission.model.MovieTabColorModel
import com.scodeid.scholarshipexpertscodeidev2019.submission.submission3.adapter.MoviesApiAdapter
import com.scodeid.scholarshipexpertscodeidev2019.submission.submission3.model.MoviesApiData
import com.scodeid.scholarshipexpertscodeidev2019.submission.submission3.view.MovieViewModel
import kotlinx.android.synthetic.main.fragment_movies_home_recycler.*


/**
 * @author
 * Created by scode on 13,July,2019
 * Yogi Arif Widodo
 * www.dicoding.com/users/297307
 * www.github.com/yogithesymbian
 * SCODEID company,
 * Indonesia, East Borneo.
 * ==============================================================
Android Studio 3.4.2
Build #AI-183.6156.11.34.5692245, built on June 27, 2019
JRE: 1.8.0_152-release-1343-b16-5323222 amd64
JVM: OpenJDK 64-Bit Server VM by JetBrains s.r.o
Linux 4.19.0-kali5-amd64
 * ==============================================================
 *              _               _         _               _____
 *   ___ _   _| |__  _ __ ___ (_)___ ___(_) ___  _ __   |___ /
 * / __| | | | '_ \| '_ ` _ \| / __/ __| |/ _ \| '_ \    |_ \
 * \__ \ |_| | |_) | | | | | | \__ \__ \ | (_) | | | |  ___) |
 * |___/\__,_|_.__/|_| |_| |_|_|___/___/_|\___/|_| |_| |____/
 *
 *
 */

class MoviesWapiHomeFragment : androidx.fragment.app.Fragment() {


    private var movieViewModel  = MovieViewModel()

    val adapter = MoviesApiAdapter(ArrayList())


    companion object{
        @JvmStatic
        val TAG_LOG: String = MoviesWapiHomeFragment::class.java.simpleName
        @JvmStatic
        val KEY = "keyPojoSubTab"
        fun newInstanceData(pojoSubTab: MovieTabColorModel) : MoviesWapiHomeFragment
        {
            val fragment = MoviesWapiHomeFragment()
            val args = Bundle()
            args.putParcelable(KEY, pojoSubTab)
            fragment.arguments = args
            return fragment
        }

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.d(TAG_LOG,"onAttach")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG_LOG,"onCreate")

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movies_home_recycler, container, false)
    }
    private val getMovie =
        Observer<ArrayList<MoviesApiData>> { movieItems ->
            if (movieItems != null) {
                adapter.setData(movieItems)
                frame_progress.visibility = View.GONE
            }
        }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log.d(TAG_LOG,"onActivityCreated")

    }


    private fun movieDataHandle() {

        adapter.notifyDataSetChanged()

        recycler_view_home.setHasFixedSize(true)
        recycler_view_home.layoutManager = LinearLayoutManager(context)

        recycler_view_home.adapter = adapter

        movieViewModel = ViewModelProviders.of(this).get(MovieViewModel::class.java)

        movieViewModel.getMovies().observe(this, getMovie)

        if (recycler_view_home.isEmpty()) // i didn't make state just use isEmpty for simplify that mean data ever load on else in model
        {
            Log.d(TAG_LOG,"recycler adapter movies isEmpty , try request api [arrayList.MOVIE]")
            movieViewModel.setMovie(resources.getString(R.string.app_language), context)
        }
        else
        {
            Log.d(TAG_LOG,"recycler adapter movies is already have item , didn't try request api [arrayList.MOVIE]")
        }
    }


    override fun onStart() {
        super.onStart()
        Log.d(TAG_LOG,"onStart")

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        Log.d(TAG_LOG,"onViewCreated is running")
        val pojoSubTab: MovieTabColorModel? = arguments?.getParcelable(""+ KEY)
        pojoSubTab?.let {

            val color0 = "R.color.color0"
            val color1 = "R.color.color1"
            val color2 = "R.color.color2"
            val color3 = "R.color.color3"
            when {
                it.colorString == color0 -> view.setBackgroundColor(ResourcesCompat.getColor(resources, R.color.white, null))
                it.colorString == color1 -> view.setBackgroundColor(ResourcesCompat.getColor(resources, R.color.colorBarTabGreen, null))
                it.colorString == color2 -> view.setBackgroundColor(ResourcesCompat.getColor(resources, R.color.colorBarTabPink, null))
                it.colorString == color3 -> view.setBackgroundColor(ResourcesCompat.getColor(resources, R.color.colorBarTabPurple, null))
                else -> view.setBackgroundColor(ResourcesCompat.getColor(resources, R.color.white, null))
            }
        }

        movieDataHandle() //load data movies
    }

    override fun onResume() {
        super.onResume()
        Log.d(tag,"onResume Tabs")

    }
    override fun onPause() {
        super.onPause()
        Log.d(TAG_LOG,"onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG_LOG,"onStop")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d(TAG_LOG,"onDestroyView")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG_LOG,"on Destroy")
    }

    override fun onDetach() {
        super.onDetach()
        Log.d(TAG_LOG,"onDetach")
    }


}