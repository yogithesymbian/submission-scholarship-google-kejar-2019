/*
 * Copyright (c) 2019. SCODEID
 */

package com.scodeid.scholarshipexpertscodeidev2019.submission.submission4

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.scodeid.scholarshipexpertscodeidev2019.R
import com.scodeid.scholarshipexpertscodeidev2019.submission.submission4.adapter.FavoriteMovieRoomAdapter
import com.scodeid.scholarshipexpertscodeidev2019.submission.submission4.model.MovieRoomView
import kotlinx.android.synthetic.main.activity_main_favorite_movie_room.*

class MainFavoriteMovieRoomActivity : AppCompatActivity() {

    private lateinit var movieRoomView: MovieRoomView
    private lateinit var favoriteMovieRoomAdapter: FavoriteMovieRoomAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_favorite_movie_room)

        favoriteMovieRoomAdapter = FavoriteMovieRoomAdapter(this)
        recycler_favorite_movie.adapter = favoriteMovieRoomAdapter
        recycler_favorite_movie.layoutManager = LinearLayoutManager(this)

        movieRoomView = ViewModelProviders.of(this).get(MovieRoomView::class.java)
        movieRoomView.allMovies.observe(this, Observer { movies ->
            movies?.let {
                favoriteMovieRoomAdapter.setMovieRooms(it)
            }
        })


    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data != null) {
            if (requestCode == MainFavoriteMovieDeleteActivity.REQUEST_UPDATE) {
                if (resultCode == MainFavoriteMovieDeleteActivity.RESULT_DELETE) {
                    val position = data.getIntExtra(MainFavoriteMovieRoomDeleteActivity.EXTRA_POSITION, 0)
                    favoriteMovieRoomAdapter.removeItemMovies(position)
                    showSnackbarMessage("success delete item")
                }
            }
        }
    }

    private fun showSnackbarMessage(message: String) {
        Snackbar.make(recycler_favorite_movie, message, Snackbar.LENGTH_SHORT)
            .show()
    }
    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}
