/*
 * Copyright (c) 2019. SCODEID
 */

package com.scodeid.scholarshipexpertscodeidev2019.homeFavorite

import android.content.Context
import android.content.Intent
import android.database.ContentObserver
import android.database.Cursor
import android.os.AsyncTask
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.scodeid.scholarshipexpertscodeidev2019.R
import com.scodeid.scholarshipexpertscodeidev2019.adapter.FavoriteAdapter
import com.scodeid.scholarshipexpertscodeidev2019.database.ContractDatabase.MovieColumns.CONTENT_URI_MOVIE
import com.scodeid.scholarshipexpertscodeidev2019.helper.MappingHelper.movieMapCursorToArrayList
import com.scodeid.scholarshipexpertscodeidev2019.interfaceFavorite.LoadMovieProvCallBack
import com.scodeid.scholarshipexpertscodeidev2019.model.favorite.MovieProvModel
import kotlinx.android.synthetic.main.activity_main_favorite.*
import java.lang.ref.WeakReference

class MainFavoriteMovieActivity : AppCompatActivity(),
    LoadMovieProvCallBack {

    companion object {
        private const val EXTRA_STATE = "extra_state"
        val TAG_LOG: String = MainFavoriteMovieActivity::class.java.simpleName
    }
    private lateinit var favoriteAdapter: FavoriteAdapter



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_favorite)

        recycler_favorite_movie.layoutManager = LinearLayoutManager(this)
        recycler_favorite_movie.setHasFixedSize(true)

        val handlerThread = HandlerThread("DataObserver")
        handlerThread.start()
        val handler = Handler(handlerThread.looper)
        val myObserver = DataObserver(handler, this)
        contentResolver.registerContentObserver(CONTENT_URI_MOVIE, true, myObserver)

        favoriteAdapter = FavoriteAdapter(this)
        recycler_favorite_movie.adapter = favoriteAdapter

        if (savedInstanceState == null) {
            LoadFavMovieAsync(
                this,
                this
            ).execute()
        } else {
            val list = savedInstanceState.getParcelableArrayList<MovieProvModel>(EXTRA_STATE)
            if (list != null) {
                favoriteAdapter.setListMovie(list)
            }
        }

    }

    private class LoadFavMovieAsync(context: Context, loadMovieProvCallBack: LoadMovieProvCallBack) :
        AsyncTask<Void, Void, Cursor>() {

        private val weakReferenceContext: WeakReference<Context> = WeakReference(context)

        private val weakReferenceCallBack: WeakReference<LoadMovieProvCallBack> = WeakReference(loadMovieProvCallBack)

        override fun onPreExecute() {
            super.onPreExecute()
            weakReferenceCallBack.get()?.preExecute()
            Log.d(TAG_LOG, "LoadAsync OnPreExecute")
        }


        override fun doInBackground(vararg voids: Void): Cursor? {
            val context = weakReferenceContext.get()
            Log.d(TAG_LOG, "LoadAsync doInBackground")
            return context?.contentResolver?.query(CONTENT_URI_MOVIE, null, null, null, null)

        }

        override fun onPostExecute(cursor: Cursor) {
            super.onPostExecute(cursor)
            Log.d(TAG_LOG, "LoadAsync onPostExecute")
            weakReferenceCallBack.get()?.postExecute(cursor)
        }
    }

    class DataObserver(handler: Handler, internal val context: Context) : ContentObserver(handler) {

        override fun onChange(selfChange: Boolean) {
            super.onChange(selfChange)
            LoadFavMovieAsync(context, context as LoadMovieProvCallBack).execute()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (data != null) {
            if (requestCode == MainFavoriteMovieDetailActivity.REQUEST_UPDATE) {
                if (resultCode == MainFavoriteMovieDetailActivity.RESULT_DELETE) {
                    val position = data.getIntExtra(MainFavoriteMovieDetailActivity.EXTRA_POSITION, 0)
                    favoriteAdapter.removeItemMovies(position)
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

    override fun postExecute(cursor: Cursor) {

        frame_progress_favorite.visibility = View.GONE
        card_favorite.visibility = View.VISIBLE

        Log.d(TAG_LOG, "onPostExecute")
        val listNotes = movieMapCursorToArrayList(cursor)

        if (listNotes.size > 0) favoriteAdapter.setListMovie(listNotes)
        else {
            favoriteAdapter.setListMovie(java.util.ArrayList())
            showSnackbarMessage("Item is null")
        }
    }

    override fun onPointerCaptureChanged(hasCapture: Boolean) {

    }
}
