/*
 * Copyright (c) 2019. SCODEID
 */

package com.scodeid.scholarshipexpertscodeidev2019.submission.submission3.adapter

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.scodeid.scholarshipexpertscodeidev2019.R
import com.scodeid.scholarshipexpertscodeidev2019.submission.ItemClickRecyclerSupport
import com.scodeid.scholarshipexpertscodeidev2019.submission.submission3.MoviesTvWapiHomeDetailFragment
import com.scodeid.scholarshipexpertscodeidev2019.submission.submission3.MoviesTvWapiHomeFragment
import com.scodeid.scholarshipexpertscodeidev2019.submission.submission3.MoviesTvWapiHomeFragment.Companion.insertFavoriteTv
import com.scodeid.scholarshipexpertscodeidev2019.submission.submission3.api.ApiEndPoint.Companion.POSTER_IMAGE
import com.scodeid.scholarshipexpertscodeidev2019.submission.submission3.model.MoviesTvShowApiData
import kotlinx.android.synthetic.main.fragment_movie_dialog.*
import kotlinx.android.synthetic.main.fragment_movies_tv_show.view.*
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

class MoviesTvShowApiAdapter (
    private val arrayListMoviesTvShow : ArrayList<MoviesTvShowApiData>
) : RecyclerView.Adapter<MoviesTvShowApiAdapter.ViewHolder>() {

    companion object{
        //limited recycler view item @later's will use pagination
        const val LIMIT = 10
        val TAG_LOG: String = MoviesTvShowApiAdapter::class.java.simpleName
    }
    fun setData(itemsMovie: ArrayList<MoviesTvShowApiData>)
    {
        arrayListMoviesTvShow.clear()
        arrayListMoviesTvShow.addAll(itemsMovie)
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view =  ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_movies_tv_shows, parent, false))
        val context = parent.context
        ItemClickRecyclerSupport
            .addTo(parent.recycler_view_tv_show)
            .setOnItemClickListener(object : ItemClickRecyclerSupport.OnItemClickListener{

                override fun onItemClicked(recyclerView: RecyclerView, position: Int, v: View) {
                    openingTvShowDetail(arrayListMoviesTvShow[position], context)
                    Log.d(TAG_LOG,"Try opening something about the detail tv show" +arrayListMoviesTvShow[position].name)
                }

            })
        return view
    }

    private fun openingTvShowDetail(moviesTvShowApiData: MoviesTvShowApiData, context: Context) {

        // instance DetailCategoryFragment
        val mMoviesTvShowDetailFragment = MoviesTvWapiHomeDetailFragment()

        // tx tx data between the fragment using Bundle
        val mBundle = Bundle()

        mBundle.putParcelable(MoviesTvWapiHomeDetailFragment.EXTRA_TV_DETAILS, moviesTvShowApiData)
        mMoviesTvShowDetailFragment.arguments = mBundle

        //manage the fragment manager in this fragment
        val mFragmentManager = (context as AppCompatActivity).supportFragmentManager

        //check for replace and go
        // use FragTransaction
        val mFragmentTransaction = mFragmentManager.beginTransaction()
        // replace the container frameLayout
        mFragmentTransaction.replace(R.id.frame_container_tv_show, mMoviesTvShowDetailFragment,  MoviesTvWapiHomeDetailFragment::class.java.simpleName)
        // set back stack null to get on back pressed !exit
        mFragmentTransaction.addToBackStack(null)
        // commit the fragment
        mFragmentTransaction.commit()
        Log.d(TAG_LOG,"Fragment has commit")
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

        val title = arrayListMoviesTvShow[position].name
        val voteAverage = arrayListMoviesTvShow[position].voteAverage //score /rate
        val imagePoster = arrayListMoviesTvShow[position].posterPath
        val context = holder.itemView.context

        val movieDialog = Dialog(context)
        val animation = AnimationUtils.loadAnimation(context, R.anim.fade_scale)

        holder.itemView.checkbox_fav_tv.setOnCheckedChangeListener { buttonView, isChecked ->
            buttonView.startAnimation(animation)
            if (isChecked) MoviesTvWapiHomeFragment.initFavoriteTvParam(title, voteAverage,  imagePoster, context,  ::insertFavoriteTv)
            else Toast.makeText(context, "Sorry ,Delete item at this time only on favorite view, you cant do it at here.",
                Toast.LENGTH_LONG)
                .show()
        }

        holder.itemView.text_tv_movie_name.text = title
        holder.itemView.text_tv_rate.text = voteAverage.toString()

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
             * override the layout movie with tv_show
             */
            movieDialog.setContentView(R.layout.fragment_movie_dialog)
            // bind onClick on there layout movie dialogUe

            movieDialog.button_close.setOnClickListener {
                movieDialog.dismiss()
            }

            Glide.with(it)
                .asBitmap()
                .load(POSTER_IMAGE+"w342"+imagePoster)
                .error(R.color.error_color_material_light)
                .format(DecodeFormat.PREFER_ARGB_8888)
                .into(movieDialog.image_dialog_home)

            movieDialog.window?.attributes?.windowAnimations = R.style.AnimBottomTop
            movieDialog.show()
            // end of dialog view
        }
        // end of image clicked
    }

    inner class ViewHolder(view : View) : RecyclerView.ViewHolder(view)

}