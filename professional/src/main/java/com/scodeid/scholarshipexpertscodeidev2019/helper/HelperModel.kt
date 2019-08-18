/*
 * Copyright (c) 2019. SCODEID
 */

package com.scodeid.scholarshipexpertscodeidev2019.helper

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns._ID
import com.scodeid.scholarshipexpertscodeidev2019.database.HelperDatabase
import com.scodeid.yomoviecommon.database.ContractDatabase.MovieColumns.TABLE_NAME_MOVIE
import com.scodeid.yomoviecommon.database.ContractDatabase.MovieColumns.TABLE_NAME_TV


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

    // delete data movie
    @Suppress("Unused")
    fun deleteMovie(id: Int): Int {
        return sqLiteDatabase.delete(TABLE_NAME_MOVIE, "$_ID = '$id'", null)
    }

    // delete data TV
    @Suppress("Unused")
    fun deleteTv(id: Int): Int {
        return sqLiteDatabase.delete(TABLE_NAME_TV, "$_ID = '$id'", null)
    }

    /**
     * MOVIE_FUNCTION
     */
    fun queryByIdProviderMovie(id: String): Cursor {
        return sqLiteDatabase.query(DATABASE_TABLE_MOVIE, null, "$_ID = ?", arrayOf(id), null, null, null, null)
    }

    fun queryProviderMovie(): Cursor {
        return sqLiteDatabase.query(DATABASE_TABLE_MOVIE, null, null, null, null, null, "$_ID ASC")
    }

    fun insertProviderMovie(values: ContentValues): Long {
        return sqLiteDatabase.insert(DATABASE_TABLE_MOVIE, null, values)
    }

    fun deleteProviderMovie(id: String): Int {
        return sqLiteDatabase.delete(DATABASE_TABLE_MOVIE, "$_ID = ?", arrayOf(id))
    }

    /**
     * TV_FUNCTION
     */
    fun queryByIdProviderTv(id: String): Cursor {
        return sqLiteDatabase.query(DATABASE_TABLE_TV, null, "$_ID = ?", arrayOf(id), null, null, null, null)
    }

    fun queryProviderTv(): Cursor {
        return sqLiteDatabase.query(DATABASE_TABLE_TV, null, null, null, null, null, "$_ID ASC")
    }

    fun insertProviderTv(values: ContentValues): Long {
        return sqLiteDatabase.insert(DATABASE_TABLE_TV, null, values)
    }

    fun deleteProviderTv(id: String): Int {
        return sqLiteDatabase.delete(DATABASE_TABLE_TV, "$_ID = ?", arrayOf(id))
    }

}