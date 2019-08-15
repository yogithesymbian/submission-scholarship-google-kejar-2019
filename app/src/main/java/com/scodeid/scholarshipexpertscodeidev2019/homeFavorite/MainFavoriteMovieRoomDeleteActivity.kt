/*
 * Copyright (c) 2019. SCODEID
 */

@file:Suppress("DEPRECATION")

package com.scodeid.scholarshipexpertscodeidev2019.homeFavorite

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.scodeid.scholarshipexpertscodeidev2019.R
import com.scodeid.scholarshipexpertscodeidev2019.model.favorite.MovieRoomModel
import com.scodeid.scholarshipexpertscodeidev2019.model.favorite.MovieRoomView
import kotlinx.android.synthetic.main.activity_main_favorite_movie_room_delete.*

class MainFavoriteMovieRoomDeleteActivity : AppCompatActivity() {

    private lateinit var movieRoomView: MovieRoomView
    private var movieRoomModel: MovieRoomModel? = null

    companion object {
        const val EXTRA_MOVIE = "extra_movie"
        const val EXTRA_POSITION = "extra_position"

        const val REQUEST_UPDATE = 200
        const val RESULT_DELETE = 301

        private const val ALERT_DIALOG_CLOSE = 10
        private const val ALERT_DIALOG_DELETE = 20

        val TAG_LOG: String = MainFavoriteMovieRoomDeleteActivity::class.java.simpleName
    }

    private var position: Int = 0


    private fun showAlertDialog(
        type: Int
    ) {
        val isDialogClose = type == ALERT_DIALOG_CLOSE
        val dialogTitle: String
        val dialogMsg: String

        if (isDialogClose) {
            dialogTitle = getString(R.string.dialog_cancel_title)
            dialogMsg = getString(R.string.dialog_cancel_msg)
        } else {
            dialogMsg = getString(R.string.dialog_delete_msg)
            dialogTitle = getString(R.string.dialog_delete_title)
        }

        val alertDialogBuilder = AlertDialog.Builder(this)

        alertDialogBuilder.setTitle(dialogTitle)
        alertDialogBuilder
            .setMessage(dialogMsg)
            .setCancelable(false)
            .setPositiveButton(getString(R.string.dialog_positive_yes)) { _, _ ->
                if (isDialogClose) {
                    finish()
                } else {
                    val intent = Intent()
                    intent.putExtra(EXTRA_POSITION, position)
                    setResult(RESULT_DELETE, intent)
                    finish()
                }
            }
            .setNegativeButton(getString(R.string.dialog_positive_no)) { dialog, _ -> dialog.cancel() }
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }

    @SuppressLint("PrivateResource")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_favorite_movie_room_delete)

        movieRoomModel = intent.getParcelableExtra<MovieRoomModel>(EXTRA_MOVIE)

        movieRoomView = ViewModelProviders.of(this).get(MovieRoomView::class.java)
        // delete item favorite
        image_delete_favorite.setOnClickListener {
            showAlertDialog(ALERT_DIALOG_DELETE)
        }
        if (movieRoomModel != null) {
            Log.d(TAG_LOG, "image can load ? see : " + movieRoomModel?.poster)
            Glide.with(this)
                .asBitmap()
                .load(movieRoomModel?.poster)
                .error(R.color.error_color_material_light)
                .format(DecodeFormat.PREFER_ARGB_8888)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .into(image_poster_delete)
        }

    }

    override fun onBackPressed() {
        showAlertDialog(ALERT_DIALOG_CLOSE)
    }
}
