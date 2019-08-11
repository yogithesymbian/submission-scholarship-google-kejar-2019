/*
 * Copyright (c) 2019. SCODEID
 */

package com.scodeid.scholarshipexpertscodeidev2019.model.favorite

import android.database.Cursor
import android.os.Build
import android.os.Parcel
import android.os.Parcelable
import android.provider.BaseColumns
import androidx.annotation.RequiresApi
import com.scodeid.scholarshipexpertscodeidev2019.database.ContractDatabase

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
class TvProvModel : Parcelable {

    var id: Int = 0
    var title: String? = null
    var voteAverage :  Int = 0
    var posterImage : String? = null



    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeInt(id)
        dest.writeString(title)
        dest.writeInt(voteAverage)
        dest.writeString(posterImage)
    }

    constructor()

    @RequiresApi(api = Build.VERSION_CODES.N)
    constructor(cursor: Cursor) {
        this.id = ContractDatabase.MovieColumns.getColumnInt(cursor, BaseColumns._ID)
        this.title = ContractDatabase.MovieColumns.getColumnString(cursor, ContractDatabase.MovieColumns.TITLE)
        this.voteAverage =
            ContractDatabase.MovieColumns.getColumnInt(cursor, ContractDatabase.MovieColumns.VOTE_AVERAGE)
        this.posterImage =
            ContractDatabase.MovieColumns.getColumnString(cursor, ContractDatabase.MovieColumns.DESCRIPTION)
    }

    constructor(id: Int, title: String, voteAverage: Int, posterImage: String) {
        this.id = id
        this.title = title
        this.voteAverage = voteAverage
        this.posterImage = posterImage
    }


    private constructor(`in`: Parcel) {
        id = `in`.readInt()
        title = `in`.readString()
        voteAverage = `in`.readInt()
        posterImage = `in`.readString()
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<TvProvModel> = object : Parcelable.Creator<TvProvModel> {
            override fun createFromParcel(source: Parcel): TvProvModel {
                return TvProvModel(source)
            }

            override fun newArray(size: Int): Array<TvProvModel?> {
                return arrayOfNulls(size)
            }
        }
    }


}
