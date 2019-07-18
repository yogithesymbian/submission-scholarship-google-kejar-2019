/*
 * Copyright (c) 2019. SCODEID
 */

/**
 * @author
 * Yogi Arif Widodo
 * www.dicoding.com/users/297307
 * www.github.com/yogithesymbian
 * For Google Kejar 2019
 */

package com.scodeid.scholarshipexpertscodeidev2019.submission.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * THIS CLASS JUST FOR SUBMISSION 2 SOMETIMES WILL USE
 */
@Parcelize
class MovieDataModels (
    val moviePicture: Int, //done
    val moviePictureBackground: Int, //done
    val moviePictureRelated1 : Int,
    val moviePictureRelated2 : Int,
    val moviePictureRelated3 : Int,
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
//the comment just for check if i had been done with set data on detail