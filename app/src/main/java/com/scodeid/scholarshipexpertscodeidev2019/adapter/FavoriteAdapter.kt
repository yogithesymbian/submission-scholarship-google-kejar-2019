/*
 * Copyright (c) 2019. SCODEID
 */

package com.scodeid.scholarshipexpertscodeidev2019.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.scodeid.scholarshipexpertscodeidev2019.CustomOnItemClickListener
import com.scodeid.scholarshipexpertscodeidev2019.R
import com.scodeid.scholarshipexpertscodeidev2019.database.ContractDatabase.MovieColumns.CONTENT_URI_MOVIE
import com.scodeid.scholarshipexpertscodeidev2019.homeFavorite.MainFavoriteMovieDetailActivity
import com.scodeid.scholarshipexpertscodeidev2019.model.favorite.MovieProvModel
import kotlinx.android.synthetic.main.fragment_movie_dialog.*
import kotlinx.android.synthetic.main.item_movies_favorite.view.*
import java.util.*

/**
 * @author
 * Created by scode on 30,July,2019
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
@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class FavoriteAdapter(var activity: Activity) : RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {

    companion object {
        val TAG_LOG: String = FavoriteAdapter::class.java.simpleName
    }

    // will save all data
    var listMovieModel = ArrayList<MovieProvModel>()
        set(listMovies) {
            if (listMovies.size > 0) this.listMovieModel.clear()
            this.listMovieModel.addAll(listMovies)
            notifyDataSetChanged()
        }

    fun setListMovie(listMovies: ArrayList<MovieProvModel>) {
        if (listMovies.size > 0) this.listMovieModel.clear()
        this.listMovieModel.addAll(listMovies)
        notifyDataSetChanged()
    }

    /**
     * add inside function favorite code developer.google android
     * remove/delete
     */

    fun removeItemMovies(position: Int) {
        this.listMovieModel.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, this.listMovieModel.size)
    }

    /**
     * implement of recycler
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        return FavoriteViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_movies_favorite,
                parent,
                false
            )
        )
    }

    @SuppressLint("PrivateResource")
    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        val context = holder.itemView.context
        val animation = AnimationUtils.loadAnimation(context, R.anim.fade_scale)

        val uri = Uri.parse("$CONTENT_URI_MOVIE/${listMovieModel[position].id}")

        holder.release.text = this.listMovieModel[position].release
        holder.textTitle.text = this.listMovieModel[position].title
        holder.textDesc.text = this.listMovieModel[position].description
        context.let {
            Glide.with(it)
                .asBitmap()
                .load(listMovieModel[position].posterImage)
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
                        val intent = Intent(activity, MainFavoriteMovieDetailActivity::class.java)
                        intent.data = uri

                        intent.putExtra(MainFavoriteMovieDetailActivity.EXTRA_POSITION, position)
                        intent.putExtra(MainFavoriteMovieDetailActivity.EXTRA_MOVIE, listMovieModel[position])

                        activity.startActivityForResult(intent, MainFavoriteMovieDetailActivity.REQUEST_UPDATE)
                    }
                })
        )
        val unFavorite = holder.itemView.checkbox_fav_movie_favorite
        unFavorite.setOnCheckedChangeListener { buttonView, isChecked ->
            buttonView.startAnimation(animation)
            if (isChecked) {
                context.contentResolver.delete(Objects.requireNonNull<Uri>(uri), null, null)
                removeItemMovies(position)
                unFavorite.isChecked = false
            }
        }

        val movieDialog = Dialog(context)
        holder
            .itemView.image_movie.setOnClickListener {
            Log.d(
                TAG_LOG + "Bind",
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
                .load(listMovieModel[position].posterImage)
                .error(R.color.error_color_material_light)
                .format(DecodeFormat.PREFER_ARGB_8888)
                .into(movieDialog.image_dialog_home)

            movieDialog.window?.attributes?.windowAnimations = R.style.AnimBottomTop
            movieDialog.show()
//             end of dialog view
        }
        // end of image clicked
    }

    override fun getItemCount(): Int {
        return this.listMovieModel.size
    }

    inner class FavoriteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal val textTitle: TextView = itemView.findViewById(R.id.text_movie_name)
        internal val textDesc: TextView = itemView.findViewById(R.id.text_overview)
        internal val release: TextView = itemView.findViewById(R.id.text_movies_release)
    }
}
