/*
 * Copyright (c) 2019. SCODEID
 */

package com.scodeid.scholarshipexpertscodeidev2019.submission.submission4.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns
import com.scodeid.scholarshipexpertscodeidev2019.submission.submission4.database.ContractDatabase.MovieColumns

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

class HelperDatabase(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_TABLE_MOVIE)
        db.execSQL(SQL_CREATE_TABLE_TV)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(" DROP TABLE IF EXISTS ${MovieColumns.TABLE_NAME_MOVIE}")
        db.execSQL(" DROP TABLE IF EXISTS ${MovieColumns.TABLE_NAME_TV}")
        onCreate(db)
    }

    companion object {

        private const val DATABASE_NAME = "DbMovie.db"

        private const val DATABASE_VERSION = 1

        private const val SQL_CREATE_TABLE_MOVIE =
            "CREATE TABLE ${MovieColumns.TABLE_NAME_MOVIE} (" +
                    "${BaseColumns._ID} INTEGER PRIMARY KEY," +
                    "${MovieColumns.RELEASE} TEXT," +
                    "${MovieColumns.TITLE} TEXT," +
                    "${MovieColumns.DESCRIPTION} TEXT," +
                    "${MovieColumns.POSTER} TEXT)"

        private const val SQL_CREATE_TABLE_TV =
            "CREATE TABLE ${MovieColumns.TABLE_NAME_TV} (" +
                    "${BaseColumns._ID} INTEGER PRIMARY KEY," +
                    "${MovieColumns.TITLE} TEXT," +
                    "${MovieColumns.VOTE_AVERAGE} INTEGER," +
                    "${MovieColumns.POSTER} TEXT)"
    }
}