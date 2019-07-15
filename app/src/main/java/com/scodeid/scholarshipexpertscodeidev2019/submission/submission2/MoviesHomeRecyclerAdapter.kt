/*
 * Copyright (c) 2019. SCODEID
 */

package com.scodeid.scholarshipexpertscodeidev2019.submission.submission2

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.snackbar.Snackbar
import com.scodeid.scholarshipexpertscodeidev2019.R
import com.scodeid.scholarshipexpertscodeidev2019.submission.ItemClickRecyclerSupport
import com.scodeid.scholarshipexpertscodeidev2019.submission.MovieCatalogueDetailActivity
import com.scodeid.scholarshipexpertscodeidev2019.submission.model.MovieDataModelsRecycler
import kotlinx.android.synthetic.main.fragment_movie_dialog.*
import kotlinx.android.synthetic.main.fragment_movies_home_recycler.view.*

/**
 * @author
 * Created by scode on 02,July,2019
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

class MoviesHomeRecyclerAdapter internal constructor(private val context: Context) :
    RecyclerView.Adapter<MoviesHomeRecyclerAdapter.CardViewMovieHolder>() {

    lateinit var listCardMovieRecycler: ArrayList<MovieDataModelsRecycler>

    companion object {
        var tagLog = "MovieRecyclerAdapterHolder"
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewMovieHolder {
        val itemMovies =  CardViewMovieHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_movies, parent, false))
        /**
         * Listener Click ItemMovies with SupportClicker
         */
        ItemClickRecyclerSupport
            .addTo(parent.recycler_view_home)
            .setOnItemClickListener(object : ItemClickRecyclerSupport.OnItemClickListener{

            override fun onItemClicked(recyclerView: RecyclerView, position: Int, v: View) {
                openingDetailMovie(listCardMovieRecycler[position])
                Log.d(tagLog,"Try opening something about the detail movies")
            }
        })
        /**
         * END OF Listener Click ItemMovies with SupportClicker
         */
        return itemMovies
    }

    override fun getItemCount(): Int {
        return listCardMovieRecycler.size
    }

    inner class CardViewMovieHolder(view: View) : RecyclerView.ViewHolder(view) {

        var imageMovieHome: ImageView = view.findViewById(R.id.image_movie)

        var textMovieName: TextView = view.findViewById(R.id.text_tv_movie_name)
        var textMovieRelease: TextView = view.findViewById(R.id.text_movies_release)
        var textOverView: TextView = view.findViewById(R.id.text_overview)
        //        private val rateHomeMovie: RatingBar = view.findViewById(R.id.rating_movie_home)
        var buttonViewer: Button = view.findViewById(R.id.button_icon_viewer)
        //        private val textRankLastToday: TextView = view.findViewById(R.id.text)
//        private val textRankLastWeek : TextView = view.findViewById()
//        private val textDirectorName : TextView = view.findViewById()
        // for dialogView implement dialogFragment
        var movieDialog = Dialog(context)
    }

    override fun onBindViewHolder(holder: CardViewMovieHolder, position: Int) {

        var numberProgress = 0
        numberProgress += 1 // oh no the loop didn't save , i see then how do like that but has increment on bind, could i ?
        Log.d(tagLog, "onBindViewHolderRecycler onProgress : $numberProgress")


        /**
         * Set TEXT-DATA
         */

        holder.textMovieName.text = listCardMovieRecycler[position].movieName
        holder.textMovieRelease.text = listCardMovieRecycler[position].movieRelease
        holder.textOverView.text = listCardMovieRecycler[position].movieOverview
        holder.buttonViewer.text = listCardMovieRecycler[position].movieViewers

        /**
         * Set IMAGE-DATA with Handler Async
         */

        Log.d(tagLog, "Movies source num ${numberProgress+1} data had been set|sry this my bug")
//                holder.textMovieName.text = listCardMovieRecycler[position].movieName?.let { String.format(it) }

//                Log.d("Image",""+listCardMovieRecycler[position].moviePicture)
//                Log.d("Image",""+ (listCardMovieRecycler[position].moviePicture?.toInt()))
        val imagePoster = listCardMovieRecycler[position].moviePicture?.toInt()
        //testing set with glide
        Glide.with(context)
            .asBitmap()
            .load(imagePoster)
            .apply(RequestOptions().override(75, 75))
            .into(holder.imageMovieHome)
//                for (i in holder.imageMovieHome.ind)

//                listCardMovieRecycler[position].moviePicture?.toInt()?.let { holder.imageMovieHome.setImageResource(it) }

        /**
         * End OF HANDLER set IMAGE-DATA
         */


        /**
         * Bind Listener a Component
         */

        holder
            .buttonViewer.setOnClickListener {
            val viewMovies = holder.buttonViewer.text.toString() // + "Viewers"
            Toast.makeText(it.context, viewMovies, Toast.LENGTH_SHORT)
                .show()
            Snackbar.make(it, ""+context.resources.getString(R.string.notification_warning_1), Snackbar.LENGTH_LONG)
                .show()
        }

        holder
            .imageMovieHome.setOnClickListener {
            Log.d(tagLog + "Bind", "image ${holder.imageMovieHome} got clicked and Try opening dialog view")

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
            listCardMovieRecycler[position].moviePicture?.toInt()
                ?.let { it1 -> holder.movieDialog.image_dialog_home.setImageResource(it1) }
//            Glide.with(it)
//                .load(R.drawable.ic_poster_avenger_infinity)
//                .into(movieDialog.image_dialog_home)
            holder.movieDialog.window?.attributes?.windowAnimations = R.style.AnimBottomTop
            holder.movieDialog.show()
            // end of dialog view
        }
        // end of image clicked

        /**
         * End of Bind LISTENER
         */
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    private fun openingDetailMovie(movieDataModelsRecycler: MovieDataModelsRecycler) {
        val movieDataModels = MovieDataModelsRecycler(
            "" + movieDataModelsRecycler.moviePicture?.toInt(),
            "" + movieDataModelsRecycler.moviePictureBackground?.toInt(),
            "" + movieDataModelsRecycler.moviePictureRelated1?.toInt(),
            "" + movieDataModelsRecycler.moviePictureRelated2?.toInt(),
            "" + movieDataModelsRecycler.moviePictureRelated3?.toInt(),
            "" + movieDataModelsRecycler.movieName,
            "" + movieDataModelsRecycler.movieRelease,
            "" + movieDataModelsRecycler.movieOverview,
            "" + movieDataModelsRecycler.movieRankLastToday,
            "" + movieDataModelsRecycler.movieRAnkLastWeek,
            "" + movieDataModelsRecycler.movieDirector1,
            "" + movieDataModelsRecycler.movieDirector2,
            "" + movieDataModelsRecycler.movieOrigLang,
            "" + movieDataModelsRecycler.movieRuntime,
            "" + movieDataModelsRecycler.movieBudget,
            "" + movieDataModelsRecycler.movieRevenue,
            "" + movieDataModelsRecycler.movieScreenPlay1,
            "" + movieDataModelsRecycler.movieScreenPlay2,
            "" + movieDataModelsRecycler.movieGenres,
            "" + movieDataModelsRecycler.movieKeywords,
            "" + movieDataModelsRecycler.movieScore,
            "" + movieDataModelsRecycler.movieViewers
        )
        val intent = Intent(context, MovieCatalogueDetailActivity::class.java)
        intent.putExtra(MovieCatalogueDetailActivity.EXTRA_MOVIE_DATA, movieDataModels)
        context.startActivity(intent)

    }


}