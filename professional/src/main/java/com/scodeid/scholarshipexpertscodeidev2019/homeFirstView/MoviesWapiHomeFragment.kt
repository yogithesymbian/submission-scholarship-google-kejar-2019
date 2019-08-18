/*
 * Copyright (c) 2019. SCODEID
 */

@file:Suppress("DEPRECATION")

package com.scodeid.scholarshipexpertscodeidev2019.homeFirstView

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.os.Bundle
import android.provider.BaseColumns._ID
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isEmpty
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.scodeid.scholarshipexpertscodeidev2019.R
import com.scodeid.scholarshipexpertscodeidev2019.adapter.MoviesApiAdapter
import com.scodeid.scholarshipexpertscodeidev2019.model.MovieTabColorModel
import com.scodeid.scholarshipexpertscodeidev2019.model.MoviesApiData
import com.scodeid.scholarshipexpertscodeidev2019.view.MovieViewModel
import com.scodeid.yomoviecommon.database.ContractDatabase.MovieColumns.CONTENT_URI_MOVIE
import com.scodeid.yomoviecommon.database.ContractDatabase.MovieColumns.DESCRIPTION
import com.scodeid.yomoviecommon.database.ContractDatabase.MovieColumns.POSTER
import com.scodeid.yomoviecommon.database.ContractDatabase.MovieColumns.RELEASE
import com.scodeid.yomoviecommon.database.ContractDatabase.MovieColumns.TITLE
import com.scodeid.yomoviecommon.utils.debuggingMyScode
import kotlinx.android.synthetic.main.fragment_movies_home_recycler.*
import java.io.IOException


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

    companion object {

        val TAG_LOG: String = MoviesWapiHomeFragment::class.java.simpleName
        var movieViewModel = MovieViewModel()
        val adapter = MoviesApiAdapter(ArrayList())
        const val KEY = "keyPojoSubTab"
//        private lateinit var  movieRoomView : MovieRoomView
//        val adapter = MoviesApiAdapter(context,ArrayList())

        fun newInstanceData(pojoSubTab: MovieTabColorModel): MoviesWapiHomeFragment {
            val fragment = MoviesWapiHomeFragment()
            val args = Bundle()
            args.putParcelable(KEY, pojoSubTab)
            fragment.arguments = args
            return fragment
        }

        // store to database
        fun initFavoriteParam(
            id: Int, release: String, title: String, description: String, poster: String, context: Context,
            bar: (id: Int, release: String, title: String, description: String, poster: String, context: Context) -> Unit
        ) {
            bar(id, release, title, description, poster, context)
        }

        @SuppressLint("RestrictedApi")
        fun insertFavoriteMovie(
            id: Int,
            release: String?,
            title: String,
            description: String,
            poster: String,
            context: Context
        ) {

            println(
                "\n\t $id" +
                        "\n\t $release" +
                        "\n\t $title" +
                        "\n\t $description" +
                        "\n\t $poster" +
                        "\n\t $context"
            )

//            val movieRoomModel = MovieRoomModel(
//                id,
//                release,
//                title,
//                description,
//                poster)
//            movieRoomView.insert(movieRoomModel)

            val values = ContentValues()
            values.put(_ID, id)
            values.put(TITLE, title)
            values.put(RELEASE, release)
            values.put(DESCRIPTION, description)
            values.put(POSTER, poster)

            try {
                context.contentResolver.insert(CONTENT_URI_MOVIE, values)
                Toast.makeText(
                    context,
                    context.resources.getString(R.string.toast_sql_lite_insert_success),
                    Toast.LENGTH_SHORT
                )
                    .show()
            } catch (e: IOException) {
                Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT)
                    .show()
            }
        }
        // end of store to database
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


    private fun movieDataHandle() {

        adapter.notifyDataSetChanged()

        recycler_view_home.setHasFixedSize(true)
        recycler_view_home.layoutManager = LinearLayoutManager(context)

        recycler_view_home.adapter = adapter

        movieViewModel = ViewModelProviders.of(this).get(MovieViewModel::class.java)

        movieViewModel.getMovies().observe(this, getMovie)

        if (recycler_view_home.isEmpty()) // i didn't make state just use isEmpty for simplify that mean data ever load on else in model
        {
            debuggingMyScode(
                TAG_LOG,
                "recycler adapter movies isEmpty , try request api [arrayList.MOVIE]"
            )
            movieViewModel.setMovie(resources.getString(R.string.app_language), context)

        } else {
            debuggingMyScode(
                TAG_LOG,
                "recycler adapter movies is already have item , didn't try request api [arrayList.MOVIE]"
            )
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        debuggingMyScode(TAG_LOG, "onViewCreated is running")
        val pojoSubTab: MovieTabColorModel? = arguments?.getParcelable("" + KEY)
        pojoSubTab?.let {

            val color0 = "R.color.color0"
            val color1 = "R.color.color1"
            val color2 = "R.color.color2"
            val color3 = "R.color.color3"
            when {
                it.colorString == color0 -> view.setBackgroundColor(
                    ResourcesCompat.getColor(
                        resources,
                        R.color.white,
                        null
                    )
                )
                it.colorString == color1 -> view.setBackgroundColor(
                    ResourcesCompat.getColor(
                        resources,
                        R.color.colorBarTabGreen,
                        null
                    )
                )
                it.colorString == color2 -> view.setBackgroundColor(
                    ResourcesCompat.getColor(
                        resources,
                        R.color.colorBarTabPink,
                        null
                    )
                )
                it.colorString == color3 -> view.setBackgroundColor(
                    ResourcesCompat.getColor(
                        resources,
                        R.color.colorBarTabPurple,
                        null
                    )
                )
                else -> view.setBackgroundColor(ResourcesCompat.getColor(resources, R.color.white, null))
            }
        }
//        movieRoomView = ViewModelProviders.of(this).get(MovieRoomView::class.java)
        movieDataHandle() //load data movies
    }

}