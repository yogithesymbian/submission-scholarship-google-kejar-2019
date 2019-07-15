/*
 * Copyright (c) 2019. SCODEID
 */

package com.scodeid.scholarshipexpertscodeidev2019.submission.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * @author
 * Created by scode on 03,July,2019
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
 *            _               _         _               ____
 *  ___ _   _| |__  _ __ ___ (_)___ ___(_) ___  _ __   |___ \
 * / __| | | | '_ \| '_ ` _ \| / __/ __| |/ _ \| '_ \    __) |
 * \__ \ |_| | |_) | | | | | | \__ \__ \ | (_) | | | |  / __/
 * |___/\__,_|_.__/|_| |_| |_|_|___/___/_|\___/|_| |_| |_____|
 *
 */

@Parcelize
class MovieDataModelsRecycler(
    val moviePicture: String?, //done
    val moviePictureBackground: String?, //done
    val moviePictureRelated1 : String?,
    val moviePictureRelated2 : String?,
    val moviePictureRelated3 : String?,
    val movieName: String?, //done
    val movieRelease: String?, //done
    val movieOverview: String?, //done
    val movieRankLastToday: String?,
    val movieRAnkLastWeek: String?,
    val movieDirector1: String?, //done
    val movieDirector2: String?, //done
    val movieOrigLang: String?, //done
    val movieRuntime: String?, //done
    val movieBudget: String?, //done
    val movieRevenue: String?, //done
    val movieScreenPlay1: String?, //done
    val movieScreenPlay2: String?, //done
    val movieGenres: String?, //done
    val movieKeywords: String?, //done
    val movieScore: String?, //done
    val movieViewers: String?) : Parcelable
