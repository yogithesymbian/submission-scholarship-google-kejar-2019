/*
 * Copyright (c) 2019. SCODEID
 */

package com.scodeid.scholarshipexpertscodeidev2019personal.homeFavorite

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.scodeid.scholarshipexpertscodeidev2019personal.R
import com.scodeid.yomoviecommon.model.favorite.TvProvModel
import com.scodeid.yomoviecommon.utils.POSTER_IMAGE
import kotlinx.android.synthetic.main.fragment_movies_tv_show_detail_head.*

class MainFavoriteTvDetailActivity : AppCompatActivity() {

    companion object {

        const val EXTRA_TV = "extra_tv"
        const val EXTRA_POSITION = "extra_position"

        const val REQUEST_UPDATE = 200

        val TAG_LOG: String = MainFavoriteTvDetailActivity::class.java.simpleName
    }

    private var tvProvModel: TvProvModel? = null
    private var position: Int = 0


    @RequiresApi(Build.VERSION_CODES.N)
    @SuppressLint("PrivateResource")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_favorite_tv_detail)

        tvProvModel = intent.getParcelableExtra<Parcelable>(EXTRA_TV) as TvProvModel?

        if (tvProvModel != null) position = intent.getIntExtra(EXTRA_POSITION, 0)
        else tvProvModel = TvProvModel()

        if (tvProvModel != null) {
            Glide.with(this)
                .asBitmap()
                .load("${POSTER_IMAGE}w185${tvProvModel?.posterImage}")
                .error(R.color.error_color_material_light)
                .format(DecodeFormat.PREFER_ARGB_8888)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .into(image_tv_movie_detail_poster)

            text_tv_movie_detail.text = tvProvModel?.title
        }

        val uri = intent.data
        if (uri != null) {
            val cursor = contentResolver.query(uri, null, null, null, null)
            if (cursor != null) {
                if (cursor.moveToFirst()) tvProvModel = TvProvModel(cursor)
            }
        }

    }
}
