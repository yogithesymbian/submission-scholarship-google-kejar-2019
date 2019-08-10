/*
 * Copyright (c) 2019. SCODEID
 */

package com.scodeid.scholarshipexpertscodeidev2019.submission.submission4.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.provider.BaseColumns._ID
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
import com.scodeid.scholarshipexpertscodeidev2019.R
import com.scodeid.scholarshipexpertscodeidev2019.submission.submission3.api.ApiEndPoint.Companion.POSTER_IMAGE
import com.scodeid.scholarshipexpertscodeidev2019.submission.submission4.CustomOnItemClickListener
import com.scodeid.scholarshipexpertscodeidev2019.submission.submission4.MainFavoriteTvDetailActivity
import com.scodeid.scholarshipexpertscodeidev2019.submission.submission4.adapter.FavoriteAdapter.Companion.num1
import com.scodeid.scholarshipexpertscodeidev2019.submission.submission4.database.ContractDatabase.MovieColumns.TABLE_NAME_TV
import com.scodeid.scholarshipexpertscodeidev2019.submission.submission4.database.HelperDatabase
import com.scodeid.scholarshipexpertscodeidev2019.submission.submission4.model.TvModel
import kotlinx.android.synthetic.main.fragment_movie_dialog.*
import kotlinx.android.synthetic.main.item_movies_tv_shows.view.*

/**
 * @author
 * Created by scode on 01,August,2019
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

class FavoriteTvAdapter(var activity: Activity) : RecyclerView.Adapter<FavoriteTvAdapter.FavoriteTvViewHolder>() {

    companion object {
        val TAG_LOG: String = FavoriteTvAdapter::class.java.simpleName
    }
    // will save all data
    var listTvModel = ArrayList<TvModel>()
        set(listTv) {
            if (listTv.size > 0) {
                this.listTvModel.clear()
            }
            this.listTvModel.addAll(listTv)
            notifyDataSetChanged()
        }

    /**
     * add inside function favorite code developer.google android
     * remove/delete
     */

    private fun removeItemTv(position: Int) {
        this.listTvModel.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, this.listTvModel.size)
    }

    /**
     * implement of recycler
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteTvViewHolder {
        return FavoriteTvViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_movies_tv_shows_favorite, parent, false))
    }

    @SuppressLint("PrivateResource")
    override fun onBindViewHolder(holder: FavoriteTvViewHolder, position: Int) {
        val context = holder.itemView.context
        holder.textTitle.text = this.listTvModel[position].title
        holder.textDesc.text = this.listTvModel[position].voteAverage.toString()

        val animation = AnimationUtils.loadAnimation(context, R.anim.fade_scale)
        val id = this.listTvModel[position].id

        context.let {
            Glide.with(it)
                .asBitmap()
                .load("${POSTER_IMAGE}w185${listTvModel[position].posterImage}")
                .error(R.color.error_color_material_light)
                .format(DecodeFormat.PREFER_ARGB_8888)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .into(holder.itemView.image_tv_movie)
        }
        holder.itemView.setOnClickListener(CustomOnItemClickListener(position, object :
            CustomOnItemClickListener.OnItemClickCallback {
            override fun onItemClicked(view: View, position: Int) {
                val intent = Intent(activity, MainFavoriteTvDetailActivity::class.java)
                intent.putExtra(MainFavoriteTvDetailActivity.EXTRA_POSITION, position)
                intent.putExtra(MainFavoriteTvDetailActivity.EXTRA_TV, listTvModel[position])
                activity.startActivityForResult(intent, MainFavoriteTvDetailActivity.REQUEST_UPDATE)
            }
        }))

        val unFavorite = holder.itemView.checkbox_fav_tv
        unFavorite.setOnCheckedChangeListener { buttonView, isChecked ->

            val helperDatabase = HelperDatabase(context)
            val db = helperDatabase.writableDatabase

            if (position == 0) db.delete(TABLE_NAME_TV, "$_ID=?$num1", null)
            else db.delete(TABLE_NAME_TV, "$_ID=?$position", null)
            db.execSQL("DELETE FROM $TABLE_NAME_TV WHERE $_ID='$id'")
            db.close()

            buttonView.startAnimation(animation)
            if (isChecked) {
                removeItemTv(position)
                unFavorite.isChecked = false
            }
        }

        val movieDialog = Dialog(context)
        holder
            .itemView.image_tv_movie.setOnClickListener {
            Log.d(
                TAG_LOG + "Bind",
                "image ${holder.itemView.image_tv_movie} got clicked and Try opening dialog view"
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
                .load("${POSTER_IMAGE}w185${listTvModel[position].posterImage}")
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
        return this.listTvModel.size
    }

    inner class FavoriteTvViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal val textTitle: TextView = itemView.findViewById(R.id.text_tv_movie_name)
        internal val textDesc: TextView = itemView.findViewById(R.id.text_tv_rate)
    }
}
