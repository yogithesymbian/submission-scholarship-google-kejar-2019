/*
 * Copyright (c) 2019. SCODEID
 */

package com.scodeid.scholarshipexpertscodeidev2019.submission.submission3

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.scodeid.scholarshipexpertscodeidev2019.R
import com.scodeid.scholarshipexpertscodeidev2019.submission.model.MovieTabColorModel
import com.scodeid.scholarshipexpertscodeidev2019.submission.submission3.adapter.MoviesApiAdapter
import com.scodeid.scholarshipexpertscodeidev2019.submission.submission3.api.ApiEndPoint
import com.scodeid.scholarshipexpertscodeidev2019.submission.submission3.model.MoviesApiData
import kotlinx.android.synthetic.main.fragment_movies_home_recycler.*
import org.json.JSONObject

/**
 * @author
 * Created by scode on 13,July,2019
 * Yogi Arif Widodo
 * www.dicoding.com/users/297307
 * www.github.com/yogithesymbian
 * SCODEID company,
 * Indonesia, East Borneo.
 * ==============================================================
 * Android Studio 3.3.2
 * Build # AI-182.5107.16.33.5314842, built on February 15, 2019
 * JRE: 1.8.0_152-release-1248-b01 amd64
 * JVM: OpenJDK 64-Bit Server VM by JetBrains s.r.o
 * Linux 4.19.0-kali5-amd64
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

        var arrayList = ArrayList<MoviesApiData>()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.d(TAG_LOG,"onAttachFragment")
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
        val view = inflater.inflate(R.layout.fragment_movies_home_recycler, container, false)
        Log.d(TAG_LOG,"onCreateView")

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log.d(TAG_LOG,"onActivityCreated")
        frame_progress.visibility = View.VISIBLE

        /**
         * GET . READ API MOVIES
         */

        // avoid skipped 34 frame of my code
        val doInBackGroundMovie = 3000 //in just start
        Handler().postDelayed(object : Runnable {
            override fun run() {

                this.finish()
                Log.d(TAG_LOG, "DONE ... for get and load data in background")
            }

            private fun finish() {
                Log.d(TAG_LOG, "Loading ... for get and load data in background")

                AndroidNetworking.get(ApiEndPoint.SERVER_MOVIES)
                    .addPathParameter("API_KEY", ApiEndPoint.API_KEY_V3_AUTH)
                    .addPathParameter("LANGUAGE", resources.getString(R.string.app_language))
                    .setPriority(Priority.LOW)
                    .build()
                    .getAsJSONObject(object : JSONObjectRequestListener {
                        override fun onResponse(response: JSONObject) {
                            arrayList.clear() // for clear while , for !duplicate
                            val jsonArray = response.optJSONArray("results")

                            if (jsonArray?.length() == 0)
                            {
                                Toast.makeText(context,"result data is empty, Add the data first", Toast.LENGTH_LONG).show()
                            }
                            /**
                             * for closing progress dialog ini
                             * looping for jsonArray to save data in , then checking has it ? with if statements
                             */
                            for (i in 0 until jsonArray.length())
                            {
                                val jsonObject = jsonArray.optJSONObject(i)

                                arrayList.add(
                                    MoviesApiData(
                                        jsonObject.getInt("vote_count"),
                                        jsonObject.getInt("id"),
                                        jsonObject.getBoolean("video"),
                                        jsonObject.getInt("vote_average"),
                                        jsonObject.getString("title"),
                                        jsonObject.getInt("popularity"),
                                        jsonObject.getString("poster_path"),
                                        jsonObject.getString("original_language"),
                                        jsonObject.getString("original_title"),
                                        arrayListOf(jsonObject.getString("genre_ids")),
                                        jsonObject.getString("backdrop_path"),
                                        jsonObject.getBoolean("adult"),
                                        jsonObject.getString("overview"),
                                        jsonObject.getString("release_date")
                                    )
                                )

                                if (jsonArray.length() - 1 == i)
                                {
                                    val adapter = context?.let { MoviesApiAdapter(it, arrayList) }
                                    adapter?.notifyDataSetChanged()
                                    recycler_view_home?.adapter = adapter
                                    frame_progress.visibility = View.GONE
                                }
                            }
                        }

                        override fun onError(anError: ANError?) {
                            Log.d("ON_ERROR",anError?.errorDetail.toString())
                            Toast.makeText(context,"Connection Failure", Toast.LENGTH_LONG).show()
                        }
                    })
            }
        }, doInBackGroundMovie.toLong())

        /**
         * END OF GET . READ API MOVIES
         */


    }


    override fun onStart() {
        super.onStart()
        Log.d(TAG_LOG,"onStart")


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

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
    }

    override fun onResume() {
        super.onResume()
        Log.d(tag,"onResume Tabs")
        recycler_view_home.setHasFixedSize(true)
        recycler_view_home.layoutManager = LinearLayoutManager(context)



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

        arrayList.clear()  //clear when out
    }


}