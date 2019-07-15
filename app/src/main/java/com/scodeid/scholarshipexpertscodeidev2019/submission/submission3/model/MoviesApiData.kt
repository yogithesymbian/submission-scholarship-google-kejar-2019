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
 * Android Studio 3.3.2
 * Build # AI-182.5107.16.33.5314842, built on February 15, 2019
 * JRE: 1.8.0_152-release-1248-b01 amd64
 * JVM: OpenJDK 64-Bit Server VM by JetBrains s.r.o
 * Linux 4.19.0-kali5-amd64
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
class MoviesApiData (
    val voteCount : Int,
    val id : Int,
    val video : Boolean,
    val voteAverage : Int,
    val title : String,
    val popularity : Int,
    val posterPath : String,
    val originalLang : String,
    val originalTitle : String,
    val genreIds : ArrayList<String>,
    val backdropPath : String,
    val adult : Boolean,
    val overview : String,
    val releaseDate : String
) : Parcelable