/*
 * Copyright (c) 2019. SCODEID
 */

package com.scodeid.scholarshipexpertscodeidev2019personal.homeFavorite

import android.content.Context
import android.database.ContentObserver
import android.database.Cursor
import android.os.AsyncTask
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.scodeid.scholarshipexpertscodeidev2019personal.R
import com.scodeid.scholarshipexpertscodeidev2019personal.adapter.ConsFavMovieAdapter
import com.scodeid.yomoviecommon.database.ContractDatabase.MovieColumns.CONTENT_URI_MOVIE
import com.scodeid.yomoviecommon.helper.MappingHelper.movieMapCursorToArrayList
import com.scodeid.yomoviecommon.interfaceFavorite.LoadMovieProvCallBack
import com.scodeid.yomoviecommon.model.favorite.MovieProvModel
import com.scodeid.yomoviecommon.utils.debuggingMyScode
import kotlinx.android.synthetic.main.activity_main_favorite.*
import java.lang.ref.WeakReference

class MainFavoriteMovieActivity : AppCompatActivity(),
    LoadMovieProvCallBack {

    companion object {
        private const val EXTRA_STATE = "extra_state"
        val TAG_LOG: String = MainFavoriteMovieActivity::class.java.simpleName
    }

    private lateinit var favoriteAdapter: ConsFavMovieAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_favorite)

        favoriteAdapter = ConsFavMovieAdapter(this)
        recycler_favorite_movie.layoutManager = LinearLayoutManager(this)
        recycler_favorite_movie.setHasFixedSize(true)
        recycler_favorite_movie.adapter = favoriteAdapter

        val handlerThread = HandlerThread("DataObserver")
        handlerThread.start()

        val handler = Handler(handlerThread.looper)
        val myObserver = DataObserver(handler, this)

        contentResolver.registerContentObserver(CONTENT_URI_MOVIE, true, myObserver)


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

        override fun doInBackground(vararg voids: Void): Cursor? {
            val context = weakReferenceContext.get()
            debuggingMyScode(TAG_LOG, "LoadAsync doInBackground")
            return context?.contentResolver?.query(CONTENT_URI_MOVIE, null, null, null, null)
        }

        override fun onPostExecute(cursor: Cursor) {
            super.onPostExecute(cursor)
            debuggingMyScode(TAG_LOG, "LoadAsync onPostExecute")
            weakReferenceCallBack.get()?.postExecute(cursor)
        }
    }

    class DataObserver(handler: Handler, internal val context: Context) : ContentObserver(handler) {

        override fun onChange(selfChange: Boolean) {
            super.onChange(selfChange)
            LoadFavMovieAsync(context, context as LoadMovieProvCallBack).execute()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
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

        debuggingMyScode(TAG_LOG, "onPostExecute")
        val listMovieArray = movieMapCursorToArrayList(cursor)

        if (listMovieArray.size > 0) {
            favoriteAdapter.setListMovie(listMovieArray)
            image_empty_fav.visibility = View.GONE
            text_empty_fav.visibility = View.GONE
        } else {
            favoriteAdapter.setListMovie(java.util.ArrayList())
            image_empty_fav.visibility = View.VISIBLE
            text_empty_fav.visibility = View.VISIBLE
        }
    }

    override fun onPointerCaptureChanged(hasCapture: Boolean) {

    }
}
