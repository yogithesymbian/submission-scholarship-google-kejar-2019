/*
 * Copyright (c) 2019. SCODEID
 */

/**
 * @author
 * Yogi Arif Widodo
 * www.dicoding.com/users/297307
 * www.github.com/yogithesymbian
 * For Google Kejar 2019
 */
package com.scodeid.scholarshipexpertscodeidev2019.submission.submission1

import android.app.Dialog
import android.content.Context
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import com.scodeid.scholarshipexpertscodeidev2019.R
import com.scodeid.scholarshipexpertscodeidev2019.submission.model.MovieDataModels
import kotlinx.android.synthetic.main.fragment_movie_dialog.*


/**
 *
 * Implements method baseAdapter
 * the need
 * View For Bind Data Model
 * context for dialogMovieImage
 * NPE note :
 * https://stackoverflow.com/questions/34498562/in-kotlin-what-is-the-idiomatic-way-to-deal-with-nullable-values-referencing-o?answertab=active#tab-top
 */

@Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS", "NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class MovieViewHolder internal constructor(view: View, context: Context?) {

    private val tagLog = "MovieViewHolder"
    private val imageMovieHome: ImageView = view.findViewById(R.id.image_movie)
    private val textMovieName: TextView = view.findViewById(R.id.text_tv_movie_name)
    private val textMovieRelease: TextView = view.findViewById(R.id.text_movies_release)
    private val textOverView: TextView = view.findViewById(R.id.text_overview)
    //    private val rateHomeMovie: RatingBar = view.findViewById(R.id.rating_movie_home)
    private val buttonViewer: Button = view.findViewById(R.id.button_icon_viewer)
//        private val textRankLastToday: TextView = view.findViewById(R.id.text)
//        private val textRankLastWeek : TextView = view.findViewById()
//        private val textDirectorName : TextView = view.findViewById()

    // for dialogView implement dialogFragment
    private val movieDialog = Dialog(context)


    init {
        // manipulation view
        // later's
    }

    internal fun bind(movieDataMovies: MovieDataModels) {

        /**
         * set resource data from data model
         */
        movieDataMovies.moviePicture.let { imageMovieHome.setImageResource(it) }
// For a simple view:
//        @Override public void onCreate(Bundle savedInstanceState) {
//            ...
//            ImageView imageView = (ImageView) findViewById(R.id.my_image_view);
//
//            Glide.with(this).load("http://goo.gl/gEgYUd").into(imageView);
//        }
        textMovieName.text = movieDataMovies.movieName
        textMovieRelease.text = movieDataMovies.movieRelease
        textOverView.text = movieDataMovies.movieOverview

        // end of set resource

        // imageMovieCatalogue Listener Clicked
        imageMovieHome.setOnClickListener {
            Log.d(tagLog + "Bind", "image $imageMovieHome got clicked and Try opening dialog view")

            /**
             * DIALOG view movie catalogue
             */
            movieDialog.setContentView(R.layout.fragment_movie_dialog)
            // bind onClick on there layout movie dialogUe

            movieDialog.button_close.setOnClickListener {
                movieDialog.dismiss()
            }
            //set image dialog movie
            movieDataMovies.moviePicture.let { it1 -> movieDialog.image_dialog_home.setImageResource(it1) }
//            Glide.with(it)
//                .load(R.drawable.ic_poster_avenger_infinity)
//                .into(movieDialog.image_dialog_home)

            movieDialog.window.attributes.windowAnimations = R.style.AnimBottomTop
            movieDialog.show()

            // end of dialog view
        }
        // end of image clicked

        buttonViewer.setOnClickListener{
            val viewMovies = buttonViewer.text.toString() // + "Viewers"
            Toast.makeText(it.context, viewMovies, Toast.LENGTH_SHORT)
                .show()
            Snackbar.make(it,"Sorry this feature still have maintenance ", Snackbar.LENGTH_LONG)
                .show()
        }

    }


}