/*
 * Copyright (c) 2019. SCODEID
 */

package com.scodeid.scholarshipexpertscodeidev2019.submission.submission3


import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.scodeid.scholarshipexpertscodeidev2019.R
import com.scodeid.scholarshipexpertscodeidev2019.submission.ItemClickRecyclerSupport
import com.scodeid.scholarshipexpertscodeidev2019.submission.submission3.adapter.MoviesTvShowApiAdapter
import com.scodeid.scholarshipexpertscodeidev2019.submission.submission3.api.ApiEndPoint
import com.scodeid.scholarshipexpertscodeidev2019.submission.submission3.model.MoviesTvShowApiData
import kotlinx.android.synthetic.main.fragment_movies_tv_show.*
import kotlinx.android.synthetic.main.fragment_movies_tv_show.view.*
import org.json.JSONObject

class MoviesTvWapiHomeFragment : Fragment() {

    companion object {
        var tagLog = "MoviesTvWapiHomeFragment"
        var arrayList = ArrayList<MoviesTvShowApiData>()
    }


    override fun onAttachFragment(childFragment: Fragment) {
        super.onAttachFragment(childFragment)
        Log.d(tagLog, "onAttachFragment")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(tagLog, "onCreate")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_movies_tv_show, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log.d(tagLog, "onActivityCreated")


        val doInBackGroundMovie = 2000 //in just start
        Handler().postDelayed(object : Runnable {
            override fun run() {
                frame_progress_tv_show.visibility = View.VISIBLE
                card_tv_show.visibility = View.GONE
                this.finish()

            }
            private fun finish() {

                AndroidNetworking.get(ApiEndPoint.SERVER_TV_SHOW)
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

                                if (jsonArray.length() - 1 == i)
                                {
                                    val adapter = context?.let { MoviesTvShowApiAdapter(it, arrayList) }
                                    adapter?.notifyDataSetChanged()
                                    recycler_view_tv_show?.adapter = adapter
                                    frame_progress_tv_show.visibility = View.GONE
                                    card_tv_show.visibility = View.VISIBLE
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
         * End Of Handling Recycler from choreographs /
         */

    }


    override fun onStart() {
        super.onStart()
        Log.d(tagLog, "onStart")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(tagLog, "onViewCreated")

        /**
         * Intent With Pojo so I create here for between FRAGMENT
         */
        ItemClickRecyclerSupport.addTo(view.recycler_view_tv_show).setOnItemClickListener(object : ItemClickRecyclerSupport.OnItemClickListener
        {
            override fun onItemClicked(recyclerView: RecyclerView, position: Int, v: View) {

                Log.d(tagLog,"Try Opening Tv Movie Detail" + arrayList[position].name)
                openingTvShowDetail(arrayList[position])
            }
        })
    }

    private fun openingTvShowDetail(moviesTvShowApiData: MoviesTvShowApiData) {
        // instance DetailCategoryFragment
        val mMoviesTvShowDetailFragment = MoviesTvWapiHomeDetailFragment()

        // tx tx data between the fragment using Bundle
        val mBundle = Bundle()

        mBundle.putParcelable(MoviesTvWapiHomeDetailFragment.extraTvDetails, moviesTvShowApiData)
        mMoviesTvShowDetailFragment.arguments = mBundle

        //manage the fragment manager in this fragment
        val mFragmentManager = fragmentManager

        //check for replace and go
        if (mFragmentManager != null)
        {
            // use FragTransaction
            val mFragmentTransaction = mFragmentManager.beginTransaction()
            // replace the container frameLayout
            mFragmentTransaction.replace(R.id.frame_container_tv_show, mMoviesTvShowDetailFragment,  MoviesTvWapiHomeDetailFragment::class.java.simpleName)
            // set back stack null to get on back pressed !exit
            mFragmentTransaction.addToBackStack(null)
            // commit the fragment
            mFragmentTransaction.commit()
        }
    }

    override fun onResume() {
        super.onResume()
        Log.d(tag, "onResume Tabs")
        recycler_view_tv_show.setHasFixedSize(true)
        recycler_view_tv_show.layoutManager = LinearLayoutManager(context)

    }


    override fun onPause() {
        super.onPause()
        Log.d(tagLog, "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d(tagLog, "onStop")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d(tagLog, "onDestroyView")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(tag, "on Destroy")
    }

    override fun onDetach() {
        super.onDetach()
        Log.d(tagLog, "onDetach")
//        arrayList.clear() //clear when out
    }

}
