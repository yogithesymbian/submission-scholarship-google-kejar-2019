/*
 * Copyright (c) 2019. SCODEID
 */

package com.scodeid.scholarshipexpertscodeidev2019.submission.submission1

/**
 * @author
 * Created by scode on 26,June,2019
 * Yogi Arif Widodo
 * www.dicoding.com/users/297307
 * www.github.com/yogithesymbian
 * SCODEID company,
 * Indonesia, East Borneo.
 * ==============================================================
 * Android Studio 3.3.2
 * Build # AI-182.5107.16.33.5314842, built on February 15, 2019
 * JRE: 1.8.0_152-release-1248-b01 amd64
 * JVM: OpenJDK 64-Bit Server VM by JetBrains s.r.o
 * Linux 4.19.0-kali5-amd64
 * ==============================================================
 */

// after i has learn about bug hunting with async / handler on dicoding.com it's make me know about skipped frame on logging
// a lot of code , :D

/**
 * CAN'T Create Handler on Thread so i can't use this code you (dicoding.com) and dunno how
 * so i use my code like these on my main movie catalogue
 */
//class DelayAsync : AsyncTask<Void, Void, Void>() {
//    private val TAG_LOG = "MainBugHuntingActivityJ"
//
//    override fun doInBackground(vararg voids: Void): Void? {
//        try {
//            Log.d(TAG_LOG, "DelayAysnc doInBackground for Movie Home and Detail's")
//
//            val movies = MovieCatalogueMainActivity()
//
//            movies.loadDataMovie() // loadData
//            movies.setMovieAdd() //setData
//            movies.bindMovieHome() //bindAction
//
//        } catch (e: InterruptedException) {
//            e.printStackTrace()
//        }
//
//        return null
//    }
//
//    override fun onPostExecute(aVoid: Void) {
//        super.onPostExecute(aVoid)
//        Log.d(TAG_LOG, "DelayAysnc Done")
//    }
//
//    override fun onCancelled() {
//        super.onCancelled()
//        Log.d(TAG_LOG, "DelayAsync Cancelled")
//    }
//
//}