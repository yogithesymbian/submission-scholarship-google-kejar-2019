/*
 * Copyright (c) 2019. SCODEID
 */

package com.scodeid.scholarshipexpertscodeidev2019.submission.submission4

import android.annotation.SuppressLint
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.scodeid.scholarshipexpertscodeidev2019.R
import com.scodeid.scholarshipexpertscodeidev2019.submission.submission4.adapter.FavoriteAdapter
import com.scodeid.scholarshipexpertscodeidev2019.submission.submission4.model.MovieHelperModel
import com.scodeid.scholarshipexpertscodeidev2019.submission.submission4.model.MovieHelperModel.Companion.getInstance
import com.scodeid.scholarshipexpertscodeidev2019.submission.submission4.model.MovieModel
import kotlinx.android.synthetic.main.activity_main_favorite.*
import java.lang.ref.WeakReference

class MainFavoriteActivity : AppCompatActivity(), LoadMovieCallBack {

    companion object {
        private const val EXTRA_STATE = "extra_state"
    }

    private lateinit var movieHelperModel: MovieHelperModel
    private lateinit var favoriteAdapter: FavoriteAdapter

    @SuppressLint("Recycle")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_favorite)
        recycler_favorite.layoutManager = LinearLayoutManager(this)
        recycler_favorite.setHasFixedSize(true)

        movieHelperModel = getInstance(applicationContext)
        movieHelperModel.open()

        //{button/add/edit/delete later's}

        favoriteAdapter = FavoriteAdapter(this)
        recycler_favorite.adapter = favoriteAdapter

        if (savedInstanceState == null) {
            LoadNotesAsync(movieHelperModel, this).execute()
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
            if (requestCode == MainFavoriteDeleteActivity.REQUEST_UPDATE) {
                if (resultCode == MainFavoriteDeleteActivity.RESULT_DELETE) {
                    val position = data.getIntExtra(MainFavoriteDeleteActivity.EXTRA_POSITION, 0)
                    favoriteAdapter.removeItemMovies(position)
                    showSnackbarMessage("succss delete item")
                }
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        movieHelperModel.close()
    }

    private fun showSnackbarMessage(message: String) {
        Snackbar.make(recycler_favorite, message, Snackbar.LENGTH_SHORT)
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

    override fun postExecute(movieModel: ArrayList<MovieModel>) {
        favoriteAdapter.listMovieModel = movieModel
        frame_progress_favorite.visibility = View.GONE
        card_favorite.visibility = View.VISIBLE

    }

    override fun onPointerCaptureChanged(hasCapture: Boolean) {

    }

    private class LoadNotesAsync(movieHelperModel: MovieHelperModel, callBack: LoadMovieCallBack) :
        AsyncTask<Void, Void, ArrayList<MovieModel>>() {

        private val weakReferenceMovieHelper: WeakReference<MovieHelperModel> = WeakReference(movieHelperModel)

        private val weakReferenceCallBack: WeakReference<LoadMovieCallBack> = WeakReference(callBack)

        override fun onPreExecute() {
            super.onPreExecute()
            weakReferenceCallBack.get()?.preExecute()
        }

        override fun doInBackground(vararg voids: Void): ArrayList<MovieModel>? {
            return weakReferenceMovieHelper.get()?.getAllMovies()
        }

        override fun onPostExecute(movieModels: ArrayList<MovieModel>) {
            super.onPostExecute(movieModels)
            weakReferenceCallBack.get()?.postExecute(movieModels)
        }

    }
}
