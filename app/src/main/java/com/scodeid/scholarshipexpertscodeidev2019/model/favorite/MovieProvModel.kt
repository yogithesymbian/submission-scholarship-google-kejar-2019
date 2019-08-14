/*
 * Copyright (c) 2019. SCODEID
 */

package com.scodeid.scholarshipexpertscodeidev2019.model.favorite

import android.database.Cursor
import android.os.Build
import android.os.Parcel
import android.os.Parcelable
import android.provider.BaseColumns._ID
import androidx.annotation.RequiresApi
import com.scodeid.scholarshipexpertscodeidev2019.database.ContractDatabase
import com.scodeid.scholarshipexpertscodeidev2019.database.ContractDatabase.MovieColumns.getColumnInt
import com.scodeid.scholarshipexpertscodeidev2019.database.ContractDatabase.MovieColumns.getColumnString

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
class MovieProvModel : Parcelable {

    var id: Int = 0
    var title: String? = null
    var release :  String? = null
    var description :  String? = null
    var posterImage : String? = null



    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeInt(id)
        dest.writeString(title)
        dest.writeString(release)
        dest.writeString(description)
        dest.writeString(posterImage)
    }

    constructor()

    @RequiresApi(api = Build.VERSION_CODES.N)
    constructor(cursor: Cursor) {
        this.id = getColumnInt(cursor, _ID)
        this.title = getColumnString(cursor, ContractDatabase.MovieColumns.TITLE)
        this.release = getColumnString(cursor, ContractDatabase.MovieColumns.TITLE)
        this.description = getColumnString(cursor, ContractDatabase.MovieColumns.DESCRIPTION)
        this.posterImage = getColumnString(cursor, ContractDatabase.MovieColumns.DESCRIPTION)
    }

    constructor(id: Int, title: String, release: String, description: String, posterImage: String) {
        this.id = id
        this.title = title
        this.release = release
        this.description = description
        this.posterImage = posterImage
    }


    private constructor(`in`: Parcel) {
        id = `in`.readInt()
        title = `in`.readString()
        release= `in`.readString()
        description = `in`.readString()
        posterImage = `in`.readString()
    }

    companion object {
        @Suppress("Unused")
        @JvmField
        val CREATOR: Parcelable.Creator<MovieProvModel> = object : Parcelable.Creator<MovieProvModel> {
            override fun createFromParcel(source: Parcel): MovieProvModel {
                return MovieProvModel(source)
            }

            override fun newArray(size: Int): Array<MovieProvModel?> {
                return arrayOfNulls(size)
            }
        }
    }


}
