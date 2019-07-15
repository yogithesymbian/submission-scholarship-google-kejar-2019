/*
 * Copyright (c) 2019. SCODEID
 */

package com.scodeid.scholarshipexpertscodeidev2019.submission.submission2


import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.scodeid.scholarshipexpertscodeidev2019.R
import com.scodeid.scholarshipexpertscodeidev2019.submission.model.MovieDataModelsRecyclerTv
import kotlinx.android.synthetic.main.fragment_movies_tv_show_detail.*

class MoviesTvShowDetailFragment : Fragment() {

    companion object {
        var tagLog = "MoviesTvShowDetailFragment"
        var extraTvDetails = "extra_tv_details"
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movies_tv_show_detail, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // check bundle
        if (arguments != null)
        {
            Log.d(tagLog,"Bundle Argument != null ")
            val data = arguments?.getParcelable<MovieDataModelsRecyclerTv>(extraTvDetails)

            /**
             * Image Set Data
             */

            Glide.with(this@MoviesTvShowDetailFragment)
                .asBitmap()
                .load(data?.moviePictureTv?.toInt())
                .apply(RequestOptions().override(114, 174))
                .into(image_tv_movie_detail_poster)

            Glide.with(this@MoviesTvShowDetailFragment)
                .asBitmap()
                .load(data?.moviePictureBackgroundTv?.toInt())
                .apply(RequestOptions().override(175, 100))
                .into(image_tv_movie_detail_background)
            Glide.with(this@MoviesTvShowDetailFragment)
                .asBitmap()
                .load(data?.moviePictureRelated1Tv?.toInt())
                .apply(RequestOptions().override(75, 75))
                .into(image_related1)
            Glide.with(this@MoviesTvShowDetailFragment)
                .asBitmap()
                .load(data?.moviePictureRelated2Tv?.toInt())
                .apply(RequestOptions().override(75, 75))
                .into(image_related2)
            Glide.with(this@MoviesTvShowDetailFragment)
                .asBitmap()
                .load(data?.moviePictureRelated3Tv?.toInt())
                .apply(RequestOptions().override(75, 75))
                .into(image_related3)

            /**
             * END OF set image data
             */

            /**
             * Text Data Set
             */

            val doInInBackGroundMovie = 100 //in just start
            Handler().postDelayed(object : Runnable {
                override fun run() {
                    this.finish()
                    Log.d(tagLog,"Loading ... for get and load data in background")
                }

                @SuppressLint("SetTextI18n")
                private fun finish() {
                    text_tv_movie_detail.text = data?.movieNameTv
                    //score is like later's will refactor
                    text_overview_desc.text = data?.movieOverviewTv
                    text_score_tv.text = data?.movieScoreTv + " \u2605"

                    text_creator_label_who.text = data?.movieCreator1
                    text_creator_label_who1.text = data?.movieCreator2
                    text_creator_label_who2.text = data?.movieCreator3

                    text_status_label_data.text = data?.movieStatusTv + "\n"
                    text_network_label_data.text = data?.movieNetworkTv + "\n"
                    text_original_language_label_data.text = data?.movieOrigLangTv + "\n"

                    text_genre_label_data.text = data?.movieGenresTv + "\n"
                    text_tv_movie_detail_genre.text = data?.movieGenresTv
                    text_keywords_label_data.text = data?.movieKeywordsTv + "\n"
                    text_type_label_data.text = data?.movieTypeTv + "\n"

                    text_rank_last_today_label_data.text = data?.movieRankLastTodayTv + "\n" // i have a lot layout xml
                    text_rank_last_week_label_data.text = data?.movieRAnkLastWeekTv + "\n" // so i need have break line with this for easy but need annotation SetTextI18n

                    text_release_label_data.text = data?.movieReleaseTv + "\n"
                    text_runtime_label_data.text = data?.movieRuntimeTv + "\n"
                }
            }, doInInBackGroundMovie.toLong())

        }
    }

    override fun onStart() {
        super.onStart()
        Log.d(tagLog, "onStart")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(tagLog, "onViewCreated")
        //view
    }

    override fun onResume() {
        super.onResume()
        Log.d(tag, "onResume Tabs")

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
    }


}
