/*
 * Copyright (c) 2019. SCODEID
 */

package com.scodeid.scholarshipexpertscodeidev2019.submission.submission2

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.scodeid.scholarshipexpertscodeidev2019.R
import com.scodeid.scholarshipexpertscodeidev2019.submission.model.MovieDataModelsRecyclerTv

/**
 * @author
 * Created by scode on 05,July,2019
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
 *            _               _         _               ____
 *  ___ _   _| |__  _ __ ___ (_)___ ___(_) ___  _ __   |___ \
 * / __| | | | '_ \| '_ ` _ \| / __/ __| |/ _ \| '_ \    __) |
 * \__ \ |_| | |_) | | | | | | \__ \__ \ | (_) | | | |  / __/
 * |___/\__,_|_.__/|_| |_| |_|_|___/___/_|\___/|_| |_| |_____|
 *
 */

class MoviesTvShowRecyclerAdapter internal constructor(private val context: Context) :
    RecyclerView.Adapter<MoviesTvShowRecyclerAdapter.CardViewMovieHolder>() {

    lateinit var listCardTvMovieRecycler: ArrayList<MovieDataModelsRecyclerTv>

    companion object {
        var tagLog = "MoviesTvShowRecyclerAdapter"
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewMovieHolder {
        return CardViewMovieHolder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.item_movies_tv_shows,
            parent,
            false
        )
    )
    }

    override fun getItemCount(): Int {
        return listCardTvMovieRecycler.size
    }

    override fun onBindViewHolder(holder: CardViewMovieHolder, position: Int) {
        /**
         * SET DATA on BIND /with
         */
        val image = listCardTvMovieRecycler[position].moviePictureTv?.toInt()
        //testing set with glide
        Glide.with(context)
            .asBitmap()
            .load(image)
            .apply(RequestOptions().override(100, 125))
            .into(holder.movieImagePosterHome)

        holder.movieName.text = listCardTvMovieRecycler[position].movieNameTv
        holder.movieRate.text = listCardTvMovieRecycler[position].movieScoreTv
//        holder.movieScoreImage.setOnClickListener {
////            val doInInBackGroundMovie = 100 //in just start
////            Handler().postDelayed(object : Runnable {
////                override fun run() {
////                    this.finish()
////                    frame_progress_tv_show.visibility = View.GONE //for other background process timing set image on adapter
////                    Log.d(TAG_LOG,"Loading ... for get and load data in background")
////                    card_tv_show.visibility = View.VISIBLE
////
////                }
////
////                private fun finish() {
////
////                }
////            }, doInInBackGroundMovie.toLong())
//        }

//        for (i in 1..10) // :D cause i know what /H' much limit my list movies :D
//            holder.movieIndex.text = "$i"

    }

    inner class CardViewMovieHolder(view: View) : RecyclerView.ViewHolder(view){
        // initialize without kotlinX cause a reason
        var movieImagePosterHome : ImageView = view.findViewById(R.id.image_tv_movie)
        var movieName :TextView= view.findViewById(R.id.text_tv_movie_name)
        var movieRate:TextView = view.findViewById(R.id.text_tv_rate)
//        var movieScoreImage: ImageView = view.findViewById(R.id.image_score)
//        var movieScoreText : TextView = view.findViewById(R.id.text_score_tv)
//        var moviePrice = view.findViewById<View>(R.id.text_tv_price)
//        var movieIndex:TextView = view.findViewById(R.id.text_tv_index)
    }
}