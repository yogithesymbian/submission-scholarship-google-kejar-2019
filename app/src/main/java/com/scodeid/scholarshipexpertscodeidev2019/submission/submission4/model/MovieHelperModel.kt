/*
 * Copyright (c) 2019. SCODEID
 */

package com.scodeid.scholarshipexpertscodeidev2019.submission.submission4.model

import android.content.Context
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns
import com.scodeid.scholarshipexpertscodeidev2019.submission.submission4.database.HelperDatabase
import com.scodeid.scholarshipexpertscodeidev2019.submission.submission4.database.MovieContractDatabase
import com.scodeid.scholarshipexpertscodeidev2019.submission.submission4.database.MovieContractDatabase.MovieColumns.DESCRIPTION
import com.scodeid.scholarshipexpertscodeidev2019.submission.submission4.database.MovieContractDatabase.MovieColumns.POSTER
import com.scodeid.scholarshipexpertscodeidev2019.submission.submission4.database.MovieContractDatabase.MovieColumns.TABLE_NAME
import com.scodeid.scholarshipexpertscodeidev2019.submission.submission4.database.MovieContractDatabase.MovieColumns.TITLE

//import com.scodeid.scholarshipexpertscodeidev2019.submission.submission4.database.MovieContractDatabase.MovieColumns.Companion.DESCRIPTION
//import com.scodeid.scholarshipexpertscodeidev2019.submission.submission4.database.MovieContractDatabase.MovieColumns.Companion.POSTER
//import com.scodeid.scholarshipexpertscodeidev2019.submission.submission4.database.MovieContractDatabase.MovieColumns.Companion.TITLE
//import com.scodeid.scholarshipexpertscodeidev2019.submission.submission4.database.MovieContractDatabase.TABLE_MOVIES

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

class MovieHelperModel
/**
 * CONSTRUCTOR
 */
private constructor(context: Context) {

    companion object {
        /**
         * DECLARE DATABASE VARIABLE
         */
        private const val DATABASE_TABLE = MovieContractDatabase.MovieColumns.TABLE_NAME
        private lateinit var helperDatabase: HelperDatabase
        lateinit var INSTANCE: MovieHelperModel
        private lateinit var sqLiteDatabase: SQLiteDatabase
        /**
         * INITIALIZE DATABASE LATER'S
         */
        fun getInstance(context: Context): MovieHelperModel {
            synchronized(SQLiteOpenHelper::class.java) {
                INSTANCE = MovieHelperModel(context)
            }
            return INSTANCE
        }
    }

    init {
        helperDatabase = HelperDatabase(context)
    }

    /**
     * OPEN AND CLOSE CONNECTION
     */
    @Throws(SQLException::class)
    fun open() {
        sqLiteDatabase = helperDatabase.writableDatabase
    }

    fun close() {
        helperDatabase.close()

        if (sqLiteDatabase.isOpen) {
            sqLiteDatabase.close()
        }
    }

    //**************favorite action**************************

    // get data /read
    fun getAllMovies(): ArrayList<MovieModel> {
        val arrayList = ArrayList<MovieModel>()
        val cursor = sqLiteDatabase.query(
            DATABASE_TABLE,
            null, null, null, null, null,
            BaseColumns._ID + " ASC", null
        )
        cursor.moveToFirst()
        var movieModel: MovieModel
        if (cursor.count > 0) {
            do {
                movieModel = MovieModel(
                    cursor.getInt(cursor.getColumnIndexOrThrow(BaseColumns._ID)),
                    "" + cursor.getString(cursor.getColumnIndexOrThrow(TITLE)),
                    "" + cursor.getString(cursor.getColumnIndexOrThrow(DESCRIPTION)),
                    "" + cursor.getString(cursor.getColumnIndexOrThrow(POSTER))
                )

                arrayList.add(movieModel)
                cursor.moveToNext()
            } while (!cursor.isAfterLast)
        }
        cursor.close()
        return arrayList
    }

    // delete data movie
    fun deleteMovie(id: Int): Int {
        return sqLiteDatabase.delete(TABLE_NAME, BaseColumns._ID + " = '" + id + "'", null)
    }


}