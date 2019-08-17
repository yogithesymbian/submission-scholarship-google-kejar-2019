/*
 * Copyright (c) 2019. SCODEID
 */

package com.scodeid.scholarshipexpertscodeidev2019.adapter

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Filter
import android.widget.Filterable
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.scodeid.scholarshipexpertscodeidev2019.ItemClickRecyclerSupport
import com.scodeid.scholarshipexpertscodeidev2019.R
import com.scodeid.scholarshipexpertscodeidev2019.api.ApiEndPoint.Companion.POSTER_IMAGE
import com.scodeid.scholarshipexpertscodeidev2019.homeFirstView.MoviesTvWapiHomeDetailFragment
import com.scodeid.scholarshipexpertscodeidev2019.homeFirstView.MoviesTvWapiHomeFragment
import com.scodeid.scholarshipexpertscodeidev2019.homeFirstView.MoviesTvWapiHomeFragment.Companion.insertFavoriteTv
import com.scodeid.scholarshipexpertscodeidev2019.model.MoviesTvShowApiData
import com.scodeid.scholarshipexpertscodeidev2019.utils.debuggingMyScode
import kotlinx.android.synthetic.main.fragment_movie_dialog.*
import kotlinx.android.synthetic.main.fragment_movies_tv_show_recycler.view.*
import kotlinx.android.synthetic.main.item_movies_tv_shows.view.*


/**
 * @author
 * Created by scode on 15,July,2019
 * Yogi Arif Widodo
 * www.dicoding.com/users/297307
 * www.github.com/yogithesymbian
 * SCODEID company,
 * Indonesia, East Borneo.
 * ==============================================================
Android Studio 3.4.2
Build #AI-183.6156.11.34.5692245, built on June 27, 2019
JRE: 1.8.0_152-release-1343-b16-5323222 amd64
JVM: OpenJDK 64-Bit Server VM by JetBrains s.r.o
Linux 4.19.0-kali5-amd64
 * ==============================================================
 *              _               _         _               _____
 *   ___ _   _| |__  _ __ ___ (_)___ ___(_) ___  _ __   |___ /
 * / __| | | | '_ \| '_ ` _ \| / __/ __| |/ _ \| '_ \    |_ \
 * \__ \ |_| | |_) | | | | | | \__ \__ \ | (_) | | | |  ___) |
 * |___/\__,_|_.__/|_| |_| |_|_|___/___/_|\___/|_| |_| |____/
 *
 *
 */

class MoviesTvShowApiAdapter(
    private var arrayListMoviesTvShow: ArrayList<MoviesTvShowApiData>
) : RecyclerView.Adapter<MoviesTvShowApiAdapter.ViewHolder>(), Filterable {

    private var arrayListMoviesTvShowTemp = ArrayList<MoviesTvShowApiData>()

    companion object {
        val TAG_LOG: String = MoviesTvShowApiAdapter::class.java.simpleName
    }

    init {
        setHasStableIds(true)
        arrayListMoviesTvShowTemp = arrayListMoviesTvShow
    }

    fun setData(itemsMovie: ArrayList<MoviesTvShowApiData>) {
        arrayListMoviesTvShow.clear()
        arrayListMoviesTvShow.addAll(itemsMovie)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_movies_tv_shows, parent, false))
        val context = parent.context
        ItemClickRecyclerSupport
            .addTo(parent.recycler_view_tv_show)
            .setOnItemClickListener(object : ItemClickRecyclerSupport.OnItemClickListener {

                override fun onItemClicked(recyclerView: RecyclerView, position: Int, v: View) {
                    openingTvShowDetail(arrayListMoviesTvShow[position], context)
                    debuggingMyScode(
                        TAG_LOG,
                        "Try opening something about the detail tv show" + arrayListMoviesTvShow[position].name
                    )
                }

            })
        ItemClickRecyclerSupport
            .addTo(parent.recycler_view_tv_show)
            .setOnItemLongClickListener(object : ItemClickRecyclerSupport.OnItemLongClickListener {

                override fun onItemLongClicked(recyclerView: RecyclerView, position: Int, v: View): Boolean {
                    debuggingMyScode(TAG_LOG, "Try onLongClick itemMovies")
                    Toast.makeText(context, arrayListMoviesTvShow[position].name, Toast.LENGTH_SHORT)
                        .show()
                    return true
                }

            })
        return view
    }

    private fun openingTvShowDetail(moviesTvShowApiData: MoviesTvShowApiData, context: Context) {

        // instance DetailCategoryFragment
        val mMoviesTvShowDetailFragment =
            MoviesTvWapiHomeDetailFragment()

        // tx tx data between the fragment using Bundle
        val mBundle = Bundle()

        mBundle.putParcelable(MoviesTvWapiHomeDetailFragment.EXTRA_TV_DETAILS, moviesTvShowApiData)
        mMoviesTvShowDetailFragment.arguments = mBundle

        //manage the fragment manager in this fragment
        val mFragmentManager = (context as AppCompatActivity).supportFragmentManager

        //check for replace and go
        val mFragmentTransaction = mFragmentManager.beginTransaction()
        // replace the container frameLayout
        mFragmentTransaction.replace(
            R.id.frame_container_tv_show,
            mMoviesTvShowDetailFragment,
            MoviesTvWapiHomeDetailFragment::class.java.simpleName
        )
        // set back stack null to get on back pressed !exit
        mFragmentTransaction.addToBackStack(null)
        // commit the fragment
        mFragmentTransaction.commit()
        debuggingMyScode(TAG_LOG, "Fragment has commit")
    }


    override fun getItemCount(): Int {
        return arrayListMoviesTvShow.size
    }

    @SuppressLint("PrivateResource")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val title = arrayListMoviesTvShow[position].name
        val voteAverage = arrayListMoviesTvShow[position].voteAverage //score /rate
        val imagePoster = arrayListMoviesTvShow[position].posterPath
        val context = holder.itemView.context

        val movieDialog = Dialog(context)
        val animation = AnimationUtils.loadAnimation(context, R.anim.fade_scale)

        holder.itemView.checkbox_fav_tv.setOnCheckedChangeListener { buttonView, isChecked ->
            buttonView.startAnimation(animation)
            if (isChecked) MoviesTvWapiHomeFragment.initFavoriteTvParam(
                title,
                voteAverage.toInt(),
                imagePoster,
                context,
                ::insertFavoriteTv
            )
            else Toast.makeText(
                context, "Sorry ,Delete item at this time only on favorite view, you cant do it at here.",
                Toast.LENGTH_LONG
            )
                .show()
        }

        holder.itemView.text_tv_movie_name.text = title
        holder.itemView.text_tv_rate.text = voteAverage.toString()

        context.let {
            Glide.with(it)
                .asBitmap()
                .load(POSTER_IMAGE + "w185" + imagePoster)
                .error(R.color.error_color_material_light)
                .format(DecodeFormat.PREFER_ARGB_8888)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .into(holder.itemView.image_tv_movie)
        }

        holder
            .itemView.image_tv_movie.setOnClickListener {
            debuggingMyScode(TAG_LOG + "Bind", "image ${holder.itemView.image_tv_movie} got clicked and Try opening dialog view")

            /**
             * DIALOG view movie catalogue
             * override the layout movie with tv_show
             */
            movieDialog.setContentView(R.layout.fragment_movie_dialog)
            // bind onClick on there layout movie dialogUe

            movieDialog.button_close.setOnClickListener {
                movieDialog.dismiss()
            }

            Glide.with(it)
                .asBitmap()
                .load(POSTER_IMAGE + "w342" + imagePoster)
                .error(R.color.error_color_material_light)
                .format(DecodeFormat.PREFER_ARGB_8888)
                .into(movieDialog.image_dialog_home)

            movieDialog.window?.attributes?.windowAnimations = R.style.AnimBottomTop
            movieDialog.show()
            // end of dialog view
        }
        // end of image clicked
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence, results: FilterResults) {
                arrayListMoviesTvShow = results.values as ArrayList<MoviesTvShowApiData>
                notifyDataSetChanged()
            }

            override fun performFiltering(constraint: CharSequence): FilterResults {
                val charStr = constraint.toString()
                arrayListMoviesTvShow = if (charStr.isEmpty()) {
                    arrayListMoviesTvShowTemp //save before result data shown and return this for isEmpty
                } else {

                    val filterListener = ArrayList<MoviesTvShowApiData>()
                    for (row in arrayListMoviesTvShow) {

                        val originalTitle = row.originalName.toLowerCase().contains(charStr.toLowerCase())
                        val title = row.name.toLowerCase().contains(charStr.toLowerCase())

                        if (title || originalTitle) {
                            filterListener.add(row)
                        }
                    }
                    filterListener // set item from result filter
                }

                val filterResult = FilterResults()
                filterResult.values = arrayListMoviesTvShow
                return filterResult
            }
        }
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view)

}