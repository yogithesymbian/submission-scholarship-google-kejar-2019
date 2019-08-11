/*
 * Copyright (c) 2019. SCODEID
 */

package com.scodeid.scholarshipexpertscodeidev2019.homeFavorite

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.scodeid.scholarshipexpertscodeidev2019.R
import com.scodeid.scholarshipexpertscodeidev2019.api.ApiEndPoint.Companion.POSTER_IMAGE
import com.scodeid.scholarshipexpertscodeidev2019.model.favorite.HelperModel
import com.scodeid.scholarshipexpertscodeidev2019.model.favorite.TvModel
import kotlinx.android.synthetic.main.fragment_movies_tv_show_detail_head.*

class MainFavoriteTvDetailActivity : AppCompatActivity() {

    companion object {

        const val EXTRA_TV = "extra_tv"
        const val EXTRA_POSITION = "extra_position"

        const val REQUEST_UPDATE = 200

        val TAG_LOG: String = MainFavoriteTvDetailActivity::class.java.simpleName
    }

    private var tvModel: TvModel? = null
    private var helperModel: HelperModel? = null
    private var position: Int = 0
    private var isEdit = false


    @SuppressLint("PrivateResource")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_favorite_tv_detail)

        helperModel = HelperModel.getInstance(applicationContext)
        tvModel = intent.getParcelableExtra<TvModel>(EXTRA_TV)

        // set position intent
        if (tvModel != null) {
            position = intent.getIntExtra(EXTRA_POSITION, 0)
            isEdit = true
        }

        if (isEdit) {
            if (tvModel != null) {
                Log.d(TAG_LOG, "image can load ? see : ${tvModel?.posterImage}")
                Glide.with(this)
                    .asBitmap()
                    .load("${POSTER_IMAGE}w185${tvModel?.posterImage}")
                    .error(R.color.error_color_material_light)
                    .format(DecodeFormat.PREFER_ARGB_8888)
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                    .into(image_tv_movie_detail_poster)
                text_tv_movie_detail.text = tvModel?.title

            }
        }

    }
}
