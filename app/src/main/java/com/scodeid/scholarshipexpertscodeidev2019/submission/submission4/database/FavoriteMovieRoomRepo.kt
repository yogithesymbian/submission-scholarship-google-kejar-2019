/*
 * Copyright (c) 2019. SCODEID
 */

package com.scodeid.scholarshipexpertscodeidev2019.submission.submission4.database

import androidx.lifecycle.LiveData
import com.scodeid.scholarshipexpertscodeidev2019.submission.submission4.model.MovieRoomDao
import com.scodeid.scholarshipexpertscodeidev2019.submission.submission4.model.MovieRoomModel

/**
 * @author
 * Created by scode on 02,August,2019
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

class FavoriteMovieRoomRepo (private val movieRoomDao: MovieRoomDao) {
    val allMovies: LiveData<List<MovieRoomModel>> = movieRoomDao.getAllMovieRoom()


    fun deleteMovie(movieRoomModel: MovieRoomModel) {
        movieRoomDao.deleteMovies(movieRoomModel)
    }

    fun deleteSingle(movieRoomModel: MovieRoomModel){
        movieRoomDao.deleteMovies(movieRoomModel)
    }
    suspend fun insert(movieRoomModel: MovieRoomModel) {
        movieRoomDao.insert(movieRoomModel)
    }
}