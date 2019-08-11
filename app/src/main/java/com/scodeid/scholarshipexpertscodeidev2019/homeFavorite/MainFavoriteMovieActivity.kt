/*
 * Copyright (c) 2019. SCODEID
 */

package com.scodeid.scholarshipexpertscodeidev2019.homeFavorite

import android.annotation.SuppressLint
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.scodeid.scholarshipexpertscodeidev2019.R
import com.scodeid.scholarshipexpertscodeidev2019.adapter.FavoriteAdapter
import com.scodeid.scholarshipexpertscodeidev2019.interfaceFavorite.LoadMovieCallBack
import com.scodeid.scholarshipexpertscodeidev2019.model.favorite.HelperModel
import com.scodeid.scholarshipexpertscodeidev2019.model.favorite.HelperModel.Companion.getInstance
import com.scodeid.scholarshipexpertscodeidev2019.model.favorite.MovieModel
import kotlinx.android.synthetic.main.activity_main_favorite.*
import java.lang.ref.WeakReference

class MainFavoriteMovieActivity : AppCompatActivity(),
    LoadMovieCallBack {

    companion object {
        private const val EXTRA_STATE = "extra_state"
    }

    private lateinit var helperModel: HelperModel
    private lateinit var favoriteAdapter: FavoriteAdapter

    @SuppressLint("Recycle")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_favorite)

        recycler_favorite_movie.layoutManager = LinearLayoutManager(this)
        recycler_favorite_movie.setHasFixedSize(true)

        helperModel = getInstance(applicationContext)
        helperModel.open()

        favoriteAdapter = FavoriteAdapter(this)
        recycler_favorite_movie.adapter = favoriteAdapter

        if (savedInstanceState == null) {
            LoadNotesAsync(
                helperModel,
                this
            ).execute()
        } else {
            val movieModels = savedInstanceState.getParcelableArrayList<MovieModel>(EXTRA_STATE)
            if (movieModels != null) {
                favoriteAdapter.listMovieModel = movieModels
            }
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data != null) {
            if (requestCode == MainFavoriteMovieDetailActivity.REQUEST_UPDATE) {
                if (resultCode == MainFavoriteMovieDetailActivity.RESULT_DELETE) {
                    val position = data.getIntExtra(MainFavoriteMovieDetailActivity.EXTRA_POSITION, 0)
                    favoriteAdapter.removeItemMovies(position)
                    showSnackbarMessage("success delete item")
                }
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    private fun showSnackbarMessage(message: String) {
        Snackbar.make(recycler_favorite_movie, message, Snackbar.LENGTH_SHORT)
            .show()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(EXTRA_STATE, favoriteAdapter.listMovieModel)
    }

    override fun preExecute() {
        runOnUiThread {
            frame_progress_favorite.visibility = View.VISIBLE
            card_favorite.visibility = View.GONE
        }
    }

    override fun postExecuteMovie(movieModel: ArrayList<MovieModel>) {
        favoriteAdapter.listMovieModel = movieModel
        frame_progress_favorite.visibility = View.GONE
        card_favorite.visibility = View.VISIBLE
    }

    override fun onPointerCaptureChanged(hasCapture: Boolean) {

    }

    private class LoadNotesAsync(helperModel: HelperModel, callBack: LoadMovieCallBack) :
        AsyncTask<Void, Void, ArrayList<MovieModel>>() {

        private val weakReferenceHelper: WeakReference<HelperModel> = WeakReference(helperModel)

        private val weakReferenceCallBack: WeakReference<LoadMovieCallBack> = WeakReference(callBack)

        override fun onPreExecute() {
            super.onPreExecute()
            weakReferenceCallBack.get()?.preExecute()
        }

        override fun doInBackground(vararg voids: Void): ArrayList<MovieModel>? {
            return weakReferenceHelper.get()?.getAllMovies()
        }

        override fun onPostExecute(movieModels: ArrayList<MovieModel>) {
            super.onPostExecute(movieModels)
            weakReferenceCallBack.get()?.postExecuteMovie(movieModels)
        }

    }
}
