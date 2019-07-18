/*
 * Copyright (c) 2019. SCODEID
 */

package com.scodeid.scholarshipexpertscodeidev2019.submission.submission3.view

import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.scodeid.scholarshipexpertscodeidev2019.submission.notification.NoInternetConnActivity
import com.scodeid.scholarshipexpertscodeidev2019.submission.submission3.api.ApiEndPoint
import com.scodeid.scholarshipexpertscodeidev2019.submission.submission3.model.MoviesTvShowApiData
import org.json.JSONObject

/**
 * @author
 * Created by scode on 17,July,2019
 * Yogi Arif Widodo
 * www.dicoding.com/users/297307
 * www.github.com/yogithesymbian
 * SCODEID company,
 * Indonesia, East Borneo.
 * ==============================================================
 * Android Studio 3.4.2
 * Build #AI-183.6156.11.34.5692245, built on June 27, 2019
 * JRE: 1.8.0_152-release-1343-b16-5323222 amd64
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

class MovieTvShowViewModel : ViewModel() {

    private val listMovieTvShowMutableLiveData = MutableLiveData<ArrayList<MoviesTvShowApiData>>()

    companion object {
        val arrayListMovieTvShow = ArrayList<MoviesTvShowApiData>()
        @JvmStatic
        val TAG_LOG: String = MovieTvShowViewModel::class.java.simpleName
    }

    fun setMovieTvShow(lang: String, context: Context?) {
        if (arrayListMovieTvShow.isEmpty())
        {
            Log.d(TAG_LOG, "Language use : $lang")
            AndroidNetworking.get(ApiEndPoint.SERVER_TV_SHOW)
                .addPathParameter("API_KEY", ApiEndPoint.API_KEY_V3_AUTH)
                .addPathParameter("LANGUAGE", lang)
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONObject(object : JSONObjectRequestListener {
                    override fun onResponse(response: JSONObject) {
                        val jsonArray = response.optJSONArray("results")

                        if (jsonArray?.length() == 0) {
                            Toast.makeText(context, "result data is empty, Add the data first", Toast.LENGTH_LONG).show()
                        }

                        for (i in 0 until jsonArray.length()) {
                            val jsonObject = jsonArray.optJSONObject(i)

                            arrayListMovieTvShow.add(
                                MoviesTvShowApiData(
                                    jsonObject.getString("original_name"),
                                    arrayListOf(jsonObject.getString("genre_ids")),
                                    jsonObject.getString("name"),
                                    jsonObject.getInt("popularity"),
                                    arrayListOf(jsonObject.getString("origin_country")),
                                    jsonObject.getInt("vote_count"),
                                    jsonObject.getString("first_air_date"),
                                    jsonObject.getString("backdrop_path"),
                                    jsonObject.getString("original_language"),
                                    jsonObject.getInt("id"),
                                    jsonObject.getInt("vote_average"),
                                    jsonObject.getString("overview"),
                                    jsonObject.getString("poster_path")
                                )
                            )

                            if (jsonArray.length() - 1 == i) {
                                // post the data value
                                listMovieTvShowMutableLiveData.postValue(arrayListMovieTvShow)

                            }
                        }
                    }

                    override fun onError(anError: ANError?) {

                        Log.d("ON_ERROR", anError?.errorDetail.toString())
                        val intent = Intent(context, NoInternetConnActivity::class.java)
                        context?.startActivity(intent)


                        // received error from server
                        if (anError?.errorCode != 0) {

                            Log.d(
                                TAG_LOG,
                                "onError errorCode : " + anError?.errorCode
                            ) // error.getErrorCode() - the error code from server
                            Log.d(
                                TAG_LOG,
                                "onError errorBody : " + anError?.errorBody
                            ) // error.getErrorBody() - the error body from server
                            Log.d(
                                TAG_LOG,
                                "onError errorDetail : " + anError?.errorDetail
                            ) // error.getErrorDetail() - just an error detail

                        } else {
                            // error.getErrorDetail() : connectionError, parseError, requestCancelledError
                            Log.d(TAG_LOG, "onError errorDetail : " + anError.errorDetail)
                        }
                    }
                })
        }
        // request API
    }

    fun getMoviesTvShow(): LiveData<ArrayList<MoviesTvShowApiData>> {
        return listMovieTvShowMutableLiveData
    }


}