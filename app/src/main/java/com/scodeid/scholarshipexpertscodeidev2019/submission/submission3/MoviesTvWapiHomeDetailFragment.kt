/*
 * Copyright (c) 2019. SCODEID
 */

package com.scodeid.scholarshipexpertscodeidev2019.submission.submission3


import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.scodeid.scholarshipexpertscodeidev2019.R
import com.scodeid.scholarshipexpertscodeidev2019.submission.submission3.api.ApiEndPoint.Companion.POSTER_IMAGE
import com.scodeid.scholarshipexpertscodeidev2019.submission.submission3.model.MoviesTvShowApiData
import kotlinx.android.synthetic.main.fragment_movies_tv_show_detail.*

class MoviesTvWapiHomeDetailFragment : Fragment() {

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

    @SuppressLint("PrivateResource")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // check bundle
        if (arguments != null)
        {
            Log.d(tagLog,"Bundle Argument != null ")
            val data = arguments?.getParcelable<MoviesTvShowApiData>(extraTvDetails)

            /**
             * Image Set Data
             */

            Glide.with(this@MoviesTvWapiHomeDetailFragment)
                .asBitmap()
                .load(POSTER_IMAGE+"w185"+data?.posterPath)
                .error(R.color.error_color_material_light)
                .format(DecodeFormat.PREFER_ARGB_8888)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .into(image_tv_movie_detail_poster)

            Glide.with(this@MoviesTvWapiHomeDetailFragment)
                .asBitmap()
                .load(POSTER_IMAGE+"w185"+data?.backDropPath)
                .error(R.color.error_color_material_light)
                .format(DecodeFormat.PREFER_ARGB_8888)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .into(image_tv_movie_detail_background)


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
                    text_tv_movie_detail.text = data?.name
                    //score is like later's will refactor
                    text_overview_desc.text = data?.overView
                    text_score_tv.text = data?.voteAverage.toString() + " \u2605"

                    text_creator_label_who.text = "-"
                    text_creator_label_who1.text = "-"
                    text_creator_label_who2.text = "-"

                    text_status_label_data.text = "-"+ "\n"
                    text_network_label_data.text = "-" + "\n"

                    text_original_name.text = data?.originalName + "\n"

                    text_original_country.text = data?.originCountry.toString().replace("[[","").replace("]]","").replace("","") + "\n"

                    when (data?.originalLanguage) {
                        "en" -> text_original_language_label_data.text = getString(R.string.activity_movie_catalogue_detail_language) +"\n"
                        "ja" -> text_original_language_label_data.text = getString(R.string.activity_movie_catalogue_detail_language_ja) +"\n"
                        else -> text_original_language_label_data.text = data?.originalLanguage + "\n"
                    }

                    text_genre_label_data.text = "-" + "\n"
                    text_tv_movie_detail_genre.text = "ID :"+ data?.genreIds.toString().replace("[[","").replace("]]","")
                    text_keywords_label_data.text = "-" + "\n"
                    text_type_label_data.text = "-" + "\n"

                    text_rank_last_today_label_data.text = "-" + "\n" // i have a lot layout xml
                    text_rank_last_week_label_data.text = "-" + "\n" // so i need have break line with this for easy but need annotation SetTextI18n

                    text_vote_count_label_data.text = data?.voteCount.toString() + "\n"
                    text_popularity_label_data.text = data?.popularity.toString() + "\n"

                    text_release_label_data.text = data?.firstAirDate + "\n"
                    text_runtime_label_data.text = "-" + "\n"
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
