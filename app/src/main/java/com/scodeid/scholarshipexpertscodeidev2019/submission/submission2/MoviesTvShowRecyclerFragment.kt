/*
 * Copyright (c) 2019. SCODEID
 */

package com.scodeid.scholarshipexpertscodeidev2019.submission.submission2


import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.scodeid.scholarshipexpertscodeidev2019.R
import com.scodeid.scholarshipexpertscodeidev2019.submission.ItemClickRecyclerSupport
import com.scodeid.scholarshipexpertscodeidev2019.submission.model.MovieDataModelsRecyclerTv
import kotlinx.android.synthetic.main.fragment_movies_tv_show.*
import kotlinx.android.synthetic.main.fragment_movies_tv_show.view.*

class MoviesTvShowRecyclerFragment : Fragment() {

    companion object {
        var tagLog = "MoviesTvShowRecyclerFragment"
        var arrayList = ArrayList<MovieDataModelsRecyclerTv>()
    }


    override fun onAttachFragment(childFragment: Fragment) {
        super.onAttachFragment(childFragment)
        Log.d(tagLog, "onAttachFragment")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(tagLog, "onCreate")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_movies_tv_show, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log.d(tagLog, "onActivityCreated")

        // this function is same like  on MovieList Home but the concept are different


        /**
         * Handling Recycler from choreographs / skipping frame / freeze in Do in Background in the in of in background :D
         */

        val doInBackGroundMovie = 2000 //in just start
        Handler().postDelayed(object : Runnable {
            override fun run() {
                frame_progress_tv_show.visibility = View.VISIBLE
                card_tv_show.visibility = View.GONE
                this.finish()

                val doInInBackGroundMovie = 100 //in just start
                Handler().postDelayed(object : Runnable {
                    override fun run() {
                        this.finish()
                        frame_progress_tv_show?.visibility = View.GONE //for other background process timing set image on adapter
                        Log.d(tagLog,"Loading ... for get and load data in background")
                        card_tv_show.visibility = View.VISIBLE

                    }

                    private fun finish() {

                    }
                }, doInInBackGroundMovie.toLong())
            }
            private fun finish() {
                arrayList.clear() //clear while back maybe
                /**
                 * From Object class / Innerclass getListMovie for access resources
                 * i can't handle on separate file cause there is different lifecycle
                 * i have stuck with this rare case on 1 day
                 * i cant't handle on activity for load data cause i was call the var in context / fragment
                 * so fragment and activity is different lifecycle like applicationContext. / context. / this. / this@main
                 */

                fun getListMoviesDataAct(): java.util.ArrayList<MovieDataModelsRecyclerTv> {
                    // for return
                    val arrayList = java.util.ArrayList<MovieDataModelsRecyclerTv>()
                    // load object data
                    val dataMovies = funcLoadData()
                    // looping was object data
                    for (aMovieData in dataMovies) {
                        val movieDataModels = MovieDataModelsRecyclerTv(
                            "" + aMovieData[0],
                            "" + aMovieData[1],
                            "" + aMovieData[2],
                            "" + aMovieData[3],
                            "" + aMovieData[4],
                            "" + aMovieData[5],
                            "" + aMovieData[6],
                            "" + aMovieData[7],
                            "" + aMovieData[8],
                            "" + aMovieData[9],
                            "" + aMovieData[10],
                            "" + aMovieData[11],
                            "" + aMovieData[12],
                            "" + aMovieData[13],
                            "" + aMovieData[14],
                            "" + aMovieData[15],
                            "" + aMovieData[16],
                            "" + aMovieData[17],
                            "" + aMovieData[18],
                            "" + aMovieData[19],
                            "" + aMovieData[20]
                        )
                        arrayList.add(movieDataModels)
                    }
                    return arrayList //return
                }

                /**
                 * End of From Object Class
                 */


                arrayList.addAll(getListMoviesDataAct())

                val movieTvRecyclerAdapter = context?.let { MoviesTvShowRecyclerAdapter(it) }
                movieTvRecyclerAdapter?.listCardTvMovieRecycler = arrayList
                recycler_view_tv_show.adapter = movieTvRecyclerAdapter
            }
        }, doInBackGroundMovie.toLong())

        /**
         * End Of Handling Recycler from choreographs /
         */

    }


    override fun onStart() {
        super.onStart()
        Log.d(tagLog, "onStart")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(tagLog, "onViewCreated")

        /**
         * Intent With Pojo so I create here for between FRAGMENT
         */
        ItemClickRecyclerSupport.addTo(view.recycler_view_tv_show).setOnItemClickListener(object : ItemClickRecyclerSupport.OnItemClickListener
        {
            override fun onItemClicked(recyclerView: RecyclerView, position: Int, v: View) {

                Log.d(tagLog,"Try Opening Tv Movie Detail" + arrayList[position].movieNameTv)
                openingTvShowDetail(arrayList[position])
            }
        })
    }

    private fun openingTvShowDetail(movieDataModelsRecycler: MovieDataModelsRecyclerTv) {
        // instance DetailCategoryFragment
        val mMoviesTvShowDetailFragment = MoviesTvShowDetailFragment()

        // tx tx data between the fragment using Bundle
        val mBundle = Bundle()

        val movieDataModels = MovieDataModelsRecyclerTv(
            "" + movieDataModelsRecycler.moviePictureTv?.toInt(),
            "" + movieDataModelsRecycler.moviePictureBackgroundTv?.toInt(),
            "" + movieDataModelsRecycler.moviePictureRelated1Tv?.toInt(),
            "" + movieDataModelsRecycler.moviePictureRelated2Tv?.toInt(),
            "" + movieDataModelsRecycler.moviePictureRelated3Tv?.toInt(),
            "" + movieDataModelsRecycler.movieNameTv,
            "" + movieDataModelsRecycler.movieOverviewTv,
            "" + movieDataModelsRecycler.movieScoreTv,
            "" + movieDataModelsRecycler.movieCreator1,
            "" + movieDataModelsRecycler.movieCreator2,
            "" + movieDataModelsRecycler.movieCreator3,
            "" + movieDataModelsRecycler.movieStatusTv,
            "" + movieDataModelsRecycler.movieNetworkTv,
            "" + movieDataModelsRecycler.movieOrigLangTv,
            "" + movieDataModelsRecycler.movieGenresTv,
            "" + movieDataModelsRecycler.movieKeywordsTv,
            "" + movieDataModelsRecycler.movieTypeTv,
            "" + movieDataModelsRecycler.movieRankLastTodayTv,
            "" + movieDataModelsRecycler.movieRAnkLastWeekTv,
            "" + movieDataModelsRecycler.movieReleaseTv,
            "" + movieDataModelsRecycler.movieRuntimeTv
        )

        mBundle.putParcelable(MoviesTvShowDetailFragment.extraTvDetails, movieDataModels)
        mMoviesTvShowDetailFragment.arguments = mBundle

        //manage the fragment manager in this fragment
        val mFragmentManager = fragmentManager

        //check for replace and go
        if (mFragmentManager != null)
        {
            // use FragTransaction
            val mFragmentTransaction = mFragmentManager.beginTransaction()
            // replace the container frameLayout
            mFragmentTransaction.replace(R.id.frame_container_tv_show, mMoviesTvShowDetailFragment,  MoviesTvShowDetailFragment::class.java.simpleName)
            // set back stack null to get on back pressed !exit
            mFragmentTransaction.addToBackStack(null)
            // commit the fragment
            mFragmentTransaction.commit()
        }
    }

    override fun onResume() {
        super.onResume()
        Log.d(tag, "onResume Tabs")
        recycler_view_tv_show.setHasFixedSize(true)
        recycler_view_tv_show.layoutManager = LinearLayoutManager(context)

    }


    override fun onPause() {
        super.onPause()
        Log.d(tagLog, "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d(tagLog, "onStop")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d(tagLog, "onDestroyView")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(tag, "on Destroy")
    }

    override fun onDetach() {
        super.onDetach()
        Log.d(tagLog, "onDetach")
//        arrayList.clear() //clear when out
    }


    /**
     * This MovieDataObject.kt Object Array
     */

    private fun passingDataArrayResources(rArrayString: Int, position: Int): String? {
        return (context?.resources?.getStringArray(rArrayString)?.get(position))
    }

    /**
     * STAY UNFOLD this array :'( later's will use looping for set positing foreach for in indices
     * while my movies has more than > 15
     */
//    for (i in movieName.indices) { foreach
    @SuppressLint("Recycle", "ResourceType") //recycle the obtainTypedArray could ? just implement from exercise11DataHeroesRecycler.Java
    private fun funcLoadData(): Array<Array<String>> {
//    for (i in )
        /*
            return arrayOf(
                arrayOf(

                    ""+ (context?.resources?.obtainTypedArray(R.array.data_movie_image_tv_show)?.getResourceId(0,-1)),
                    ""+ (context?.resources?.obtainTypedArray(R.array.data_movie_image_background_tv_show)?.getResourceId(0,-1)),
                    ""+ (context?.resources?.obtainTypedArray(R.array.data_movie_image_related1_tv_show)?.getResourceId(0,-1)),
                    ""+ (context?.resources?.obtainTypedArray(R.array.data_movie_image_related2_tv_show)?.getResourceId(0,-1)),
                    ""+ (context?.resources?.obtainTypedArray(R.array.data_movie_image_related3_tv_show)?.getResourceId(0,-1)),

                    ""+ (context?.resources?.getStringArray(R.array.data_movie_name_tv_show)?.get(0)), //movieName
                    ""+ (passingDataArrayResources(R.array.data_movie_overview_tv_show,0)), //movieRelease
                    ""+ (passingDataArrayResources(R.array.data_movie_score_tv,0)),

                    ""+ (passingDataArrayResources(R.array.data_movie_creator1,0)),
                    ""+ (passingDataArrayResources(R.array.data_movie_creator2,0)),
                    ""+ (passingDataArrayResources(R.array.data_movie_creator3,0)),

                    ""+ (passingDataArrayResources(R.array.data_movie_status_tv_show,0)),
                    ""+ (passingDataArrayResources(R.array.data_movie_network_tv_show, 0)),
                    ""+ (passingDataArrayResources(R.array.data_movie_original_language_tv_show,0)),

                    ""+ (passingDataArrayResources(R.array.data_movie_genres_tv_show,0)),
                    ""+ (passingDataArrayResources(R.array.data_movie_keywords_tv_show,0)),
                    ""+ (passingDataArrayResources(R.array.data_movie_type_tv_show,0)),

                    ""+ (passingDataArrayResources(R.array.data_movie_last_today_tv_show,0)),
                    ""+ (passingDataArrayResources(R.array.data_movie_last_week_tv_show,0)),
                    ""+ (passingDataArrayResources(R.array.data_movie_release_tv_show,0)),
                    ""+ (passingDataArrayResources(R.array.data_movie_runtime_tv_show,0))
                ),

                arrayOf(

                    ""+ (context?.resources?.obtainTypedArray(R.array.data_movie_image_tv_show)?.getResourceId(1,-1)),
                    ""+ (context?.resources?.obtainTypedArray(R.array.data_movie_image_background_tv_show)?.getResourceId(1,-1)),
                    ""+ (context?.resources?.obtainTypedArray(R.array.data_movie_image_related1_tv_show)?.getResourceId(1,-1)),
                    ""+ (context?.resources?.obtainTypedArray(R.array.data_movie_image_related2_tv_show)?.getResourceId(1,-1)),
                    ""+ (context?.resources?.obtainTypedArray(R.array.data_movie_image_related3_tv_show)?.getResourceId(1,-1)),

                    ""+ (context?.resources?.getStringArray(R.array.data_movie_name_tv_show)?.get(1)), //movieName
                    ""+ (passingDataArrayResources(R.array.data_movie_overview_tv_show,1)), //movieRelease
                    ""+ (passingDataArrayResources(R.array.data_movie_score_tv,1)),

                    ""+ (passingDataArrayResources(R.array.data_movie_creator1,1)),
                    ""+ (passingDataArrayResources(R.array.data_movie_creator2,1)),
                    ""+ (passingDataArrayResources(R.array.data_movie_creator3,1)),

                    ""+ (passingDataArrayResources(R.array.data_movie_status_tv_show,1)),
                    ""+ (passingDataArrayResources(R.array.data_movie_network_tv_show, 1)),
                    ""+ (passingDataArrayResources(R.array.data_movie_original_language_tv_show,1)),

                    ""+ (passingDataArrayResources(R.array.data_movie_genres_tv_show,1)),
                    ""+ (passingDataArrayResources(R.array.data_movie_keywords_tv_show,1)),
                    ""+ (passingDataArrayResources(R.array.data_movie_type_tv_show,1)),

                    ""+ (passingDataArrayResources(R.array.data_movie_last_today_tv_show,1)),
                    ""+ (passingDataArrayResources(R.array.data_movie_last_week_tv_show,1)),
                    ""+ (passingDataArrayResources(R.array.data_movie_release_tv_show,1)),
                    ""+ (passingDataArrayResources(R.array.data_movie_runtime_tv_show,1))
                ),

                arrayOf(

                    ""+ (context?.resources?.obtainTypedArray(R.array.data_movie_image_tv_show)?.getResourceId(2,-1)),
                    ""+ (context?.resources?.obtainTypedArray(R.array.data_movie_image_background_tv_show)?.getResourceId(2,-1)),
                    ""+ (context?.resources?.obtainTypedArray(R.array.data_movie_image_related1_tv_show)?.getResourceId(2,-1)),
                    ""+ (context?.resources?.obtainTypedArray(R.array.data_movie_image_related2_tv_show)?.getResourceId(2,-1)),
                    ""+ (context?.resources?.obtainTypedArray(R.array.data_movie_image_related3_tv_show)?.getResourceId(2,-1)),

                    ""+ (context?.resources?.getStringArray(R.array.data_movie_name_tv_show)?.get(2)), //movieName
                    ""+ (passingDataArrayResources(R.array.data_movie_overview_tv_show,2)), //movieRelease
                    ""+ (passingDataArrayResources(R.array.data_movie_score_tv,2)),

                    ""+ (passingDataArrayResources(R.array.data_movie_creator1,2)),
                    ""+ (passingDataArrayResources(R.array.data_movie_creator2,2)),
                    ""+ (passingDataArrayResources(R.array.data_movie_creator3,2)),

                    ""+ (passingDataArrayResources(R.array.data_movie_status_tv_show,2)),
                    ""+ (passingDataArrayResources(R.array.data_movie_network_tv_show, 2)),
                    ""+ (passingDataArrayResources(R.array.data_movie_original_language_tv_show,2)),

                    ""+ (passingDataArrayResources(R.array.data_movie_genres_tv_show,2)),
                    ""+ (passingDataArrayResources(R.array.data_movie_keywords_tv_show,2)),
                    ""+ (passingDataArrayResources(R.array.data_movie_type_tv_show,2)),

                    ""+ (passingDataArrayResources(R.array.data_movie_last_today_tv_show,2)),
                    ""+ (passingDataArrayResources(R.array.data_movie_last_week_tv_show,2)),
                    ""+ (passingDataArrayResources(R.array.data_movie_release_tv_show,2)),
                    ""+ (passingDataArrayResources(R.array.data_movie_runtime_tv_show,2))
                ),

                arrayOf(

                    ""+ (context?.resources?.obtainTypedArray(R.array.data_movie_image_tv_show)?.getResourceId(3,-1)),
                    ""+ (context?.resources?.obtainTypedArray(R.array.data_movie_image_background_tv_show)?.getResourceId(3,-1)),
                    ""+ (context?.resources?.obtainTypedArray(R.array.data_movie_image_related1_tv_show)?.getResourceId(3,-1)),
                    ""+ (context?.resources?.obtainTypedArray(R.array.data_movie_image_related2_tv_show)?.getResourceId(3,-1)),
                    ""+ (context?.resources?.obtainTypedArray(R.array.data_movie_image_related3_tv_show)?.getResourceId(3,-1)),

                    ""+ (context?.resources?.getStringArray(R.array.data_movie_name_tv_show)?.get(3)), //movieName
                    ""+ (passingDataArrayResources(R.array.data_movie_overview_tv_show,3)), //movieRelease
                    ""+ (passingDataArrayResources(R.array.data_movie_score_tv,3)),

                    ""+ (passingDataArrayResources(R.array.data_movie_creator1,3)),
                    ""+ (passingDataArrayResources(R.array.data_movie_creator2,3)),
                    ""+ (passingDataArrayResources(R.array.data_movie_creator3,3)),

                    ""+ (passingDataArrayResources(R.array.data_movie_status_tv_show,3)),
                    ""+ (passingDataArrayResources(R.array.data_movie_network_tv_show, 3)),
                    ""+ (passingDataArrayResources(R.array.data_movie_original_language_tv_show,3)),

                    ""+ (passingDataArrayResources(R.array.data_movie_genres_tv_show,3)),
                    ""+ (passingDataArrayResources(R.array.data_movie_keywords_tv_show,3)),
                    ""+ (passingDataArrayResources(R.array.data_movie_type_tv_show,3)),

                    ""+ (passingDataArrayResources(R.array.data_movie_last_today_tv_show,3)),
                    ""+ (passingDataArrayResources(R.array.data_movie_last_week_tv_show,3)),
                    ""+ (passingDataArrayResources(R.array.data_movie_release_tv_show,3)),
                    ""+ (passingDataArrayResources(R.array.data_movie_runtime_tv_show,3))
                ),

                arrayOf(

                    ""+ (context?.resources?.obtainTypedArray(R.array.data_movie_image_tv_show)?.getResourceId(4,-1)),
                    ""+ (context?.resources?.obtainTypedArray(R.array.data_movie_image_background_tv_show)?.getResourceId(4,-1)),
                    ""+ (context?.resources?.obtainTypedArray(R.array.data_movie_image_related1_tv_show)?.getResourceId(4,-1)),
                    ""+ (context?.resources?.obtainTypedArray(R.array.data_movie_image_related2_tv_show)?.getResourceId(4,-1)),
                    ""+ (context?.resources?.obtainTypedArray(R.array.data_movie_image_related3_tv_show)?.getResourceId(4,-1)),

                    ""+ (context?.resources?.getStringArray(R.array.data_movie_name_tv_show)?.get(4)), //movieName
                    ""+ (passingDataArrayResources(R.array.data_movie_overview_tv_show,4)), //movieRelease
                    ""+ (passingDataArrayResources(R.array.data_movie_score_tv,4)),

                    ""+ (passingDataArrayResources(R.array.data_movie_creator1,4)),
                    ""+ (passingDataArrayResources(R.array.data_movie_creator2,4)),
                    ""+ (passingDataArrayResources(R.array.data_movie_creator3,4)),

                    ""+ (passingDataArrayResources(R.array.data_movie_status_tv_show,4)),
                    ""+ (passingDataArrayResources(R.array.data_movie_network_tv_show, 4)),
                    ""+ (passingDataArrayResources(R.array.data_movie_original_language_tv_show,4)),

                    ""+ (passingDataArrayResources(R.array.data_movie_genres_tv_show,4)),
                    ""+ (passingDataArrayResources(R.array.data_movie_keywords_tv_show,4)),
                    ""+ (passingDataArrayResources(R.array.data_movie_type_tv_show,4)),

                    ""+ (passingDataArrayResources(R.array.data_movie_last_today_tv_show,4)),
                    ""+ (passingDataArrayResources(R.array.data_movie_last_week_tv_show,4)),
                    ""+ (passingDataArrayResources(R.array.data_movie_release_tv_show,4)),
                    ""+ (passingDataArrayResources(R.array.data_movie_runtime_tv_show,4))
                ),

                arrayOf(

                    ""+ (context?.resources?.obtainTypedArray(R.array.data_movie_image_tv_show)?.getResourceId(5,-1)),
                    ""+ (context?.resources?.obtainTypedArray(R.array.data_movie_image_background_tv_show)?.getResourceId(5,-1)),
                    ""+ (context?.resources?.obtainTypedArray(R.array.data_movie_image_related1_tv_show)?.getResourceId(5,-1)),
                    ""+ (context?.resources?.obtainTypedArray(R.array.data_movie_image_related2_tv_show)?.getResourceId(5,-1)),
                    ""+ (context?.resources?.obtainTypedArray(R.array.data_movie_image_related3_tv_show)?.getResourceId(5,-1)),

                    ""+ (context?.resources?.getStringArray(R.array.data_movie_name_tv_show)?.get(5)), //movieName
                    ""+ (passingDataArrayResources(R.array.data_movie_overview_tv_show,5)), //movieRelease
                    ""+ (passingDataArrayResources(R.array.data_movie_score_tv,5)),

                    ""+ (passingDataArrayResources(R.array.data_movie_creator1,5)),
                    ""+ (passingDataArrayResources(R.array.data_movie_creator2,5)),
                    ""+ (passingDataArrayResources(R.array.data_movie_creator3,5)),

                    ""+ (passingDataArrayResources(R.array.data_movie_status_tv_show,5)),
                    ""+ (passingDataArrayResources(R.array.data_movie_network_tv_show, 5)),
                    ""+ (passingDataArrayResources(R.array.data_movie_original_language_tv_show,5)),

                    ""+ (passingDataArrayResources(R.array.data_movie_genres_tv_show,5)),
                    ""+ (passingDataArrayResources(R.array.data_movie_keywords_tv_show,5)),
                    ""+ (passingDataArrayResources(R.array.data_movie_type_tv_show,5)),

                    ""+ (passingDataArrayResources(R.array.data_movie_last_today_tv_show,5)),
                    ""+ (passingDataArrayResources(R.array.data_movie_last_week_tv_show,5)),
                    ""+ (passingDataArrayResources(R.array.data_movie_release_tv_show,5)),
                    ""+ (passingDataArrayResources(R.array.data_movie_runtime_tv_show,5))
                ),

                arrayOf(

                    ""+ (context?.resources?.obtainTypedArray(R.array.data_movie_image_tv_show)?.getResourceId(6,-1)),
                    ""+ (context?.resources?.obtainTypedArray(R.array.data_movie_image_background_tv_show)?.getResourceId(6,-1)),
                    ""+ (context?.resources?.obtainTypedArray(R.array.data_movie_image_related1_tv_show)?.getResourceId(6,-1)),
                    ""+ (context?.resources?.obtainTypedArray(R.array.data_movie_image_related2_tv_show)?.getResourceId(6,-1)),
                    ""+ (context?.resources?.obtainTypedArray(R.array.data_movie_image_related3_tv_show)?.getResourceId(6,-1)),

                    ""+ (context?.resources?.getStringArray(R.array.data_movie_name_tv_show)?.get(6)), //movieName
                    ""+ (passingDataArrayResources(R.array.data_movie_overview_tv_show,6)), //movieRelease
                    ""+ (passingDataArrayResources(R.array.data_movie_score_tv,6)),

                    ""+ (passingDataArrayResources(R.array.data_movie_creator1,6)),
                    ""+ (passingDataArrayResources(R.array.data_movie_creator2,6)),
                    ""+ (passingDataArrayResources(R.array.data_movie_creator3,6)),

                    ""+ (passingDataArrayResources(R.array.data_movie_status_tv_show,6)),
                    ""+ (passingDataArrayResources(R.array.data_movie_network_tv_show, 6)),
                    ""+ (passingDataArrayResources(R.array.data_movie_original_language_tv_show,6)),

                    ""+ (passingDataArrayResources(R.array.data_movie_genres_tv_show,6)),
                    ""+ (passingDataArrayResources(R.array.data_movie_keywords_tv_show,6)),
                    ""+ (passingDataArrayResources(R.array.data_movie_type_tv_show,6)),

                    ""+ (passingDataArrayResources(R.array.data_movie_last_today_tv_show,6)),
                    ""+ (passingDataArrayResources(R.array.data_movie_last_week_tv_show,6)),
                    ""+ (passingDataArrayResources(R.array.data_movie_release_tv_show,6)),
                    ""+ (passingDataArrayResources(R.array.data_movie_runtime_tv_show,6))
                ),

                arrayOf(

                    ""+ (context?.resources?.obtainTypedArray(R.array.data_movie_image_tv_show)?.getResourceId(7,-1)),
                    ""+ (context?.resources?.obtainTypedArray(R.array.data_movie_image_background_tv_show)?.getResourceId(7,-1)),
                    ""+ (context?.resources?.obtainTypedArray(R.array.data_movie_image_related1_tv_show)?.getResourceId(7,-1)),
                    ""+ (context?.resources?.obtainTypedArray(R.array.data_movie_image_related2_tv_show)?.getResourceId(7,-1)),
                    ""+ (context?.resources?.obtainTypedArray(R.array.data_movie_image_related3_tv_show)?.getResourceId(7,-1)),

                    ""+ (context?.resources?.getStringArray(R.array.data_movie_name_tv_show)?.get(7)), //movieName
                    ""+ (passingDataArrayResources(R.array.data_movie_overview_tv_show,7)), //movieRelease
                    ""+ (passingDataArrayResources(R.array.data_movie_score_tv,7)),

                    ""+ (passingDataArrayResources(R.array.data_movie_creator1,7)),
                    ""+ (passingDataArrayResources(R.array.data_movie_creator2,7)),
                    ""+ (passingDataArrayResources(R.array.data_movie_creator3,7)),

                    ""+ (passingDataArrayResources(R.array.data_movie_status_tv_show,7)),
                    ""+ (passingDataArrayResources(R.array.data_movie_network_tv_show, 7)),
                    ""+ (passingDataArrayResources(R.array.data_movie_original_language_tv_show,7)),

                    ""+ (passingDataArrayResources(R.array.data_movie_genres_tv_show,7)),
                    ""+ (passingDataArrayResources(R.array.data_movie_keywords_tv_show,7)),
                    ""+ (passingDataArrayResources(R.array.data_movie_type_tv_show,7)),

                    ""+ (passingDataArrayResources(R.array.data_movie_last_today_tv_show,7)),
                    ""+ (passingDataArrayResources(R.array.data_movie_last_week_tv_show,7)),
                    ""+ (passingDataArrayResources(R.array.data_movie_release_tv_show,7)),
                    ""+ (passingDataArrayResources(R.array.data_movie_runtime_tv_show,7))
                ),

                arrayOf(

                    ""+ (context?.resources?.obtainTypedArray(R.array.data_movie_image_tv_show)?.getResourceId(8,-1)),
                    ""+ (context?.resources?.obtainTypedArray(R.array.data_movie_image_background_tv_show)?.getResourceId(8,-1)),
                    ""+ (context?.resources?.obtainTypedArray(R.array.data_movie_image_related1_tv_show)?.getResourceId(8,-1)),
                    ""+ (context?.resources?.obtainTypedArray(R.array.data_movie_image_related2_tv_show)?.getResourceId(8,-1)),
                    ""+ (context?.resources?.obtainTypedArray(R.array.data_movie_image_related3_tv_show)?.getResourceId(8,-1)),

                    ""+ (context?.resources?.getStringArray(R.array.data_movie_name_tv_show)?.get(8)), //movieName
                    ""+ (passingDataArrayResources(R.array.data_movie_overview_tv_show,8)), //movieRelease
                    ""+ (passingDataArrayResources(R.array.data_movie_score_tv,8)),

                    ""+ (passingDataArrayResources(R.array.data_movie_creator1,8)),
                    ""+ (passingDataArrayResources(R.array.data_movie_creator2,8)),
                    ""+ (passingDataArrayResources(R.array.data_movie_creator3,8)),

                    ""+ (passingDataArrayResources(R.array.data_movie_status_tv_show,8)),
                    ""+ (passingDataArrayResources(R.array.data_movie_network_tv_show, 8)),
                    ""+ (passingDataArrayResources(R.array.data_movie_original_language_tv_show,8)),

                    ""+ (passingDataArrayResources(R.array.data_movie_genres_tv_show,8)),
                    ""+ (passingDataArrayResources(R.array.data_movie_keywords_tv_show,8)),
                    ""+ (passingDataArrayResources(R.array.data_movie_type_tv_show,8)),

                    ""+ (passingDataArrayResources(R.array.data_movie_last_today_tv_show,8)),
                    ""+ (passingDataArrayResources(R.array.data_movie_last_week_tv_show,8)),
                    ""+ (passingDataArrayResources(R.array.data_movie_release_tv_show,8)),
                    ""+ (passingDataArrayResources(R.array.data_movie_runtime_tv_show,8))
                ),

                arrayOf(

                    ""+ (context?.resources?.obtainTypedArray(R.array.data_movie_image_tv_show)?.getResourceId(9,-1)),
                    ""+ (context?.resources?.obtainTypedArray(R.array.data_movie_image_background_tv_show)?.getResourceId(9,-1)),
                    ""+ (context?.resources?.obtainTypedArray(R.array.data_movie_image_related1_tv_show)?.getResourceId(9,-1)),
                    ""+ (context?.resources?.obtainTypedArray(R.array.data_movie_image_related2_tv_show)?.getResourceId(9,-1)),
                    ""+ (context?.resources?.obtainTypedArray(R.array.data_movie_image_related3_tv_show)?.getResourceId(9,-1)),

                    ""+ (context?.resources?.getStringArray(R.array.data_movie_name_tv_show)?.get(9)), //movieName
                    ""+ (passingDataArrayResources(R.array.data_movie_overview_tv_show,9)), //movieRelease
                    ""+ (passingDataArrayResources(R.array.data_movie_score_tv,9)),

                    ""+ (passingDataArrayResources(R.array.data_movie_creator1,9)),
                    ""+ (passingDataArrayResources(R.array.data_movie_creator2,9)),
                    ""+ (passingDataArrayResources(R.array.data_movie_creator3,9)),

                    ""+ (passingDataArrayResources(R.array.data_movie_status_tv_show,9)),
                    ""+ (passingDataArrayResources(R.array.data_movie_network_tv_show, 9)),
                    ""+ (passingDataArrayResources(R.array.data_movie_original_language_tv_show,9)),

                    ""+ (passingDataArrayResources(R.array.data_movie_genres_tv_show,9)),
                    ""+ (passingDataArrayResources(R.array.data_movie_keywords_tv_show,9)),
                    ""+ (passingDataArrayResources(R.array.data_movie_type_tv_show,9)),

                    ""+ (passingDataArrayResources(R.array.data_movie_last_today_tv_show,9)),
                    ""+ (passingDataArrayResources(R.array.data_movie_last_week_tv_show,9)),
                    ""+ (passingDataArrayResources(R.array.data_movie_release_tv_show,9)),
                    ""+ (passingDataArrayResources(R.array.data_movie_runtime_tv_show,9))
                )
        )
*/
        return arrayOf()
    }

    /**
     * END OF This MovieDataObject.kt Object Array
     */


}
