/*
 * Copyright (c) 2019. SCODEID
 */

package com.scodeid.scholarshipexpertscodeidev2019.submission.view

import com.scodeid.scholarshipexpertscodeidev2019.submission.model.MovieDataModels
import com.scodeid.scholarshipexpertscodeidev2019.submission.model.MovieDataModelsRecycler

/**
 * @author
 * Created by scode on 26,June,2019
 * Yogi Arif Widodo
 * www.dicoding.com/users/297307
 * www.github.com/yogithesymbian
 * SCODEID company,
 * Indonesia, East Borneo.
 * ==============================================================
Android Studio 3.4.2
Build #AI-183.6156.11.34.5692245, built on June 27, 2019
JRE: 1.8.0_152-release-1343-b16-5323222 amd64
JVM: OpenJDK 64-Bit Server VM by JetBrains s.r.o
Linux 4.19.0-kali5-amd64
 * ==============================================================
 */

interface MovieViewViewers
{
    fun allMovieData(models: MovieDataModels)
    fun homeMovies(models: MovieDataModelsRecycler)

//    fun maintenanceNotify(context: Context)
//    {
//        Snackbar.make(context.theme,"Sorry this feature still have maintenance ", Snackbar.LENGTH_LONG)
//            .show()
//    }
}