/*
 * Copyright (c) 2019. SCODEID
 */

/**
 * @author
 * Yogi Arif Widodo
 * www.dicoding.com/users/297307
 * www.github.com/yogithesymbian
 * For Google Kejar 2019
 */
package com.scodeid.scholarshipexpertscodeidev2019.submission.submission1

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.scodeid.scholarshipexpertscodeidev2019.R
import com.scodeid.scholarshipexpertscodeidev2019.submission.model.MovieDataModels
import java.util.*
/**
 * THIS CLASS JUST FOR SUBMISSION 2 SOMETIMES WILL USE
 */
// adapter have complex
@Suppress("NAME_SHADOWING")
class MovieAdapter internal constructor(var context: Context?) : BaseAdapter() {
     private var dataModelMovies: ArrayList<MovieDataModels> = ArrayList()

    private val tagLog = "MovieAdapter"

    /**
     * get and setter
     *
     */
    override fun getCount(): Int {
        return dataModelMovies.size
    }

    override fun getItem(position: Int): Any {
        return dataModelMovies[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
        if (convertView == null)
        {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_movies, parent, false)
            // with params context for my dialogMovieBest
            val viewHolder =
                MovieViewHolder(convertView, context)
            val movie = getItem(position) as MovieDataModels
            viewHolder.bind(movie)
            return convertView
        }
        return convertView
    }

    override fun notifyDataSetChanged() {
        super.notifyDataSetChanged()
        Log.d(tagLog,"notifyDataChanged")
    }


}