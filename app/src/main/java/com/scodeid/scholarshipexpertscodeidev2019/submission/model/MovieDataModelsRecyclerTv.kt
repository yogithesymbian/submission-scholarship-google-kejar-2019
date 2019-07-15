/*
 * Copyright (c) 2019. SCODEID
 */

package com.scodeid.scholarshipexpertscodeidev2019.submission.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * @author
 * Created by scode on 06,July,2019
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
class MovieDataModelsRecyclerTv(
    val moviePictureTv: String?, //done
    val moviePictureBackgroundTv: String?, //done
    val moviePictureRelated1Tv : String?, //not yet
    val moviePictureRelated2Tv : String?, //not yet
    val moviePictureRelated3Tv : String?, //not yet

    val movieNameTv: String?, //done
    val movieOverviewTv: String?, //done
    val movieScoreTv: String?,  //done

    val movieCreator1: String?, //not yet
    val movieCreator2: String?, //not yet
    val movieCreator3: String?, //not yet

    val movieStatusTv: String?, //not yet
    val movieNetworkTv: String?, //not yet
    val movieOrigLangTv: String?, //done


    val movieGenresTv: String?, //done
    val movieKeywordsTv: String?, //done
    val movieTypeTv: String?, //not yet

    val movieRankLastTodayTv: String?,
    val movieRAnkLastWeekTv: String?,
    val movieReleaseTv: String?,
    val movieRuntimeTv: String?
) : Parcelable
