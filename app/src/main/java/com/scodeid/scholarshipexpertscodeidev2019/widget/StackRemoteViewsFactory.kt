/*
 * Copyright (c) 2019. SCODEID
 */

package com.scodeid.scholarshipexpertscodeidev2019.widget

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.graphics.Bitmap
import android.os.Binder
import android.os.Bundle
import android.util.Log
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import com.bumptech.glide.Glide
import com.scodeid.scholarshipexpertscodeidev2019.R
import com.scodeid.scholarshipexpertscodeidev2019.database.ContractDatabase


/**
 * @author
 * Created by scode on 15,August,2019
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
class StackRemoteViewsFactory(private val context: Context) : RemoteViewsService.RemoteViewsFactory {

    private var cursor: Cursor? = null
    private var bitmapMovie = ArrayList<Bitmap>()
    private var title = ArrayList<String>()

    override fun onCreate() {
        // ...
    }

    @SuppressLint("Recycle")
    override fun onDataSetChanged() {

        Log.d(TAG_LOG, "ON_DATA_SET_CHANGE_WIDGET")

        val threadQuerying = object : Thread() {
            override fun run() {

                val identityToken = Binder.clearCallingIdentity()

                if (cursor != null) {
                    cursor?.close()
                }
                // i choose query to favorite movies
                cursor = context.contentResolver.query(
                    ContractDatabase.MovieColumns.CONTENT_URI_MOVIE,
                    null,
                    null,
                    null,
                    null
                )

                Binder.restoreCallingIdentity(identityToken)

            }
        }

        threadQuerying.start()
        try {
            threadQuerying.join()
        } catch (e: Throwable) {
        }

        /**
         * set ArrayList BitMap
         */
        if (cursor != null) {
            if (cursor?.moveToFirst()!!) {
                do {
                    val getImages =
                        "${cursor?.getString(cursor!!.getColumnIndexOrThrow(ContractDatabase.MovieColumns.POSTER))}"
                    val titleImage =
                        "${cursor?.getString(cursor!!.getColumnIndexOrThrow(ContractDatabase.MovieColumns.TITLE))}"
                    Log.d(
                        TAG_LOG,
                        """ \n
                            title is $titleImage
                            image is $getImages
                            
                        """.trimIndent()
                    )

                    val posterToBitmap = Glide.with(context)
                        .asBitmap()
                        .load(getImages)
                        .submit()
                    bitmapMovie.add(posterToBitmap.get())
                    title.add(titleImage)

                } while (cursor?.moveToNext()!!)
            }
        }

    }

    override fun onDestroy() {
        cursor?.close()
    }

    override fun getCount(): Int {
        return cursor?.count!!
    }

    override fun getViewAt(position: Int): RemoteViews {

        val remoteViews =
            RemoteViews(context.packageName, R.layout.widget_item_movies)

        remoteViews.setImageViewBitmap(R.id.imageView, bitmapMovie[position])
        val extras = Bundle()
        extras.putInt(
            StackImageMovies.EXTRA_ITEM,
            position
        )
        extras.putString(
            StackImageMovies.EXTRA_TITLE,
            title[position]
        )

        val fillInIntent = Intent()
        fillInIntent.putExtras(extras)

        remoteViews.setOnClickFillInIntent(R.id.imageView, fillInIntent)
        return remoteViews

    }

    override fun getLoadingView(): RemoteViews? {
        return null
    }

    override fun getViewTypeCount(): Int {
        return 1
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun hasStableIds(): Boolean {
        return false
    }

    companion object {
        const val TAG_LOG: String = "RemoteViews"
    }
}

