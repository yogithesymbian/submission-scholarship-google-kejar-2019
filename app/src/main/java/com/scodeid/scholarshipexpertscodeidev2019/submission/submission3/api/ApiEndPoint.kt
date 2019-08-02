/*
 * Copyright (c) 2019. SCODEID
 */

package com.scodeid.scholarshipexpertscodeidev2019.submission.submission3.api

/**
 * @author
 * Created by scode on 13,July,2019
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
 *              _               _         _               _____
 *   ___ _   _| |__  _ __ ___ (_)___ ___(_) ___  _ __   |___ /
 * / __| | | | '_ \| '_ ` _ \| / __/ __| |/ _ \| '_ \    |_ \
 * \__ \ |_| | |_) | | | | | | \__ \__ \ | (_) | | | |  ___) |
 * |___/\__,_|_.__/|_| |_| |_|_|___/___/_|\___/|_| |_| |____/
 *
 *
 */

class ApiEndPoint {
    companion object{
        const val API_KEY_V3_AUTH = "10494fa60da45dee76b53c177ada8d19"
//        val API_READ_ACCESS_TOKEN_V4_AUTH = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIxMDQ5NGZhNjBkYTQ1ZGVlNzZiNTNjMTc3YWRhOGQxOSIsInN1YiI6IjVkMjcxZmIyNWY2YzQ5MDJjMzVjZDE2ZSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.f5vy18cwvMcwoC4o1ibEac9FBBRw7Etc5f5tlzGbx-Y"

        const val SERVER_MOVIES = "https://api.themoviedb.org/3/discover/movie?api_key={API_KEY}&language={LANGUAGE}"
        const val SERVER_TV_SHOW = "https://api.themoviedb.org/3/discover/tv?api_key={API_KEY}&language={LANGUAGE}"

        /**
         * SIZE IMAGE
         * w92, w154, w185, w342, w500, w780, dan original.
         */
        const val POSTER_IMAGE = "https://image.tmdb.org/t/p/"
    }
}