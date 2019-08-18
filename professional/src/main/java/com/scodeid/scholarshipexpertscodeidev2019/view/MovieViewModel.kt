/*
 * Copyright (c) 2019. SCODEID
 */

package com.scodeid.scholarshipexpertscodeidev2019.view

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.scodeid.scholarshipexpertscodeidev2019.R
import com.scodeid.scholarshipexpertscodeidev2019.api.ApiEndPoint
import com.scodeid.scholarshipexpertscodeidev2019.model.MoviesApiData
import com.scodeid.scholarshipexpertscodeidev2019.notification.NoInternetConnActivity
import com.scodeid.yomoviecommon.utils.debuggingMyScode
import com.scodeid.yomoviecommon.utils.toastAllActivity
import org.json.JSONObject

/**
 * @author
 * Created by scode on 16,July,2019
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
class MovieViewModel : ViewModel() {
    //    RELEASE_MOVIE
    private val listMovieMutableLiveData = MutableLiveData<ArrayList<MoviesApiData>>()

    companion object {
        val arrayListMovie = ArrayList<MoviesApiData>()
        val TAG_LOG: String = MovieViewModel::class.java.simpleName
    }

    fun setMovie(lang: String, context: Context?) {
        if (arrayListMovie.isEmpty()) {
            debuggingMyScode(
                TAG_LOG,
                "arrayList MOVIE is Empty, request api is in background"
            )
            debuggingMyScode(TAG_LOG, "Language use : $lang")
            AndroidNetworking.get(ApiEndPoint.SERVER_MOVIES)
                .addPathParameter("API_KEY", ApiEndPoint.API_KEY_V3_AUTH)
                .addPathParameter("LANGUAGE", lang)
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONObject(object : JSONObjectRequestListener {
                    override fun onResponse(response: JSONObject) {
                        arrayListMovie.clear() // for clear while , for !duplicate //already clear on adapter set data like @deprecated my code :V :D
                        val jsonArray = response.optJSONArray("results")

                        if (jsonArray?.length() == 0) {
                            Toast.makeText(context, "result data is empty, Add the data first", Toast.LENGTH_LONG)
                                .show()
                        }
                        /**
                         * for closing progress dialog ini
                         * looping for jsonArray to save data in , then checking has it ? with if statements
                         */
                        for (i in 0 until jsonArray.length()) {
                            val jsonObject = jsonArray.optJSONObject(i)

                            arrayListMovie.add(
                                MoviesApiData(
                                    jsonObject.getInt("vote_count"),
                                    jsonObject.getInt("id"),
                                    jsonObject.getBoolean("video"),
                                    jsonObject.getInt("vote_average"),
                                    jsonObject.optString("title"),
                                    jsonObject.getInt("popularity"),
                                    jsonObject.optString("poster_path"),
                                    jsonObject.optString("original_language"),
                                    jsonObject.optString("original_title"),
                                    arrayListOf(jsonObject.getString("genre_ids")),
                                    jsonObject.optString("backdrop_path"),
                                    jsonObject.getBoolean("adult"),
                                    jsonObject.optString("overview"),
                                    jsonObject.optString("release_date")
                                )
                            )

                            if (jsonArray.length() - 1 == i) {
                                listMovieMutableLiveData.postValue(arrayListMovie)
                            }
                        }
                    }

                    override fun onError(anError: ANError?) {
                        val intent = Intent(context, NoInternetConnActivity::class.java)
                        debuggingMyScode("ON_ERROR", anError?.errorDetail.toString())
//                        val intent = Intent(context, NoInternetConnActivity::class.java)
//                        context?.startActivity(intent)


                        // received error from server
                        // later's this scope will have an intent for user knowledge about condition on getAnError { notification package }
                        if (anError?.errorCode != 0) {

                            debuggingMyScode(
                                TAG_LOG,
                                "onError errorCode : ${anError?.errorCode}"
                            ) // error.getErrorCode() - the error code from server
                            debuggingMyScode(
                                TAG_LOG,
                                "onError errorBody : ${anError?.errorBody}"
                            ) // error.getErrorBody() - the error body from server
                            debuggingMyScode(
                                TAG_LOG,
                                "onError errorDetail : ${anError?.errorDetail}"
                            ) // error.getErrorDetail() - just an error detail
                            if (anError?.errorCode == 422 && anError.errorBody == "{\"errors\":[\"query must be provided\"]}"){
                                // nothing IF USER FIRST CLICK SEARCH WILL REAL TIME ON REQUEST API IS NULL WORD/QUERY
                                // SO I MADE SPECIAL RESPONSE STRING FOR THIS AND NOT REDIRECT TO NO INTERNET CONNECTION
                            }
                            else if(anError?.errorCode == 503 && anError.errorBody == "be right back")
                                toastAllActivity(context!!,context.getString(R.string.error_503_request_api_toast))
                            else
                                context?.startActivity(intent)


                        } else {
                            // error.getErrorDetail() : connectionError, parseError, requestCancelledError
                            debuggingMyScode(
                                TAG_LOG,
                                "onError errorDetail : " + anError.errorDetail
                            )
                        }
                    }
                })
        }
        // request API
        else {
            debuggingMyScode(
                TAG_LOG,
                "arrayList is Not Empty , TRY request api is reject by arrayList.isEmpty"
            )
            listMovieMutableLiveData.postValue(arrayListMovie)
        }
        // didn't request API
    }

    fun getMovies(): LiveData<ArrayList<MoviesApiData>> {
        return listMovieMutableLiveData
    }

    fun searchMovies(lang: String, query: String, context: Context?) {

        AndroidNetworking.get(ApiEndPoint.SEARCH_MOVIES)
            .addPathParameter("API_KEY", ApiEndPoint.API_KEY_V3_AUTH)
            .addPathParameter("LANGUAGE", lang)
            .addPathParameter("QUERY_MOVIE_NAME", query)
            .setPriority(Priority.MEDIUM)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener {
                override fun onResponse(response: JSONObject) {
                    arrayListMovie.clear()
                    val jsonArray = response.optJSONArray("results")

                    if (jsonArray?.length() == 0) {
                        Toast.makeText(
                            context,
                            """${context?.getString(R.string.view_model_search_keyword)}$query ${context?.getString(R.string.view_model_search_info)}""".trimIndent(),
                            Toast.LENGTH_LONG
                        ).show()
                    }
                    for (i in 0 until jsonArray.length()) {
                        val jsonObject = jsonArray.optJSONObject(i)

                        arrayListMovie.add(
                            MoviesApiData(
                                jsonObject.getInt("vote_count"),
                                jsonObject.getInt("id"),
                                jsonObject.getBoolean("video"),
                                jsonObject.getInt("vote_average"),
                                jsonObject.optString("title"),
                                jsonObject.getInt("popularity"),
                                jsonObject.optString("poster_path"),
                                jsonObject.optString("original_language"),
                                jsonObject.optString("original_title"),
                                arrayListOf(jsonObject.getString("genre_ids")),
                                jsonObject.optString("backdrop_path"),
                                jsonObject.getBoolean("adult"),
                                jsonObject.optString("overview"),
                                jsonObject.optString("release_date")
                            )
                        )

                        if (jsonArray.length() - 1 == i) {
                            listMovieMutableLiveData.postValue(arrayListMovie)
                        }
                    }
                }

                override fun onError(anError: ANError?) {
                    val intent = Intent(context, NoInternetConnActivity::class.java)
                    debuggingMyScode("ON_ERROR", anError?.errorDetail.toString())
                    if (anError?.errorCode != 0) {

                        debuggingMyScode(
                            TAG_LOG,
                            "onError errorCode : ${anError?.errorCode}"
                        )
                        debuggingMyScode(
                            TAG_LOG,
                            "onError errorBody : ${anError?.errorBody}"
                        )
                        debuggingMyScode(
                            TAG_LOG,
                            "onError errorDetail : ${anError?.errorDetail}"
                        )
                        if (anError?.errorCode == 422 && anError.errorBody == "{\"errors\":[\"query must be provided\"]}"){
                            // nothing IF USER FIRST CLICK SEARCH WILL REAL TIME ON REQUEST API IS NULL WORD/QUERY
                            // SO I MADE SPECIAL RESPONSE STRING FOR THIS AND NOT REDIRECT TO NO INTERNET CONNECTION
                        }
                        else if(anError?.errorCode == 503 && anError.errorBody == "be right back")
                            toastAllActivity(context!!,context.getString(R.string.error_503_request_api_toast))
                        else
                            context?.startActivity(intent)

                    } else {
                        debuggingMyScode(
                            TAG_LOG,
                            "onError errorDetail : ${anError.errorDetail}"
                        )
                        context?.startActivity(intent)
                    }
                }
            })
    }
}