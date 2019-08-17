/*
 * Copyright (c) 2019. SCODEID
 */

package com.scodeid.scholarshipexpertscodeidev2019.notification

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.scodeid.scholarshipexpertscodeidev2019.R
import kotlinx.android.synthetic.main.activity_coming_soon.*

class ComingSoonActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coming_soon)

        button_back.setOnClickListener {
            finish()
            return@setOnClickListener
        }
    }
}