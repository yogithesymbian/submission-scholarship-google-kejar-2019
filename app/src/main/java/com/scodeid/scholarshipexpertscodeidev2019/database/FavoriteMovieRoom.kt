/*
 * Copyright (c) 2019. SCODEID
 */

package com.scodeid.scholarshipexpertscodeidev2019.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.scodeid.scholarshipexpertscodeidev2019.model.favorite.MovieRoomDao
import com.scodeid.scholarshipexpertscodeidev2019.model.favorite.MovieRoomModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

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

@Database(entities = [MovieRoomModel::class], version = 1, exportSchema = false)
abstract class FavoriteMovieRoom : RoomDatabase() {
    abstract fun movieRoomDao(): MovieRoomDao

    companion object {
        @Volatile
        private var INSTANCE : FavoriteMovieRoom? = null

        fun getDatabase(context: Context, scope: CoroutineScope) : FavoriteMovieRoom {

           return INSTANCE ?: synchronized(this) {
               val instance = Room.databaseBuilder(
                   context.applicationContext,
                   FavoriteMovieRoom::class.java,
                   "DbMovie.db"
               )
                   .fallbackToDestructiveMigration()
                   .addCallback(FavoriteDatabaseCallBack(scope))
                   .build()
               INSTANCE = instance
               instance
           }
        }

        private class FavoriteDatabaseCallBack(
            private val scope: CoroutineScope
        ) : RoomDatabase.Callback() {

            override fun onOpen(db: SupportSQLiteDatabase) {
                super.onOpen(db)

                INSTANCE?.let { database ->
                    scope.launch {
                        populateDatabase(database.movieRoomDao())
                    }
                }
            }
        }

        @Suppress("Unused")
        fun populateDatabase(@Suppress("UNUSED_PARAMETER") movieRoomDao: MovieRoomDao) {
//            movieRoomDao.deleteAll()

//            var movieRoomModel = MovieRoomModel(2,"yogi","arif","https://image.tmdb.org/t/p/w185/dzBtMocZuJbjLOXvrl4zGYigDzh.jpg")
//            movieRoomDao.insert(movieRoomModel)
//            movieRoomModel = MovieRoomModel(4,"yogi","arif","https://image.tmdb.org/t/p/w185/dzBtMocZuJbjLOXvrl4zGYigDzh.jpg")
//            movieRoomDao.insert(movieRoomModel)
        }
    }
}