/*
 * Copyright (c) 2019. SCODEID
 */

@file:Suppress("DEPRECATION")

package com.scodeid.scholarshipexpertscodeidev2019.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.scodeid.scholarshipexpertscodeidev2019.CustomOnItemClickListener
import com.scodeid.scholarshipexpertscodeidev2019.R
import com.scodeid.scholarshipexpertscodeidev2019.homeFavorite.MainFavoriteMovieRoomActivity
import com.scodeid.scholarshipexpertscodeidev2019.homeFavorite.MainFavoriteMovieRoomActivity.Companion.deleteFavoriteMovie
import com.scodeid.scholarshipexpertscodeidev2019.homeFavorite.MainFavoriteMovieRoomDeleteActivity
import com.scodeid.scholarshipexpertscodeidev2019.model.favorite.MovieRoomModel
import com.scodeid.scholarshipexpertscodeidev2019.model.favorite.MovieRoomView
import kotlinx.android.synthetic.main.fragment_movie_dialog.*
import kotlinx.android.synthetic.main.item_movies_favorite.view.*

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

class FavoriteMovieRoomAdapter(
    var activity: Activity
) : RecyclerView.Adapter<FavoriteMovieRoomAdapter.FavoriteMovieRoomViewHolder>() {

    //    lateinit var activity: Activity
//    private var listMovieRoom = emptyList<MovieRoomModel>()
    private var listMovieRoom = ArrayList<MovieRoomModel>()
    //    private lateinit var favoriteMovieRoom: FavoriteMovieRoom
    private lateinit var movieRoomView: MovieRoomView
//    private lateinit var movieRoomDao: MovieRoomDao

    inner class FavoriteMovieRoomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal val textTitle: TextView = itemView.findViewById(R.id.text_movie_name)
        internal val textDesc: TextView = itemView.findViewById(R.id.text_overview)
        internal val release: TextView = itemView.findViewById(R.id.text_movies_release)
    }

    private fun removeItemMovies(
        position: Int
    ) {
        this.listMovieRoom.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, this.listMovieRoom.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteMovieRoomViewHolder {
        val fav = FavoriteMovieRoomViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_movies_favorite,
                parent,
                false
            )
        )
        movieRoomView = ViewModelProviders.of(parent.context as FragmentActivity).get(MovieRoomView::class.java)
        return fav
    }

    override fun getItemCount(): Int = listMovieRoom.size

    @SuppressLint("PrivateResource")
    override fun onBindViewHolder(holder: FavoriteMovieRoomViewHolder, position: Int) {
        val context = holder.itemView.context

        val id = this.listMovieRoom[position].id
        val title = this.listMovieRoom[position].titleMovie
        val overview = this.listMovieRoom[position].overview
        val poster = listMovieRoom[position].poster
        val release = listMovieRoom[position].release

        val animation = AnimationUtils.loadAnimation(context, R.anim.fade_scale)
        val unFavorite = holder.itemView.checkbox_fav_movie_favorite
        unFavorite.setOnCheckedChangeListener { buttonView, isChecked ->
            buttonView.startAnimation(animation)
            if (isChecked) {
                removeItemMovies(position)
                MainFavoriteMovieRoomActivity.initFavoriteParam(
                    id,
                    release,
                    title,
                    overview,
                    poster,
                    context,
                    ::deleteFavoriteMovie
                )
                unFavorite.isChecked = false
            }
        }

        holder.textTitle.text = title
        holder.textDesc.text = overview
        holder.release.text = release
        context.let {
            Glide.with(it)
                .asBitmap()
                .load(poster)
                .error(R.color.error_color_material_light)
                .format(DecodeFormat.PREFER_ARGB_8888)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .into(holder.itemView.image_movie)
        }
        holder.itemView.card_item_favorite.setOnClickListener(
            CustomOnItemClickListener(
                position,
                object :
                    CustomOnItemClickListener.OnItemClickCallback {
                    override fun onItemClicked(view: View, position: Int) {
                        val intent = Intent(activity, MainFavoriteMovieRoomDeleteActivity::class.java)
                        intent.putExtra(MainFavoriteMovieRoomDeleteActivity.EXTRA_POSITION, position)
                        intent.putExtra(MainFavoriteMovieRoomDeleteActivity.EXTRA_MOVIE, listMovieRoom[position])
                        activity.startActivityForResult(intent, MainFavoriteMovieRoomDeleteActivity.REQUEST_UPDATE)
                    }
                })
        )

        val movieDialog = Dialog(context)
        holder
            .itemView.image_movie.setOnClickListener {
            Log.d(
                FavoriteAdapter.TAG_LOG + "Bind",
                "image ${holder.itemView.image_movie} got clicked and Try opening dialog view"
            )
            /**
             * DIALOG view movie catalogue
             * override the layout movie with favorite movie
             */
            movieDialog.setContentView(R.layout.fragment_movie_dialog)
            // bind onClick on there layout movie dialogUe

            movieDialog.button_close.setOnClickListener {
                movieDialog.dismiss()
            }
            Glide.with(it)
                .asBitmap()
                .load(poster)
                .error(R.color.error_color_material_light)
                .format(DecodeFormat.PREFER_ARGB_8888)
                .into(movieDialog.image_dialog_home)

            movieDialog.window?.attributes?.windowAnimations = R.style.AnimBottomTop
            movieDialog.show()
//             end of dialog view
        }
        // end of image clicked
    }

    internal fun setMovieRooms(movieRoomModel: List<MovieRoomModel>) {
        this.listMovieRoom = movieRoomModel as ArrayList<MovieRoomModel>
        notifyDataSetChanged()
    }

}