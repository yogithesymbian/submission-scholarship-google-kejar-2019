/*
 * Copyright (c) 2019. SCODEID
 */

package com.scodeid.scholarshipexpertscodeidev2019.homeFavorite

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.scodeid.scholarshipexpertscodeidev2019.R
import com.scodeid.scholarshipexpertscodeidev2019.model.favorite.MovieProvModel
import kotlinx.android.synthetic.main.activity_main_favorite_detail.*
import kotlinx.android.synthetic.main.activity_movie_catalogue_detail_head.*

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class MainFavoriteMovieDetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_MOVIE = "extra_movie"
        const val EXTRA_POSITION = "extra_position"

        const val REQUEST_UPDATE = 200
        const val RESULT_DELETE = 301


        val TAG_LOG: String = MainFavoriteMovieDetailActivity::class.java.simpleName
    }

    private var movieProvModel: MovieProvModel? = null

    private var position: Int = 0


    @RequiresApi(Build.VERSION_CODES.N)
    @SuppressLint("PrivateResource")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_favorite_detail)

        movieProvModel = intent.getParcelableExtra<Parcelable>(EXTRA_MOVIE) as MovieProvModel?

        if (movieProvModel != null) position = intent.getIntExtra(EXTRA_POSITION, 0)
        else movieProvModel = MovieProvModel()

        if (movieProvModel != null) {
            Glide.with(this)
                .asBitmap()
                .load(movieProvModel?.posterImage)
                .error(R.color.error_color_material_light)
                .format(DecodeFormat.PREFER_ARGB_8888)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .into(image_poster)
            text_tv_movie_name.text = movieProvModel?.title
        }

        val uri = intent.data
        if (uri != null) {
            val cursor = contentResolver.query(uri, null, null, null, null)
            if (cursor != null) {
                if (cursor.moveToFirst()) movieProvModel = MovieProvModel(cursor)
                cursor.close()
            }
        }

    }
}
