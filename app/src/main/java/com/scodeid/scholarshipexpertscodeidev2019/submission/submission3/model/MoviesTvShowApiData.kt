/*
 * Copyright (c) 2019. SCODEID
 */

package com.scodeid.scholarshipexpertscodeidev2019.submission.submission3.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * @author
 * Created by scode on 13,July,2019
 * Yogi Arif Widodo
 * www.dicoding.com/users/297307
 * www.github.com/yogithesymbian
 * SCODEID company,
 * Indonesia, East Borneo.
 * ==============================================================
Android Studio 3.4.2
Build #AI-183.6156.11.34.5692245, built on June 27, 2019
JRE: 1.8.0_152-release-1343-b16-5323222 amd64
JVM: OpenJDK 64-Bit Server VM by JetBrains s.r.o
Linux 4.19.0-kali5-amd64
 * ==============================================================
 *              _               _         _               _____
 *   ___ _   _| |__  _ __ ___ (_)___ ___(_) ___  _ __   |___ /
 * / __| | | | '_ \| '_ ` _ \| / __/ __| |/ _ \| '_ \    |_ \
 * \__ \ |_| | |_) | | | | | | \__ \__ \ | (_) | | | |  ___) |
 * |___/\__,_|_.__/|_| |_| |_|_|___/___/_|\___/|_| |_| |____/
 *
 *
 */

@Parcelize
class MoviesTvShowApiData (
    val originalName : String,
    val genreIds : ArrayList<String>,
    val name : String,
    val popularity : Int,
    val originCountry : ArrayList<String>,
    val voteCount : Int,
    val firstAirDate : String,
    val backDropPath : String,
    val originalLanguage : String,
    val id : Int,
    val voteAverage : Int,
    val overView : String,
    val posterPath : String
) : Parcelable