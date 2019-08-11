/*
 * Copyright (c) 2019. SCODEID
 */

package com.scodeid.scholarshipexpertscodeidev2019.model.favorite

import android.annotation.SuppressLint
import android.content.Context
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns
import com.scodeid.scholarshipexpertscodeidev2019.database.ContractDatabase.MovieColumns.DESCRIPTION
import com.scodeid.scholarshipexpertscodeidev2019.database.ContractDatabase.MovieColumns.POSTER
import com.scodeid.scholarshipexpertscodeidev2019.database.ContractDatabase.MovieColumns.RELEASE
import com.scodeid.scholarshipexpertscodeidev2019.database.ContractDatabase.MovieColumns.TABLE_NAME_MOVIE
import com.scodeid.scholarshipexpertscodeidev2019.database.ContractDatabase.MovieColumns.TABLE_NAME_TV
import com.scodeid.scholarshipexpertscodeidev2019.database.ContractDatabase.MovieColumns.TITLE
import com.scodeid.scholarshipexpertscodeidev2019.database.ContractDatabase.MovieColumns.VOTE_AVERAGE
import com.scodeid.scholarshipexpertscodeidev2019.database.HelperDatabase

//import com.scodeid.scholarshipexpertscodeidev2019.database.ContractDatabase.MovieColumns.Companion.DESCRIPTION
//import com.scodeid.scholarshipexpertscodeidev2019.database.ContractDatabase.MovieColumns.Companion.POSTER
//import com.scodeid.scholarshipexpertscodeidev2019.database.ContractDatabase.MovieColumns.Companion.TITLE
//import com.scodeid.scholarshipexpertscodeidev2019.database.ContractDatabase.TABLE_MOVIES

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

class HelperModel
/**
 * CONSTRUCTOR
 */
private constructor(context: Context) {

    companion object {
        /**
         * DECLARE DATABASE VARIABLE
         */
        private const val DATABASE_TABLE_MOVIE = TABLE_NAME_MOVIE
        private const val DATABASE_TABLE_TV = TABLE_NAME_TV


        private lateinit var helperDatabase: HelperDatabase
        private lateinit var INSTANCE: HelperModel
        private lateinit var sqLiteDatabase: SQLiteDatabase
        /**
         * INITIALIZE DATABASE LATER'S
         */
        fun getInstance(context: Context): HelperModel {
            synchronized(SQLiteOpenHelper::class.java) {
                INSTANCE =
                    HelperModel(context)
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

    @Suppress("Unused")
    fun close() {
        // dunno why get re opened object i was try and catch /final y still get the error at cursor close
        // not properly on close , app not for close , but just throw an error then click back and try delete item is work
        // then i remove that close for cursor.close
        helperDatabase.close()

        if (sqLiteDatabase.isOpen) {
            sqLiteDatabase.close()
        }
    }

    //**************favorite action**************************

    @SuppressLint("Recycle")
    fun getAllMovies(): ArrayList<MovieModel> {
        val arrayList = ArrayList<MovieModel>()
        val cursor = sqLiteDatabase.query(
            DATABASE_TABLE_MOVIE,
            null, null, null, null, null,
            BaseColumns._ID + " ASC", null
        )
        cursor.moveToFirst()
        var movieModel: MovieModel
        if (cursor.count > 0) {
            do {
                movieModel = MovieModel(
                    cursor.getInt(cursor.getColumnIndexOrThrow(BaseColumns._ID)),
                    "" + cursor.getString(cursor.getColumnIndexOrThrow(RELEASE)),
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

    @SuppressLint("Recycle")
    fun getAllTv(): ArrayList<TvModel> {
        val arrayList = ArrayList<TvModel>()
        val cursor = sqLiteDatabase.query(
            DATABASE_TABLE_TV,
            null, null, null, null, null,
            BaseColumns._ID + " ASC", null
        )
        cursor.moveToFirst()
        var tvModel: TvModel
        if (cursor.count > 0) {
            do {
                tvModel = TvModel(
                    cursor.getInt(cursor.getColumnIndexOrThrow(BaseColumns._ID)),
                    "" + cursor.getString(cursor.getColumnIndexOrThrow(TITLE)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(VOTE_AVERAGE)),
                    "" + cursor.getString(cursor.getColumnIndexOrThrow(POSTER))
                )

                arrayList.add(tvModel)
                cursor.moveToNext()
            } while (!cursor.isAfterLast)
        }
        cursor.close()
        return arrayList
    }

    // delete data movie
    @Suppress("Unused")
    fun deleteMovie(id: Int): Int {
        return sqLiteDatabase.delete(TABLE_NAME_MOVIE, BaseColumns._ID + " = '" + id + "'", null)
    }

    // delete data TV
    @Suppress("Unused")
    fun deleteTv(id: Int): Int {
        return sqLiteDatabase.delete(TABLE_NAME_TV, BaseColumns._ID + " = '" + id + "'", null)
    }


}