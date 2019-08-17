/*
 * Copyright (c) 2019. SCODEID
 */

@file:Suppress("DEPRECATION")

package com.scodeid.scholarshipexpertscodeidev2019.homeFavorite

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.scodeid.scholarshipexpertscodeidev2019.R
import com.scodeid.scholarshipexpertscodeidev2019.adapter.FavoriteMovieRoomAdapter
import com.scodeid.scholarshipexpertscodeidev2019.model.favorite.MovieRoomModel
import com.scodeid.scholarshipexpertscodeidev2019.model.favorite.MovieRoomView
import kotlinx.android.synthetic.main.activity_main_favorite_movie_room.*

class MainFavoriteMovieRoomActivity : AppCompatActivity() {


    private lateinit var favoriteMovieRoomAdapter: FavoriteMovieRoomAdapter
    private lateinit var movieRoomView: MovieRoomView

    companion object {
        private lateinit var movieRoomView: MovieRoomView
        // store to database
        fun initFavoriteParam(
            id: Int, release: String, title: String, description: String, poster: String, context: Context,
            bar: (id: Int, release: String, title: String, description: String, poster: String, context: Context) -> Unit
        ) {
            bar(id, release, title, description, poster, context)
        }

        @SuppressLint("RestrictedApi")
        fun deleteFavoriteMovie(
            id: Int,
            release: String,
            title: String,
            description: String,
            poster: String,
            context: Context
        ) {

            println(
                "\n\t $id" +
                        "\n\t $release" +
                        "\n\t $title" +
                        "\n\t $description" +
                        "\n\t $poster" +
                        "\n\t $context"
            )

            val movieRoomModel = MovieRoomModel(
                id,
                release,
                title,
                description,
                poster
            )
            movieRoomView.delete(movieRoomModel)
        }
    }

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

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}
