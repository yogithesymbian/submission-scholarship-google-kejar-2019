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
import com.scodeid.scholarshipexpertscodeidev2019.R
import com.scodeid.scholarshipexpertscodeidev2019.adapter.FavoriteTvAdapter
import com.scodeid.yomoviecommon.database.ContractDatabase.MovieColumns.CONTENT_URI_TV
import com.scodeid.scholarshipexpertscodeidev2019.helper.MappingHelper.tvMapCursorToArrayList
import com.scodeid.scholarshipexpertscodeidev2019.interfaceFavorite.LoadTvProvCallBack
import com.scodeid.scholarshipexpertscodeidev2019.model.favorite.TvProvModel
import com.scodeid.scholarshipexpertscodeidev2019personal.R
import com.scodeid.yomoviecommon.utils.debuggingMyScode
import kotlinx.android.synthetic.main.activity_main_favorite_tv.*
import java.lang.ref.WeakReference

class MainFavoriteTvActivity : AppCompatActivity(),
    LoadTvProvCallBack {

    companion object {
        private const val EXTRA_STATE = "extra_state"
        val TAG_LOG: String = MainFavoriteTvActivity::class.java.simpleName
    }

    private lateinit var favoriteAdapter: FavoriteTvAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_favorite_tv)

        recycler_favorite_tv.layoutManager = LinearLayoutManager(this)
        recycler_favorite_tv.setHasFixedSize(true)

        val handlerThread = HandlerThread("DataObserverTv")
        handlerThread.start()
        val handler = Handler(handlerThread.looper)
        val myObserver = DataObserverTv(handler, this)
        contentResolver.registerContentObserver(CONTENT_URI_TV, true, myObserver)

        favoriteAdapter = FavoriteTvAdapter(this)
        recycler_favorite_tv.adapter = favoriteAdapter

        if (savedInstanceState == null) {
            LoadFavMovieAsync(
                this,
                this
            ).execute()
        } else {
            val list = savedInstanceState.getParcelableArrayList<TvProvModel>(EXTRA_STATE)
            if (list != null) {
                favoriteAdapter.setListTv(list)
            }
        }

    }

    private class LoadFavMovieAsync(context: Context, loadMovieProvCallBack: LoadTvProvCallBack) :
        AsyncTask<Void, Void, Cursor>() {

        private val weakReferenceContext: WeakReference<Context> = WeakReference(context)

        private val weakReferenceCallBack: WeakReference<LoadTvProvCallBack> = WeakReference(loadMovieProvCallBack)

        override fun onPreExecute() {
            super.onPreExecute()
            weakReferenceCallBack.get()?.preExecute()
            debuggingMyScode(TAG_LOG, "LoadAsync OnPreExecute")
        }


        override fun doInBackground(vararg voids: Void): Cursor? {
            val context = weakReferenceContext.get()
            debuggingMyScode(TAG_LOG, "LoadAsync doInBackground")
            return context?.contentResolver?.query(CONTENT_URI_TV, null, null, null, null)

        }

        override fun onPostExecute(cursor: Cursor) {
            super.onPostExecute(cursor)
            debuggingMyScode(TAG_LOG, "LoadAsync onPostExecute")
            weakReferenceCallBack.get()?.postExecute(cursor)
        }
    }

    class DataObserverTv(handler: Handler, internal val context: Context) : ContentObserver(handler) {

        override fun onChange(selfChange: Boolean) {
            super.onChange(selfChange)
            LoadFavMovieAsync(context, context as LoadTvProvCallBack).execute()
        }
    }


    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(EXTRA_STATE, favoriteAdapter.listTvModel)
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
        val listNotes = tvMapCursorToArrayList(cursor)

        if (listNotes.size > 0) {
            favoriteAdapter.setListTv(listNotes)
            image_empty_fav.visibility = View.GONE
            text_empty_fav.visibility = View.GONE
        } else {
            favoriteAdapter.setListTv(java.util.ArrayList())
            image_empty_fav.visibility = View.VISIBLE
            text_empty_fav.visibility = View.VISIBLE
            card_favorite.visibility = View.GONE
        }
    }

    override fun onPointerCaptureChanged(hasCapture: Boolean) {

    }
}
