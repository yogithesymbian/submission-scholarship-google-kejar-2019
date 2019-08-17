/*
 * Copyright (c) 2019. SCODEID
 */

package com.scodeid.scholarshipexpertscodeidev2019.notification

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.scodeid.scholarshipexpertscodeidev2019.R
import com.scodeid.scholarshipexpertscodeidev2019.homeFavorite.MainFavoriteMovieActivity
import com.scodeid.scholarshipexpertscodeidev2019.homeFavorite.MainFavoriteTvActivity
import kotlinx.android.synthetic.main.activity_no_internet_conn.*

class NoInternetConnActivity : AppCompatActivity() {

    companion object {
        val TAG_LOG: String = NoInternetConnActivity::class.java.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_no_internet_conn)

        button_get_notif.setOnClickListener {
            Toast.makeText(
                this@NoInternetConnActivity,
                resources.getString(R.string.activity_movie_catalogue_main_content_soon),
                Toast.LENGTH_SHORT
            ).show()
        }

        button_fav_mv_act.setOnClickListener { startActivity(Intent(this@NoInternetConnActivity, MainFavoriteMovieActivity::class.java)) }

        button_fav_tv_act.setOnClickListener { startActivity(Intent(this@NoInternetConnActivity, MainFavoriteTvActivity::class.java)) }

    }

}
