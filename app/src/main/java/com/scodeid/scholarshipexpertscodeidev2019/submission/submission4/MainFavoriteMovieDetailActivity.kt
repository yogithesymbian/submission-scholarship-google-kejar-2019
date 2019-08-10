/*
 * Copyright (c) 2019. SCODEID
 */

package com.scodeid.scholarshipexpertscodeidev2019.submission.submission4

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.scodeid.scholarshipexpertscodeidev2019.R
import com.scodeid.scholarshipexpertscodeidev2019.submission.submission4.model.HelperModel
import com.scodeid.scholarshipexpertscodeidev2019.submission.submission4.model.MovieModel
import kotlinx.android.synthetic.main.activity_movie_catalogue_detail_head.*

class MainFavoriteMovieDetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_MOVIE = "extra_movie"
        const val EXTRA_POSITION = "extra_position"

        const val REQUEST_UPDATE = 200
        const val RESULT_DELETE = 301


        val TAG_LOG: String = MainFavoriteMovieDetailActivity::class.java.simpleName
    }

    private var movieModel: MovieModel? = null
    private var helperModel: HelperModel? = null
    private var position: Int = 0
    private var isEdit = false


    @SuppressLint("PrivateResource")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_favorite_detail)

        helperModel = HelperModel.getInstance(applicationContext)
        movieModel = intent.getParcelableExtra<MovieModel>(EXTRA_MOVIE)

        // set position intent
        if (movieModel != null) {
            position = intent.getIntExtra(EXTRA_POSITION, 0)
            isEdit = true
        }

        if (isEdit) {
            if (movieModel != null) {
                Log.d(TAG_LOG, "image can load ? see : " + movieModel?.posterImage)
                Glide.with(this)
                    .asBitmap()
                    .load(movieModel?.posterImage)
                    .error(R.color.error_color_material_light)
                    .format(DecodeFormat.PREFER_ARGB_8888)
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                    .into(image_poster)

                text_tv_movie_name.text = movieModel?.title
            }
        }


    }
}
