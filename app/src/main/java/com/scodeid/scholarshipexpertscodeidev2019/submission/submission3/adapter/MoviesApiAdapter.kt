/*
 * Copyright (c) 2019. SCODEID
 */

package com.scodeid.scholarshipexpertscodeidev2019.submission.submission3.adapter

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.scodeid.scholarshipexpertscodeidev2019.R
import com.scodeid.scholarshipexpertscodeidev2019.submission.ItemClickRecyclerSupport
import com.scodeid.scholarshipexpertscodeidev2019.submission.MovieCatalogueDetailActivity
import com.scodeid.scholarshipexpertscodeidev2019.submission.submission3.MoviesWapiHomeFragment
import com.scodeid.scholarshipexpertscodeidev2019.submission.submission3.MoviesWapiHomeFragment.Companion.insertFavoriteMovie
import com.scodeid.scholarshipexpertscodeidev2019.submission.submission3.api.ApiEndPoint.Companion.POSTER_IMAGE
import com.scodeid.scholarshipexpertscodeidev2019.submission.submission3.model.MoviesApiData
import kotlinx.android.synthetic.main.fragment_movie_dialog.*
import kotlinx.android.synthetic.main.fragment_movies_home_recycler.view.*
import kotlinx.android.synthetic.main.item_movies.view.*


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

class MoviesApiAdapter internal constructor(
    private val arrayListMovies: ArrayList<MoviesApiData>
) : RecyclerView.Adapter<MoviesApiAdapter.ViewHolder>() {
    //before i have internal constructor context: Context but when i in the fragment is needed passing an ArrayList
    // then the variable is global so i can't pass the context cause null then the array can't retrieve properly cause context are null on fragment global variable
    //so in here i re use the ViewGroup (parent.context)
    // and i have explore in 1 day and found this logic val context = holder.itemView.context :'(
    companion object {
        //limited recycler view item @later's will use pagination
        const val LIMIT = 10
        val TAG_LOG: String = MoviesApiAdapter::class.java.simpleName
    }

    fun setData(itemsMovie: ArrayList<MoviesApiData>) {
        arrayListMovies.clear()
        arrayListMovies.addAll(itemsMovie)
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemMovies = ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_movies, parent, false))
        val context = parent.context
        /**
         * Listener Click ItemMovies with SupportClicker
         */
        ItemClickRecyclerSupport
            .addTo(parent.recycler_view_home)
            .setOnItemClickListener(object : ItemClickRecyclerSupport.OnItemClickListener {

                override fun onItemClicked(recyclerView: RecyclerView, position: Int, v: View) {

                    openingDetailMovie(arrayListMovies[position], context)
                    Log.d(TAG_LOG, "Try opening something about the detail movies")
                }

            })
        ItemClickRecyclerSupport
            .addTo(parent.recycler_view_home)
            .setOnItemLongClickListener(object : ItemClickRecyclerSupport.OnItemLongClickListener {

                override fun onItemLongClicked(recyclerView: RecyclerView, position: Int, v: View): Boolean {
                    Log.d(TAG_LOG, "Try onLongClick itemMovies")
                    Toast.makeText(context, arrayListMovies[position].title, Toast.LENGTH_SHORT)
                        .show()
                    return true
                }

            })

        /**
         * END OF Listener Click ItemMovies with SupportClicker
         */
        return itemMovies
    }

    private fun openingDetailMovie(
        moviesApiData: MoviesApiData,
        context: Context
    ) {

        val intent = Intent(context, MovieCatalogueDetailActivity::class.java)
        intent.putExtra(MovieCatalogueDetailActivity.EXTRA_MOVIE_DATA, moviesApiData)
        context.startActivity(intent)
    }

    override fun getItemCount(): Int {
        return if (arrayListMovies.size > LIMIT) {
            LIMIT
        } else {
            arrayListMovies.size
        }
    }

    @SuppressLint("PrivateResource")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val title = arrayListMovies[position].title
        val overview = arrayListMovies[position].overview
        val poster = POSTER_IMAGE + "w185" + arrayListMovies[position].posterPath

        val context = holder.itemView.context
        val movieDialog = Dialog(context)
        val animation = AnimationUtils.loadAnimation(context, R.anim.fade_scale)

        holder.itemView.checkbox_fav_movie.setOnCheckedChangeListener { buttonView, isChecked ->
            buttonView.startAnimation(animation)
            if (isChecked) MoviesWapiHomeFragment.initFavoriteParam(title, overview, poster, context,  ::insertFavoriteMovie)
            else Toast.makeText(context, "Sorry ,Delete item at this time only on favorite view, you cant do it at here.",
                    Toast.LENGTH_LONG)
                    .show()
        }

        holder.itemView.text_movies_release.text = arrayListMovies[position].releaseDate
        holder.itemView.text_overview.text = overview
        holder.itemView.text_movie_name.text = title


        /**
         * GENRE ID into NAME  https://api.themoviedb.org/3/genre/movie/list?api_key=10494fa60da45dee76b53c177ada8d19&language=en-US
         * later's
         */

/*      val genre =  arrayListMovies[position].genreIds.toString().replace("[[","").replace("]]","")
        if (genre == "{addParam},{addParam},{addParam}")
        {

        }
        if (genre == "28,12,878")
        {
            Log.d("com.scodeid.scholarshipexpertscodeidev2019.submission.submission3.adapter.MoviesApiAdapter.Companion.TAG_LOG","Action, Adventure, Science Fiction")
        }
            .load(profile.imageUrl)
            .signature(new StringSignature(profile.imageLastUpdate))
            .apply(RequestOptions().override(125, 125))*/

        context.let {
            Glide.with(it)
                .asBitmap()
                .load(poster)
                .error(R.color.error_color_material_light)
                .format(DecodeFormat.PREFER_ARGB_8888)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .into(holder.itemView.image_movie)
        }

        holder
            .itemView.image_movie.setOnClickListener {
            Log.d(
                TAG_LOG + "Bind",
                "image ${holder.itemView.image_movie} and the video is ${arrayListMovies[position].video} got clicked and Try opening dialog view"
            )

            /**
             * DIALOG view movie catalogue
             */
            movieDialog.setContentView(R.layout.fragment_movie_dialog)
            // bind onClick on there layout movie dialogUe

            movieDialog.button_close.setOnClickListener {
                movieDialog.dismiss()
            }
            Glide.with(it)
                .asBitmap()
                .load(POSTER_IMAGE + "w342" + arrayListMovies[position].posterPath)
                .error(R.color.error_color_material_light)
                .format(DecodeFormat.PREFER_ARGB_8888)
                .into(movieDialog.image_dialog_home)

            movieDialog.window?.attributes?.windowAnimations = R.style.AnimBottomTop
            movieDialog.show()
//             end of dialog view
        }
        // end of image clicked
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view)
}