/*
 * Copyright (c) 2019. SCODEID
 */

package com.scodeid.scholarshipexpertscodeidev2019.submission.submission4

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.scodeid.scholarshipexpertscodeidev2019.R
import com.scodeid.scholarshipexpertscodeidev2019.submission.submission4.model.MovieHelperModel
import com.scodeid.scholarshipexpertscodeidev2019.submission.submission4.model.MovieModel
import kotlinx.android.synthetic.main.activity_main_favorite_delete.*

class MainFavoriteDeleteActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_MOVIE = "extra_movie"
        const val EXTRA_POSITION = "extra_position"

        const val REQUEST_UPDATE = 200

        const val RESULT_DELETE = 301

        private const val ALERT_DIALOG_CLOSE = 10
        private const val ALERT_DIALOG_DELETE = 20
        val TAG_LOG: String = MainFavoriteDeleteActivity::class.java.simpleName
    }

    private var movieModel: MovieModel? = null
    private var movieHelperModel: MovieHelperModel? = null
    private var position: Int = 0
    private var isEdit = false

    private fun showAlertDialog(type: Int) {
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
            .setPositiveButton(getString(R.string.dialog_positif_yes)) { _, _ ->
                if (isDialogClose) {
                    finish()
                } else {
                    // init for delete
                    val result = movieModel?.id?.let { movieHelperModel?.deleteMovie(it)?.toLong() }
                    // looking for the delete have an item ?
                    if (result != null) {
                        // result_success ?
                        if (result > 0) {
                            val intent = Intent()
                            intent.putExtra(EXTRA_POSITION, position)
                            setResult(RESULT_DELETE, intent)
                            finish()
                        } else {
                            Toast.makeText(this@MainFavoriteDeleteActivity, getString(R.string.toast_sql_lite_delete_fail), Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                }
            }
            .setNegativeButton(getString(R.string.dialog_positif_no)) { dialog, _ -> dialog.cancel() }
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }

    @SuppressLint("PrivateResource")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_favorite_delete)

        movieHelperModel = MovieHelperModel.getInstance(applicationContext)
        movieModel = intent.getParcelableExtra<MovieModel>(EXTRA_MOVIE)

        // set position intent
        if (movieModel != null) {
            position = intent.getIntExtra(EXTRA_POSITION, 0)
            isEdit = true
        }

        if (isEdit) {
            if (movieModel != null) {
                Log.d(TAG_LOG, "image can load ? see : " + movieModel?.posterImage)
                Glide.with(this)
                    .asBitmap()
                    .load(movieModel?.posterImage)
                    .error(R.color.error_color_material_light)
                    .format(DecodeFormat.PREFER_ARGB_8888)
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                    .into(image_poster_delete)
            }
        }

        // delete item favorite
        image_delete_favorite.setOnClickListener {
            showAlertDialog(ALERT_DIALOG_DELETE)
        }

    }

    override fun onBackPressed() {
        showAlertDialog(ALERT_DIALOG_CLOSE)
    }
}
