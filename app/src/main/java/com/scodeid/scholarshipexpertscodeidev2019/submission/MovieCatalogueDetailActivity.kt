/*
 * Copyright (c) 2019. SCODEID
 */

/**
 * @author
 * Yogi Arif Widodo
 * www.dicoding.com/users/297307
 * www.github.com/yogithesymbian
 * For Google Kejar 2019
 */
package com.scodeid.scholarshipexpertscodeidev2019.submission

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.scodeid.scholarshipexpertscodeidev2019.R
import com.scodeid.scholarshipexpertscodeidev2019.submission.submission3.api.ApiEndPoint.Companion.POSTER_IMAGE
import com.scodeid.scholarshipexpertscodeidev2019.submission.submission3.model.MoviesApiData
import kotlinx.android.synthetic.main.activity_movie_catalogue_detail.*


/**
 * Create by new -> activity -> FullScreenActivity and has Implementation'sme
 */

class MovieCatalogueDetailActivity : Activity() {
    private val mHideHandler = Handler()
    private val tagLog = "MovieDetailActivity"

    //    private var delayAsyncDetailMovie: DelayAsyncMovieDetail? = null
    private val mHidePart2Runnable = Runnable {
        // Delayed removal of status and navigation_bottom_movie bar

        // Note that some of these constants are new as of API 16 (Jelly Bean)
        // and API 19 (KitKat). It is safe to use them, as they are inlined
        // at compile-time and do nothing on earlier devices.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                image_backdrop.systemUiVisibility =
                    View.SYSTEM_UI_FLAG_LOW_PROFILE or
                            View.SYSTEM_UI_FLAG_FULLSCREEN or
                            View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                            View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or
                            View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
                            View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
            }
        }
    }

    private val mShowPart2Runnable = Runnable {
        // Delayed display of UI elements
        actionBar?.show()
        fullscreen_content_controls.visibility = View.VISIBLE
    }
    private var mVisible: Boolean = false
    private val mHideRunnable = Runnable { hide() }

    /**
     * Touch listener to use for in-layout UI controls to delay hiding the
     * system UI. This is to prevent the jarring behavior of controls going away
     * while interacting with activity UI.
     */
    private val mDelayHideTouchListener = View.OnTouchListener { _, _ ->
        delayedHide(AUTO_HIDE_DELAY_MILLIS)
        false
    }

    @SuppressLint("SetTextI18n", "PrivateResource")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_movie_catalogue_detail)

        actionBar?.setDisplayHomeAsUpEnabled(true)

        mVisible = true

        // Set up the user interaction to manually show or hide the system UI.
        image_backdrop.setOnClickListener { toggle() }

        // Upon interacting with UI controls, delay any scheduled hide()
        // operations to prevent the jarring behavior of controls going away
        // while interacting with the UI.
        button_buy_download.setOnTouchListener(mDelayHideTouchListener)
        button_take_notification.setOnTouchListener(mDelayHideTouchListener)
        button_share.setOnTouchListener(mDelayHideTouchListener)
        button_stream_watch.setOnTouchListener(mDelayHideTouchListener)

//        val movieDataParcel = intent.getParcelableExtra<MovieDataModels>(EXTRA_MOVIE_DATA) //submission 1
//        val movieDataParcel = intent.getParcelableExtra<MovieDataModelsRecycler>(EXTRA_MOVIE_DATA) //submission 2
        val movieDataParcel = intent.getParcelableExtra<MoviesApiData>(EXTRA_MOVIE_DATA) //submission 3

        verifyIntentFromHomeMovieCatalogue(movieDataParcel.title)

        val string = "" + movieDataParcel.title
        text_tv_movie_name.text = string

        //score is vote_average
        text_score_label_percent.text =  movieDataParcel.voteAverage.toString()+" "+resources.getString(R.string.activity_movie_catalogue_detail_score)
        text_overview_desc.text = movieDataParcel.overview

        text_rank_last_today_label_data.text = "-" + "\n"
        text_rank_last_week_label_data.text = "-" + "\n"

        text_director_label_who.text = "-" + "\n"
        text_director_label_who1.text = "-" + "\n"

        text_screen_label_who.text = "-" + "\n"
        text_screen_label_who1.text = "-" + "\n"

        when (movieDataParcel.originalLang) {
            "en" -> text_original_language_label_data.text = getString(R.string.activity_movie_catalogue_detail_language) +"\n"
            "ja" -> text_original_language_label_data.text = getString(R.string.activity_movie_catalogue_detail_language_ja) +"\n"
            else -> text_original_language_label_data.text = movieDataParcel.originalLang + "\n"
        }

        text_runtime_label_data.text = "-" + "\n"
        text_budget_label_data.text = "-" + "\n"

        text_revenue_label_data.text = "-" + "\n"
        text_genre_label_data.text = " ID : "+movieDataParcel.genreIds.toString().replace("[[","").replace("]]","") + "\n"
        text_keywords_label_data.text = "-"

        text_release_label_data.text = movieDataParcel.releaseDate + "\n"

        text_vote_count_label_data.text = movieDataParcel.voteCount.toString() + "\n"
        text_popularity_label_data.text = movieDataParcel.popularity.toString() + "\n"
        if (!movieDataParcel.adult)
        {
            text_adult_label_data.text = getString(R.string.activity_movie_catalogue_detail_adult_desc) + "\n"
        }
        else
        {
            text_adult_label_data.text = getString(R.string.activity_movie_catalogue_detail_adult_desc_1) + "\n"
        }


        val imagePoster = movieDataParcel.posterPath
        val imagePosterBack = movieDataParcel.backdropPath

        Glide.with(this@MovieCatalogueDetailActivity)
            .asBitmap()
            .load(POSTER_IMAGE+"w185"+imagePoster)
            .error(R.color.error_color_material_light)
            .format(DecodeFormat.PREFER_ARGB_8888)
            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
            .into(image_poster)

        Glide.with(this@MovieCatalogueDetailActivity)
            .asBitmap()
            .load(POSTER_IMAGE+"w342"+imagePosterBack)
            .error(R.color.error_color_material_light)
            .format(DecodeFormat.PREFER_ARGB_8888)
            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
            .into(image_backdrop)


        /**
         * SET DATA from PoJo Intent Submission 2
         * ======================================
         */

        /*
        val string = "" + movieDataParcel.movieName
        text_tv_movie_name.text = string

        //score is like later's will refactor
        text_score_label_percent.text =  movieDataParcel.movieScore +" "+resources.getString(R.string.activity_movie_catalogue_detail_score)
        text_overview_desc.text = movieDataParcel.movieOverview

        text_rank_last_today_label_data.text = movieDataParcel.movieRankLastToday + "\n"
        text_rank_last_week_label_data.text = movieDataParcel.movieRAnkLastWeek + "\n"

        text_director_label_who.text = movieDataParcel.movieDirector1
        text_director_label_who1.text = movieDataParcel.movieDirector2

        text_screen_label_who.text = movieDataParcel.movieScreenPlay1
        text_screen_label_who1.text = movieDataParcel.movieScreenPlay2

        text_original_language_label_data.text = movieDataParcel.movieOrigLang

        text_runtime_label_data.text = movieDataParcel.movieRuntime + "\n"
        text_budget_label_data.text = movieDataParcel.movieBudget + "\n"

        text_revenue_label_data.text = movieDataParcel.movieRevenue + "\n"
        text_genre_label_data.text = movieDataParcel.movieGenres + "\n"
        text_keywords_label_data.text = movieDataParcel.movieKeywords + "\n"

        text_release_label_data.text = movieDataParcel.movieRelease + "\n"

        val imagePoster = movieDataParcel.moviePicture?.toInt()
        val imagePosterBack = movieDataParcel.moviePictureBackground?.toInt()
        val imagePosterRel1 = movieDataParcel.moviePictureRelated1?.toInt()
        val imagePosterRel2 = movieDataParcel.moviePictureRelated2?.toInt()
        val imagePosterRel3 = movieDataParcel.moviePictureRelated3?.toInt()

        Glide.with(this@MovieCatalogueDetailActivity)
            .asBitmap()
            .load(imagePoster)
            .apply(RequestOptions().override(114, 174))
            .into(image_poster)

        Glide.with(this@MovieCatalogueDetailActivity)
            .asBitmap()
            .load(imagePosterBack)
            .apply(RequestOptions().override(175, 100))
            .into(image_backdrop)
        // related movie image
        Glide.with(this@MovieCatalogueDetailActivity)
            .asBitmap()
            .load(imagePosterRel1)
            .apply(RequestOptions().override(75, 75))
            .into(image_related1)
        Glide.with(this@MovieCatalogueDetailActivity)
            .asBitmap()
            .load(imagePosterRel2)
            .apply(RequestOptions().override(75, 75))
            .into(image_related2)
        Glide.with(this@MovieCatalogueDetailActivity)
            .asBitmap()
            .load(imagePosterRel3)
            .apply(RequestOptions().override(75, 75))
            .into(image_related3)
        */


        /**
         * SET DATA from PoJo Intent Submission 1
         * ======================================
         *         // avoid skipped 34 frame of my code only image resource or resource link maybe later's
         */

/*
        val doInBackGroundMovieDetail = 1000 //in just start
        Handler().postDelayed(object : Runnable {
            override fun run() {

                this.finish()
                Log.d(TAG_LOG,"DONE ... for get and load data in background")

            }

            private fun finish()
            {

                Log.d(TAG_LOG,"Loading ... for get and load data in background")
                movieDataParcel.moviePicture.let { image_poster.setImageResource(it) }
                movieDataParcel.moviePictureBackground.let { image_backdrop.setImageResource(it) }
                movieDataParcel.moviePictureRelated1.let { image_related1.setImageResource(it) }
                movieDataParcel.moviePictureRelated2.let { image_related2.setImageResource(it) }
                movieDataParcel.moviePictureRelated3.let { image_related3.setImageResource(it) }


            }
        }, doInBackGroundMovieDetail.toLong())
        */


        image_related1.setOnClickListener{
            notificationFeatureWarn()
        }
        image_related2.setOnClickListener{
            notificationFeatureWarn()
        }
        image_related3.setOnClickListener{
            notificationFeatureWarn()
        }

        /**
         * bottomPopUpMovieDetail
         */

        button_take_notification.setOnClickListener {
            Log.d(tagLog,"notification got clicked")
            notificationFeatureWarn()
        }
        button_buy_download.setOnClickListener {
            Log.d(tagLog,"download got clicked")
            notificationFeatureWarn()
        }
        button_stream_watch.setOnClickListener {
            Log.d(tagLog,"stream got clicked")
            notificationFeatureWarn()
        }
        button_share.setOnClickListener {
            Log.d(tagLog,"share got clicked")
            notificationFeatureWarn()
        }
    }

    private fun notificationFeatureWarn() {

        Toast.makeText(this,"We are sorry , button on coming soon", Toast.LENGTH_SHORT)
            .show()
    }

    private fun verifyIntentFromHomeMovieCatalogue(movieName: String?) {

        if (movieName == movieName) {
            // check with log
            Log.d(tagLog, "Intent From Home Like mockito/junit succeed")
        } else {
            // exit / onBackPressed
            finish()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        Log.d(tagLog, "onBackPressed Clicked ")
//        val viewerAddIntent = Intent()
//        val viewData = 1
//        viewerAddIntent.putExtra(EXTRA_VIEWER, viewData)
//        setResult(RESULT_CODE,viewerAddIntent)
        finish()
    }

    /**
     * LifeCycle Android
     * onCreate -> onStart -> onResum -> onPause -> onStop -> onRestart -> onDestroy
     */
    override fun onStart() {
        super.onStart()
        Log.d(tagLog, "onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d(tagLog, "onStart")
    }

    override fun onPause() {
        super.onPause()
        /**
         * if need memory got killed and back to the onCreate
         * if user return to activity back to the onResume
         */
        Log.d(tagLog, "onStart")
    }

    override fun onStop() {
        super.onStop()
        /**
         * if need navigate's to the activity
         * if user return to activity back to the onResume then run onStart
         */
        Log.d(tagLog, "onStart")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d(tagLog, "onStart")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(tagLog, "Detail Movie Activity onDestroy")
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)

        // Trigger the initial hide() shortly after the activity has been
        // created, to briefly hint to the user that UI controls
        // are available.
        delayedHide(100)
    }

    private fun toggle() {
        if (mVisible) {
            hide()
        } else {
            show()
        }
    }

    private fun hide() {
        // Hide UI first
        actionBar?.hide()
        fullscreen_content_controls.visibility = View.GONE
        mVisible = false

        // Schedule a runnable to remove the status and navigation_bottom_movie bar after a delay
        mHideHandler.removeCallbacks(mShowPart2Runnable)
        mHideHandler.postDelayed(mHidePart2Runnable, UI_ANIMATION_DELAY.toLong())
    }

    private fun show() {
        // Show the system bar
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            image_backdrop.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                        View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
        }
        mVisible = true

        // Schedule a runnable to display UI elements after a delay
        mHideHandler.removeCallbacks(mHidePart2Runnable)
        mHideHandler.postDelayed(mShowPart2Runnable, UI_ANIMATION_DELAY.toLong())
    }

    /**
     * Schedules a call to hide() in [delayMillis], canceling any
     * previously scheduled calls.
     */
    private fun delayedHide(delayMillis: Int) {
        mHideHandler.removeCallbacks(mHideRunnable)
        mHideHandler.postDelayed(mHideRunnable, delayMillis.toLong())
    }

    companion object {
        /**
         * Whether or not the system UI should be auto-hidden after
         * [AUTO_HIDE_DELAY_MILLIS] milliseconds.
         */
        private const val AUTO_HIDE = true

        /**
         * If [AUTO_HIDE] is set, the number of milliseconds to wait after
         * user interaction before hiding the system UI.
         */
        private const val AUTO_HIDE_DELAY_MILLIS = 3000

        /**
         * Some older devices needs a small delay between UI widget updates
         * and a change of the status and navigation_bottom_movie bar.
         */
        private const val UI_ANIMATION_DELAY = 300

        /**
         * Detail movie
         */

        var EXTRA_MOVIE_DATA = "extra_movie_data"


        // intent result viewer increment
//        var RESULT_CODE = 110
//        var EXTRA_VIEWER = "extra_viewer"
    }
}