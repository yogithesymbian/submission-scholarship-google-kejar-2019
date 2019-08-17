/*
 * Copyright (c) 2019. SCODEID
 */

package com.scodeid.scholarshipexpertscodeidev2019.helper

import android.database.Cursor
import android.os.Build
import android.provider.BaseColumns._ID
import androidx.annotation.RequiresApi
import com.scodeid.scholarshipexpertscodeidev2019.database.ContractDatabase
import com.scodeid.scholarshipexpertscodeidev2019.model.favorite.MovieProvModel
import com.scodeid.scholarshipexpertscodeidev2019.model.favorite.TvProvModel
import java.util.*

/**
 * @author
 * Created by scode on 11,August,2019
 * Yogi Arif Widodo
 * www.dicoding.com/users/297307
 * www.github.com/yogithesymbian
 * SCODEID company,
 * Indonesia, East Borneo.
 * ==============================================================
 * Android Studio 3.4.2
 * Build #AI-183.6156.11.34.5692245, built on June 27, 2019
 * JRE: 1.8.0_152-release-1343-b16-5323222 amd64
 * JVM: OpenJDK 64-Bit Server VM by JetBrains s.r.o
 * Linux 4.19.0-kali5-amd64
 * ==============================================================
_               _         _               ____
___ _   _| |__  _ __ ___ (_)___ ___(_) ___  _ __   | ___|
/ __| | | | '_ \| '_ ` _ \| / __/ __| |/ _ \| '_ \  |___ \
\__ \ |_| | |_) | | | | | | \__ \__ \ | (_) | | | |  ___) |
|___/\__,_|_.__/|_| |_| |_|_|___/___/_|\___/|_| |_| |____/

 */

object MappingHelper {

    @RequiresApi(api = Build.VERSION_CODES.N)
    fun movieMapCursorToArrayList(movieCursor: Cursor): ArrayList<MovieProvModel> {
        val favMovieList = ArrayList<MovieProvModel>()
        while (movieCursor.moveToNext()) {

            val id = movieCursor.getInt(movieCursor.getColumnIndexOrThrow(_ID))
            val title = movieCursor.getString(movieCursor.getColumnIndexOrThrow(ContractDatabase.MovieColumns.TITLE))
            val release =
                movieCursor.getString(movieCursor.getColumnIndexOrThrow(ContractDatabase.MovieColumns.RELEASE))
            val description =
                movieCursor.getString(movieCursor.getColumnIndexOrThrow(ContractDatabase.MovieColumns.DESCRIPTION))
            val posterImage =
                movieCursor.getString(movieCursor.getColumnIndexOrThrow(ContractDatabase.MovieColumns.POSTER))

            favMovieList.add(MovieProvModel(id, title, release, description, posterImage))
        }
        return favMovieList
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    fun tvMapCursorToArrayList(tvCursor: Cursor): ArrayList<TvProvModel> {
        val tvMovieList = ArrayList<TvProvModel>()
        while (tvCursor.moveToNext()) {

            val id = tvCursor.getInt(tvCursor.getColumnIndexOrThrow(_ID))
            val title = tvCursor.getString(tvCursor.getColumnIndexOrThrow(ContractDatabase.MovieColumns.TITLE))
            val voteAverage =
                tvCursor.getInt(tvCursor.getColumnIndexOrThrow(ContractDatabase.MovieColumns.VOTE_AVERAGE))
            val posterImage = tvCursor.getString(tvCursor.getColumnIndexOrThrow(ContractDatabase.MovieColumns.POSTER))

            tvMovieList.add(TvProvModel(id, title, voteAverage, posterImage))
        }
        return tvMovieList
    }
}
