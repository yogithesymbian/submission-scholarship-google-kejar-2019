/*
 * Copyright (c) 2019. SCODEID
 */

package com.scodeid.scholarshipexpertscodeidev2019.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.Handler
import androidx.annotation.RequiresApi
import com.scodeid.scholarshipexpertscodeidev2019.database.ContractDatabase.AUTHORITY
import com.scodeid.scholarshipexpertscodeidev2019.database.ContractDatabase.MovieColumns.CONTENT_URI_MOVIE
import com.scodeid.scholarshipexpertscodeidev2019.database.ContractDatabase.MovieColumns.TABLE_NAME_MOVIE
import com.scodeid.scholarshipexpertscodeidev2019.helper.HelperModel
import com.scodeid.scholarshipexpertscodeidev2019.homeFavorite.MainFavoriteMovieActivity
import java.util.*

/**
 * @author
 * Created by scode on 10,August,2019
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

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
@RequiresApi(api = Build.VERSION_CODES.N)
class FavMovieProvider : ContentProvider() {

    private lateinit var helperModel: HelperModel

    @RequiresApi(api = Build.VERSION_CODES.N)
    override fun onCreate(): Boolean {
        helperModel = HelperModel.getInstance(context)
        return false
    }

    override fun query(
        uri: Uri,
        projection: Array<String>?,
        selection: String?,
        selectionArgs: Array<String>?,
        sortOrder: String?
    ): Cursor? {
        helperModel.open()
        val cursor: Cursor?
        cursor = when (uriMatcher.match(uri)) {
            MOVIE -> helperModel.queryProviderMovie()
            MOVIE_ID -> helperModel.queryByIdProviderMovie(uri.lastPathSegment)
            else -> throw IllegalArgumentException("Unknown URI")
        }
        return cursor
    }

    override fun getType(uri: Uri): String? {
        return null
    }

    override fun insert(uri: Uri, values: ContentValues): Uri? {
        helperModel.open()
        val added: Long = when (uriMatcher.match(uri)) {
            MOVIE -> helperModel.insertProviderMovie(values)
            else -> throw IllegalArgumentException("Unknown URI: $uri")
        }
        Objects.requireNonNull(context).contentResolver.notifyChange(
            CONTENT_URI_MOVIE, MainFavoriteMovieActivity.DataObserver(
                Handler(),
                context
            )
        )
        return Uri.parse("$CONTENT_URI_MOVIE/$added")
    }


    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        helperModel.open()
        val delete: Int = when (uriMatcher.match(uri)) {
            MOVIE_ID -> helperModel.deleteProviderMovie(uri.lastPathSegment)
            else -> throw IllegalArgumentException("Unknown URI: $uri")
        }
        Objects.requireNonNull(context).contentResolver.notifyChange(
            CONTENT_URI_MOVIE, MainFavoriteMovieActivity.DataObserver(
                Handler(),
                context
            )
        )
        return delete
    }

    override fun update(uri: Uri, values: ContentValues?, selection: String?, selectionArgs: Array<String>?): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
    companion object {

        private const val MOVIE = 1
        private const val MOVIE_ID = 2
        private val uriMatcher = UriMatcher(UriMatcher.NO_MATCH)

        init {
            uriMatcher.addURI(AUTHORITY, TABLE_NAME_MOVIE, MOVIE)
            uriMatcher.addURI(AUTHORITY, "$TABLE_NAME_MOVIE/#", MOVIE_ID)
        }
    }

}
