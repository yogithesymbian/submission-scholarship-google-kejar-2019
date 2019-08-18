/*
 * Copyright (c) 2019. SCODEID
 */

package com.scodeid.scholarshipexpertscodeidev2019personal

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.scodeid.scholarshipexpertscodeidev2019personal.homeFavorite.MainFavoriteMovieActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fav.setOnClickListener {
            startActivity(Intent(this@MainActivity, MainFavoriteMovieActivity::class.java))
        }
    }
}
