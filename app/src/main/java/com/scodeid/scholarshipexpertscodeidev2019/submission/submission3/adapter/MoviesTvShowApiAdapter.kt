/*
 * Copyright (c) 2019. SCODEID
 */

package com.scodeid.scholarshipexpertscodeidev2019.submission.submission3.adapter

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.scodeid.scholarshipexpertscodeidev2019.R
import com.scodeid.scholarshipexpertscodeidev2019.submission.submission3.api.ApiEndPoint.Companion.POSTER_IMAGE
import com.scodeid.scholarshipexpertscodeidev2019.submission.submission3.model.MoviesTvShowApiData
import kotlinx.android.synthetic.main.fragment_movie_dialog.*
import kotlinx.android.synthetic.main.item_movies_tv_shows.view.*

/**
 * @author
 * Created by scode on 15,July,2019
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
 *              _               _         _               _____
 *   ___ _   _| |__  _ __ ___ (_)___ ___(_) ___  _ __   |___ /
 * / __| | | | '_ \| '_ ` _ \| / __/ __| |/ _ \| '_ \    |_ \
 * \__ \ |_| | |_) | | | | | | \__ \__ \ | (_) | | | |  ___) |
 * |___/\__,_|_.__/|_| |_| |_|_|___/___/_|\___/|_| |_| |____/
 *
 *
 */

class MoviesTvShowApiAdapter (private val context : Context, private val arrayListMoviesTvShow : ArrayList<MoviesTvShowApiData>) : RecyclerView.Adapter<MoviesTvShowApiAdapter.ViewHolder>() {

    companion object{
        //limited recycler view item @later's will use pagination
        @JvmStatic
        val LIMIT = 10
        @JvmStatic
        val TAG_LOG: String = MoviesTvShowApiAdapter::class.java.simpleName
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_movies_tv_shows, parent, false))
    }

    override fun getItemCount(): Int {
        return if (arrayListMoviesTvShow.size > LIMIT) {
            LIMIT
        } else {
            arrayListMoviesTvShow.size
        }
    }

    @SuppressLint("PrivateResource")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.itemView.text_tv_movie_name.text = arrayListMoviesTvShow[position].name
        holder.itemView.text_tv_rate.text = arrayListMoviesTvShow[position].voteAverage.toString() //score /rate

        val imagePoster = arrayListMoviesTvShow[position].posterPath
        context.let {
            Glide.with(it)
                .asBitmap()
                .load(POSTER_IMAGE+"w185"+ imagePoster)
                .error(R.color.error_color_material_light)
                .format(DecodeFormat.PREFER_ARGB_8888)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .into(holder.itemView.image_tv_movie)
        }

        holder
            .itemView.image_tv_movie.setOnClickListener {
            Log.d(TAG_LOG + "Bind", "image ${holder.itemView.image_tv_movie} got clicked and Try opening dialog view")

            /**
             * DIALOG view movie catalogue
             */
            holder.movieDialog.setContentView(R.layout.fragment_movie_dialog)
            // bind onClick on there layout movie dialogUe

            holder.movieDialog.button_close.setOnClickListener {
                holder.movieDialog.dismiss()
            }
            //set image dialog movie
            // using imageResourceId
//                arrayListMovies[position].moviePicture.toInt()
//                .let { it1 -> holder.movieDialog.image_dialog_home.setImageResource(it1) }
            Glide.with(it)
                .asBitmap()
                .load(POSTER_IMAGE+"w342"+imagePoster)
                .error(R.color.error_color_material_light)
                .format(DecodeFormat.PREFER_ARGB_8888)
                .into(holder.movieDialog.image_dialog_home)

            holder.movieDialog.window?.attributes?.windowAnimations = R.style.AnimBottomTop
            holder.movieDialog.show()
            // end of dialog view
        }
        // end of image clicked


    }

    inner class ViewHolder(view : View) : RecyclerView.ViewHolder(view){
        var movieDialog = Dialog(context)
    }
}