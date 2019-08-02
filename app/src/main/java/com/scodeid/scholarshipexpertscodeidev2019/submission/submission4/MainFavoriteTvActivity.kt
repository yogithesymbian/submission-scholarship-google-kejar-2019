/*
 * Copyright (c) 2019. SCODEID
 */

package com.scodeid.scholarshipexpertscodeidev2019.submission.submission4

import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.scodeid.scholarshipexpertscodeidev2019.R
import com.scodeid.scholarshipexpertscodeidev2019.submission.submission4.adapter.FavoriteTvAdapter
import com.scodeid.scholarshipexpertscodeidev2019.submission.submission4.model.HelperModel
import com.scodeid.scholarshipexpertscodeidev2019.submission.submission4.model.TvModel
import kotlinx.android.synthetic.main.activity_main_favorite_tv.*
import java.lang.ref.WeakReference

class MainFavoriteTvActivity : AppCompatActivity(), LoadTvCallBack {

    companion object {
        private const val EXTRA_STATE = "extra_state"
    }

    private lateinit var helperModel: HelperModel
    private lateinit var favoriteTvAdapter: FavoriteTvAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_favorite_tv)

        recycler_favorite_tv.layoutManager = LinearLayoutManager(this)
        recycler_favorite_tv.setHasFixedSize(true)

        helperModel = HelperModel.getInstance(applicationContext)
        helperModel.open()

        //{button/add/edit/delete later's}

        favoriteTvAdapter = FavoriteTvAdapter(this)
        recycler_favorite_tv.adapter = favoriteTvAdapter

        if (savedInstanceState == null) {
            LoadNotesAsync(helperModel, this).execute()
        } else {
            val tvModels = savedInstanceState.getParcelableArrayList<TvModel>(EXTRA_STATE)
            if (tvModels != null) {
                favoriteTvAdapter.listTvModel = tvModels
            }
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data != null) {
            if (requestCode == MainFavoriteTvDeleteActivity.REQUEST_UPDATE) {
                if (resultCode == MainFavoriteTvDeleteActivity.RESULT_DELETE) {
                    val position = data.getIntExtra(MainFavoriteTvDeleteActivity.EXTRA_POSITION, 0)
                    favoriteTvAdapter.removeItemTv(position)
                    showSnackbarMessage("success delete item")
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
        helperModel.close()
    }

    private fun showSnackbarMessage(message: String) {
        Snackbar.make(recycler_favorite_tv, message, Snackbar.LENGTH_SHORT)
            .show()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(EXTRA_STATE, favoriteTvAdapter.listTvModel)
    }

    override fun preExecute() {
        runOnUiThread {
            frame_progress_favorite.visibility = View.VISIBLE
            card_favorite.visibility = View.GONE
        }
    }

    override fun postExecuteTv(tvModel: ArrayList<TvModel>) {
        favoriteTvAdapter.listTvModel = tvModel
        frame_progress_favorite.visibility = View.GONE
        card_favorite.visibility = View.VISIBLE
    }


    private class LoadNotesAsync(helperModel: HelperModel, callBack: LoadTvCallBack) :
        AsyncTask<Void, Void, ArrayList<TvModel>>() {

        private val weakReferenceHelper: WeakReference<HelperModel> = WeakReference(helperModel)

        private val weakReferenceCallBack: WeakReference<LoadTvCallBack> = WeakReference(callBack)

        override fun onPreExecute() {
            super.onPreExecute()
            weakReferenceCallBack.get()?.preExecute()
        }

        override fun doInBackground(vararg voids: Void): ArrayList<TvModel>? {
            return weakReferenceHelper.get()?.getAllTv()
        }

        override fun onPostExecute(tvModels: ArrayList<TvModel>) {
            super.onPostExecute(tvModels)
            weakReferenceCallBack.get()?.postExecuteTv(tvModels)
        }

    }
}
