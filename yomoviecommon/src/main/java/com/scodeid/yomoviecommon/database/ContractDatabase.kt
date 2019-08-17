/*
 * Copyright (c) 2019. SCODEID
 */

package com.scodeid.yomoviecommon.database

import android.database.Cursor
import android.net.Uri
import android.provider.BaseColumns

/**
 * @author
 * Created by scode on 31,July,2019
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
_               _         _               _  _
___ _   _| |__  _ __ ___ (_)___ ___(_) ___  _ __   | || |
/ __| | | | '_ \| '_ ` _ \| / __/ __| |/ _ \| '_ \  | || |_
\__ \ |_| | |_) | | | | | | \__ \__ \ | (_) | | | | |__   _|
|___/\__,_|_.__/|_| |_| |_|_|___/___/_|\___/|_| |_|    |_|


 */
object ContractDatabase {

    // content provider
    const val AUTHORITY = "com.scodeid.scholarshipexpertscodeidev2019.homeFavorite"
    private const val SCHEME = "content"
    // ==================

    object MovieColumns : BaseColumns {
        // table name
        const val TABLE_NAME_MOVIE = "table_movies"
        const val TABLE_NAME_TV = "table_tv"

        // field
        const val RELEASE = "release"
        const val TITLE = "title"

        const val DESCRIPTION = "description"
        const val VOTE_AVERAGE = "vote_average"

        const val POSTER = "poster"

        // content provider
        val CONTENT_URI_MOVIE: Uri = Uri.Builder().scheme(SCHEME)
            .authority(AUTHORITY)
            .appendPath(TABLE_NAME_MOVIE)
            .build()

        val CONTENT_URI_TV: Uri = Uri.Builder().scheme(SCHEME)
            .authority(AUTHORITY)
            .appendPath(TABLE_NAME_TV)
            .build()
        // ==================


        fun getColumnString(cursor: Cursor, columnName: String): String {
            return cursor.getString(cursor.getColumnIndex(columnName))
        }

        fun getColumnInt(cursor: Cursor, columnName: String): Int {
            return cursor.getInt(cursor.getColumnIndex(columnName))
        }

        @Suppress("Unused")
        fun getColumnLong(cursor: Cursor, columnName: String): Long {
            return cursor.getLong(cursor.getColumnIndex(columnName))
        }
    }
}