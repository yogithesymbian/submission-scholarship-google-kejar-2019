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
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.scodeid.scholarshipexpertscodeidev2019.R
import com.scodeid.scholarshipexpertscodeidev2019.submission.model.MovieDataModelsRecycler
import com.scodeid.scholarshipexpertscodeidev2019.submission.model.MovieTabColorModel
import kotlinx.android.synthetic.main.fragment_movies_home_recycler.*

class MoviesHomeRecyclerFragment : androidx.fragment.app.Fragment() {

    companion object {

        var tagLog = "MoviesHomeRecyclerFragment"
        @JvmField var key = "keyPojoSubTab"
        fun newInstanceData(pojoSubTab: MovieTabColorModel) : MoviesHomeRecyclerFragment
        {
            val fragment = MoviesHomeRecyclerFragment()
            val args = Bundle()
            args.putParcelable(key, pojoSubTab)
            fragment.arguments = args
            return fragment
        }

        var arrayList = ArrayList<MovieDataModelsRecycler>()

    }

    override fun onAttachFragment(childFragment: Fragment) {
        super.onAttachFragment(childFragment)
        Log.d(tagLog,"onAttachFragment")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(tagLog,"onCreate")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_movies_home_recycler, container, false)
        Log.d(tagLog,"onCreateView")
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log.d(tagLog,"onActivityCreated")


        /**
         * Handling Recycler from choreographs / skipping frame / freeze in Do in Background in the in of in background :D
         */

        val doInBackGroundMovie = 2000 //in just start
        Handler().postDelayed(object : Runnable {
            override fun run() {
                frame_progress.visibility = View.VISIBLE
                this.finish()

                val doInInBackGroundMovie = 100 //in just start
                Handler().postDelayed(object : Runnable {
                    override fun run() {
                        this.finish()
                        frame_progress.visibility = View.GONE //for other background process timing set image on adapter
                        Log.d(tagLog,"Loading ... for get and load data in background")

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

                fun getListMoviesDataAct(): java.util.ArrayList<MovieDataModelsRecycler> {
                    // for return
                    val arrayList = java.util.ArrayList<MovieDataModelsRecycler>()
                    // load object data
                    val dataMovies = funcLoadData()
                    // looping was object data
                    for (aMovieData in dataMovies) {
                        val movieDataModels = MovieDataModelsRecycler(
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
                            "" + aMovieData[20],
                            "" + aMovieData[21]
                        )
                        arrayList.add(movieDataModels)
                    }
                    return arrayList //return
                }

                /**
                 * End of From Object Class
                 */


                arrayList.addAll(getListMoviesDataAct())

                val movieRecyclerAdapter = context?.let {
                    MoviesHomeRecyclerAdapter(it)
                }
                movieRecyclerAdapter?.listCardMovieRecycler = arrayList
                recycler_view_home.adapter = movieRecyclerAdapter
            }
        }, doInBackGroundMovie.toLong())

        /**
         * End Of Handling Recycler from choreographs /
         */

    }

    override fun onStart() {
        super.onStart()
        Log.d(tagLog,"onStart")
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val pojoSubTab: MovieTabColorModel? = arguments?.getParcelable(""+key)
        pojoSubTab?.let {

            val color0 = "R.color.color0"
            val color1 = "R.color.color1"
            val color2 = "R.color.color2"
            val color3 = "R.color.color3"
            when {
                it.colorString == color0 -> view.setBackgroundColor(ResourcesCompat.getColor(resources, R.color.white, null))
                it.colorString == color1 -> view.setBackgroundColor(ResourcesCompat.getColor(resources, R.color.colorBarTabGreen, null))
                it.colorString == color2 -> view.setBackgroundColor(ResourcesCompat.getColor(resources, R.color.colorBarTabPink, null))
                it.colorString == color3 -> view.setBackgroundColor(ResourcesCompat.getColor(resources, R.color.colorBarTabPurple, null))
                else -> view.setBackgroundColor(ResourcesCompat.getColor(resources, R.color.white, null))
            }

        }

    }

    override fun onResume() {
        super.onResume()
        Log.d(tag,"onResume Tabs")
        recycler_view_home.setHasFixedSize(true)
        recycler_view_home.layoutManager = LinearLayoutManager(context)

    }

    override fun onPause() {
        super.onPause()
        Log.d(tagLog,"onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d(tagLog,"onStop")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d(tagLog,"onDestroyView")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(tag,"on Destroy")
    }

    override fun onDetach() {
        super.onDetach()
        Log.d(tagLog,"onDetach")
        arrayList.clear() //clear when out
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

                ""+ (context?.resources?.obtainTypedArray(R.array.data_movie_image)?.getResourceId(0,-1)),
                ""+ (context?.resources?.obtainTypedArray(R.array.data_movie_image_background)?.getResourceId(0,-1)),
                ""+ (context?.resources?.obtainTypedArray(R.array.data_movie_image_related1)?.getResourceId(0,-1)),
                ""+ (context?.resources?.obtainTypedArray(R.array.data_movie_image_related2)?.getResourceId(0,-1)),
                ""+ (context?.resources?.obtainTypedArray(R.array.data_movie_image_related3)?.getResourceId(0,-1)),

                ""+ (context?.resources?.getStringArray(R.array.data_movie_name)?.get(0)), //movieName

                ""+ (passingDataArrayResources(R.array.data_movie_release,0)), //movieRelease
                ""+ (passingDataArrayResources(R.array.data_movie_overview,0)),
                ""+ (passingDataArrayResources(R.array.data_movie_last_today,0)),
                ""+ (passingDataArrayResources(R.array.data_movie_last_week,0)),
                ""+ (passingDataArrayResources(R.array.data_movie_director1,0)),
                ""+ (passingDataArrayResources(R.array.data_movie_director2,0)),
                ""+ (passingDataArrayResources(R.array.data_movie_original_language,0)),
                ""+ (passingDataArrayResources(R.array.data_movie_runtime, 0)),
                ""+ (passingDataArrayResources(R.array.data_movie_budget,0)),
                ""+ (passingDataArrayResources(R.array.data_movie_revenue,0)),
                ""+ (passingDataArrayResources(R.array.data_movie_screen_play1,0)),
                ""+ (passingDataArrayResources(R.array.data_movie_screen_play2,0)),
                ""+ (passingDataArrayResources(R.array.data_movie_genres,0)),
                ""+ (passingDataArrayResources(R.array.data_movie_keywords,0)),
                ""+ (passingDataArrayResources(R.array.data_movie_score,0)),
                ""+ (passingDataArrayResources(R.array.data_movie_viewer,0))
            ),
            arrayOf(

                ""+ (context?.resources?.obtainTypedArray(R.array.data_movie_image)?.getResourceId(1,-1)),
                ""+ (context?.resources?.obtainTypedArray(R.array.data_movie_image_background)?.getResourceId(1,-1)),
                ""+ (context?.resources?.obtainTypedArray(R.array.data_movie_image_related1)?.getResourceId(1,-1)),
                ""+ (context?.resources?.obtainTypedArray(R.array.data_movie_image_related2)?.getResourceId(1,-1)),
                ""+ (context?.resources?.obtainTypedArray(R.array.data_movie_image_related3)?.getResourceId(1,-1)),

                ""+ (context?.resources?.getStringArray(R.array.data_movie_name)?.get(1)), //movieName

                ""+ (passingDataArrayResources(R.array.data_movie_release,1)), //movieRelease
                ""+ (passingDataArrayResources(R.array.data_movie_overview,1)),
                ""+ (passingDataArrayResources(R.array.data_movie_last_today,1)),
                ""+ (passingDataArrayResources(R.array.data_movie_last_week,1)),
                ""+ (passingDataArrayResources(R.array.data_movie_director1,1)),
                ""+ (passingDataArrayResources(R.array.data_movie_director2,1)),
                ""+ (passingDataArrayResources(R.array.data_movie_original_language,1)),
                ""+ (passingDataArrayResources(R.array.data_movie_runtime, 1)),
                ""+ (passingDataArrayResources(R.array.data_movie_budget,1)),
                ""+ (passingDataArrayResources(R.array.data_movie_revenue,1)),
                ""+ (passingDataArrayResources(R.array.data_movie_screen_play1,1)),
                ""+ (passingDataArrayResources(R.array.data_movie_screen_play2,1)),
                ""+ (passingDataArrayResources(R.array.data_movie_genres,1)),
                ""+ (passingDataArrayResources(R.array.data_movie_keywords,1)),
                ""+ (passingDataArrayResources(R.array.data_movie_score,1)),
                ""+ (passingDataArrayResources(R.array.data_movie_viewer,1))
            ),
            arrayOf(

                ""+ (context?.resources?.obtainTypedArray(R.array.data_movie_image)?.getResourceId(2,-1)),
                ""+ (context?.resources?.obtainTypedArray(R.array.data_movie_image_background)?.getResourceId(2,-1)),
                ""+ (context?.resources?.obtainTypedArray(R.array.data_movie_image_related1)?.getResourceId(2,-1)),
                ""+ (context?.resources?.obtainTypedArray(R.array.data_movie_image_related2)?.getResourceId(2,-1)),
                ""+ (context?.resources?.obtainTypedArray(R.array.data_movie_image_related3)?.getResourceId(2,-1)),

                ""+ (context?.resources?.getStringArray(R.array.data_movie_name)?.get(2)), //movieName

                ""+ (passingDataArrayResources(R.array.data_movie_release,2)), //movieRelease
                ""+ (passingDataArrayResources(R.array.data_movie_overview,2)),
                ""+ (passingDataArrayResources(R.array.data_movie_last_today,2)),
                ""+ (passingDataArrayResources(R.array.data_movie_last_week,2)),
                ""+ (passingDataArrayResources(R.array.data_movie_director1,2)),
                ""+ (passingDataArrayResources(R.array.data_movie_director2,2)),
                ""+ (passingDataArrayResources(R.array.data_movie_original_language,2)),
                ""+ (passingDataArrayResources(R.array.data_movie_runtime, 2)),
                ""+ (passingDataArrayResources(R.array.data_movie_budget,2)),
                ""+ (passingDataArrayResources(R.array.data_movie_revenue,2)),
                ""+ (passingDataArrayResources(R.array.data_movie_screen_play1,2)),
                ""+ (passingDataArrayResources(R.array.data_movie_screen_play2,2)),
                ""+ (passingDataArrayResources(R.array.data_movie_genres,2)),
                ""+ (passingDataArrayResources(R.array.data_movie_keywords,2)),
                ""+ (passingDataArrayResources(R.array.data_movie_score,2)),
                ""+ (passingDataArrayResources(R.array.data_movie_viewer,2))
            ),
            arrayOf(

                ""+ (context?.resources?.obtainTypedArray(R.array.data_movie_image)?.getResourceId(3,-1)),
                ""+ (context?.resources?.obtainTypedArray(R.array.data_movie_image_background)?.getResourceId(3,-1)),
                ""+ (context?.resources?.obtainTypedArray(R.array.data_movie_image_related1)?.getResourceId(3,-1)),
                ""+ (context?.resources?.obtainTypedArray(R.array.data_movie_image_related2)?.getResourceId(3,-1)),
                ""+ (context?.resources?.obtainTypedArray(R.array.data_movie_image_related3)?.getResourceId(3,-1)),

                ""+ (context?.resources?.getStringArray(R.array.data_movie_name)?.get(3)), //movieName

                ""+ (passingDataArrayResources(R.array.data_movie_release,3)), //movieRelease
                ""+ (passingDataArrayResources(R.array.data_movie_overview,3)),
                ""+ (passingDataArrayResources(R.array.data_movie_last_today,3)),
                ""+ (passingDataArrayResources(R.array.data_movie_last_week,3)),
                ""+ (passingDataArrayResources(R.array.data_movie_director1,3)),
                ""+ (passingDataArrayResources(R.array.data_movie_director2,3)),
                ""+ (passingDataArrayResources(R.array.data_movie_original_language,3)),
                ""+ (passingDataArrayResources(R.array.data_movie_runtime, 3)),
                ""+ (passingDataArrayResources(R.array.data_movie_budget,3)),
                ""+ (passingDataArrayResources(R.array.data_movie_revenue,3)),
                ""+ (passingDataArrayResources(R.array.data_movie_screen_play1,3)),
                ""+ (passingDataArrayResources(R.array.data_movie_screen_play2,3)),
                ""+ (passingDataArrayResources(R.array.data_movie_genres,3)),
                ""+ (passingDataArrayResources(R.array.data_movie_keywords,3)),
                ""+ (passingDataArrayResources(R.array.data_movie_score,3)),
                ""+ (passingDataArrayResources(R.array.data_movie_viewer,3))
            ),
            arrayOf(

                ""+ (context?.resources?.obtainTypedArray(R.array.data_movie_image)?.getResourceId(4,-1)),
                ""+ (context?.resources?.obtainTypedArray(R.array.data_movie_image_background)?.getResourceId(4,-1)),
                ""+ (context?.resources?.obtainTypedArray(R.array.data_movie_image_related1)?.getResourceId(4,-1)),
                ""+ (context?.resources?.obtainTypedArray(R.array.data_movie_image_related2)?.getResourceId(4,-1)),
                ""+ (context?.resources?.obtainTypedArray(R.array.data_movie_image_related3)?.getResourceId(4,-1)),

                ""+ (context?.resources?.getStringArray(R.array.data_movie_name)?.get(4)), //movieName

                ""+ (passingDataArrayResources(R.array.data_movie_release,4)), //movieRelease
                ""+ (passingDataArrayResources(R.array.data_movie_overview,4)),
                ""+ (passingDataArrayResources(R.array.data_movie_last_today,4)),
                ""+ (passingDataArrayResources(R.array.data_movie_last_week,4)),
                ""+ (passingDataArrayResources(R.array.data_movie_director1,4)),
                ""+ (passingDataArrayResources(R.array.data_movie_director2,4)),
                ""+ (passingDataArrayResources(R.array.data_movie_original_language,4)),
                ""+ (passingDataArrayResources(R.array.data_movie_runtime, 4)),
                ""+ (passingDataArrayResources(R.array.data_movie_budget,4)),
                ""+ (passingDataArrayResources(R.array.data_movie_revenue,4)),
                ""+ (passingDataArrayResources(R.array.data_movie_screen_play1,4)),
                ""+ (passingDataArrayResources(R.array.data_movie_screen_play2,4)),
                ""+ (passingDataArrayResources(R.array.data_movie_genres,4)),
                ""+ (passingDataArrayResources(R.array.data_movie_keywords,4)),
                ""+ (passingDataArrayResources(R.array.data_movie_score,4)),
                ""+ (passingDataArrayResources(R.array.data_movie_viewer,4))
            ),
            arrayOf(

                ""+ (context?.resources?.obtainTypedArray(R.array.data_movie_image)?.getResourceId(5,-1)),
                ""+ (context?.resources?.obtainTypedArray(R.array.data_movie_image_background)?.getResourceId(5,-1)),
                ""+ (context?.resources?.obtainTypedArray(R.array.data_movie_image_related1)?.getResourceId(5,-1)),
                ""+ (context?.resources?.obtainTypedArray(R.array.data_movie_image_related2)?.getResourceId(5,-1)),
                ""+ (context?.resources?.obtainTypedArray(R.array.data_movie_image_related3)?.getResourceId(5,-1)),

                ""+ (context?.resources?.getStringArray(R.array.data_movie_name)?.get(5)), //movieName

                ""+ (passingDataArrayResources(R.array.data_movie_release,5)), //movieRelease
                ""+ (passingDataArrayResources(R.array.data_movie_overview,5)),
                ""+ (passingDataArrayResources(R.array.data_movie_last_today,5)),
                ""+ (passingDataArrayResources(R.array.data_movie_last_week,5)),
                ""+ (passingDataArrayResources(R.array.data_movie_director1,5)),
                ""+ (passingDataArrayResources(R.array.data_movie_director2,5)),
                ""+ (passingDataArrayResources(R.array.data_movie_original_language,5)),
                ""+ (passingDataArrayResources(R.array.data_movie_runtime, 5)),
                ""+ (passingDataArrayResources(R.array.data_movie_budget,5)),
                ""+ (passingDataArrayResources(R.array.data_movie_revenue,5)),
                ""+ (passingDataArrayResources(R.array.data_movie_screen_play1,5)),
                ""+ (passingDataArrayResources(R.array.data_movie_screen_play2,5)),
                ""+ (passingDataArrayResources(R.array.data_movie_genres,5)),
                ""+ (passingDataArrayResources(R.array.data_movie_keywords,5)),
                ""+ (passingDataArrayResources(R.array.data_movie_score,5)),
                ""+ (passingDataArrayResources(R.array.data_movie_viewer,5))
            ),
            arrayOf(

                ""+ (context?.resources?.obtainTypedArray(R.array.data_movie_image)?.getResourceId(6,-1)),
                ""+ (context?.resources?.obtainTypedArray(R.array.data_movie_image_background)?.getResourceId(6,-1)),
                ""+ (context?.resources?.obtainTypedArray(R.array.data_movie_image_related1)?.getResourceId(6,-1)),
                ""+ (context?.resources?.obtainTypedArray(R.array.data_movie_image_related2)?.getResourceId(6,-1)),
                ""+ (context?.resources?.obtainTypedArray(R.array.data_movie_image_related3)?.getResourceId(6,-1)),

                ""+ (context?.resources?.getStringArray(R.array.data_movie_name)?.get(6)), //movieName

                ""+ (passingDataArrayResources(R.array.data_movie_release,6)), //movieRelease
                ""+ (passingDataArrayResources(R.array.data_movie_overview,6)),
                ""+ (passingDataArrayResources(R.array.data_movie_last_today,6)),
                ""+ (passingDataArrayResources(R.array.data_movie_last_week,6)),
                ""+ (passingDataArrayResources(R.array.data_movie_director1,6)),
                ""+ (passingDataArrayResources(R.array.data_movie_director2,6)),
                ""+ (passingDataArrayResources(R.array.data_movie_original_language,6)),
                ""+ (passingDataArrayResources(R.array.data_movie_runtime, 6)),
                ""+ (passingDataArrayResources(R.array.data_movie_budget,6)),
                ""+ (passingDataArrayResources(R.array.data_movie_revenue,6)),
                ""+ (passingDataArrayResources(R.array.data_movie_screen_play1,6)),
                ""+ (passingDataArrayResources(R.array.data_movie_screen_play2,6)),
                ""+ (passingDataArrayResources(R.array.data_movie_genres,6)),
                ""+ (passingDataArrayResources(R.array.data_movie_keywords,6)),
                ""+ (passingDataArrayResources(R.array.data_movie_score,6)),
                ""+ (passingDataArrayResources(R.array.data_movie_viewer,6))
            ),
            arrayOf(

                ""+ (context?.resources?.obtainTypedArray(R.array.data_movie_image)?.getResourceId(7,-1)),
                ""+ (context?.resources?.obtainTypedArray(R.array.data_movie_image_background)?.getResourceId(7,-1)),
                ""+ (context?.resources?.obtainTypedArray(R.array.data_movie_image_related1)?.getResourceId(7,-1)),
                ""+ (context?.resources?.obtainTypedArray(R.array.data_movie_image_related2)?.getResourceId(7,-1)),
                ""+ (context?.resources?.obtainTypedArray(R.array.data_movie_image_related3)?.getResourceId(7,-1)),

                ""+ (context?.resources?.getStringArray(R.array.data_movie_name)?.get(7)), //movieName

                ""+ (passingDataArrayResources(R.array.data_movie_release,7)), //movieRelease
                ""+ (passingDataArrayResources(R.array.data_movie_overview,7)),
                ""+ (passingDataArrayResources(R.array.data_movie_last_today,7)),
                ""+ (passingDataArrayResources(R.array.data_movie_last_week,7)),
                ""+ (passingDataArrayResources(R.array.data_movie_director1,7)),
                ""+ (passingDataArrayResources(R.array.data_movie_director2,7)),
                ""+ (passingDataArrayResources(R.array.data_movie_original_language,7)),
                ""+ (passingDataArrayResources(R.array.data_movie_runtime, 7)),
                ""+ (passingDataArrayResources(R.array.data_movie_budget,7)),
                ""+ (passingDataArrayResources(R.array.data_movie_revenue,7)),
                ""+ (passingDataArrayResources(R.array.data_movie_screen_play1,7)),
                ""+ (passingDataArrayResources(R.array.data_movie_screen_play2,7)),
                ""+ (passingDataArrayResources(R.array.data_movie_genres,7)),
                ""+ (passingDataArrayResources(R.array.data_movie_keywords,7)),
                ""+ (passingDataArrayResources(R.array.data_movie_score,7)),
                ""+ (passingDataArrayResources(R.array.data_movie_viewer,7))
            ),
            arrayOf(

                ""+ (context?.resources?.obtainTypedArray(R.array.data_movie_image)?.getResourceId(8,-1)),
                ""+ (context?.resources?.obtainTypedArray(R.array.data_movie_image_background)?.getResourceId(8,-1)),
                ""+ (context?.resources?.obtainTypedArray(R.array.data_movie_image_related1)?.getResourceId(8,-1)),
                ""+ (context?.resources?.obtainTypedArray(R.array.data_movie_image_related2)?.getResourceId(8,-1)),
                ""+ (context?.resources?.obtainTypedArray(R.array.data_movie_image_related3)?.getResourceId(8,-1)),

                ""+ (context?.resources?.getStringArray(R.array.data_movie_name)?.get(8)), //movieName

                ""+ (passingDataArrayResources(R.array.data_movie_release,8)), //movieRelease
                ""+ (passingDataArrayResources(R.array.data_movie_overview,8)),
                ""+ (passingDataArrayResources(R.array.data_movie_last_today,8)),
                ""+ (passingDataArrayResources(R.array.data_movie_last_week,8)),
                ""+ (passingDataArrayResources(R.array.data_movie_director1,8)),
                ""+ (passingDataArrayResources(R.array.data_movie_director2,8)),
                ""+ (passingDataArrayResources(R.array.data_movie_original_language,8)),
                ""+ (passingDataArrayResources(R.array.data_movie_runtime, 8)),
                ""+ (passingDataArrayResources(R.array.data_movie_budget,8)),
                ""+ (passingDataArrayResources(R.array.data_movie_revenue,8)),
                ""+ (passingDataArrayResources(R.array.data_movie_screen_play1,8)),
                ""+ (passingDataArrayResources(R.array.data_movie_screen_play2,8)),
                ""+ (passingDataArrayResources(R.array.data_movie_genres,8)),
                ""+ (passingDataArrayResources(R.array.data_movie_keywords,8)),
                ""+ (passingDataArrayResources(R.array.data_movie_score,8)),
                ""+ (passingDataArrayResources(R.array.data_movie_viewer,8))
            ),
            arrayOf(

                ""+ (context?.resources?.obtainTypedArray(R.array.data_movie_image)?.getResourceId(9,-1)),
                ""+ (context?.resources?.obtainTypedArray(R.array.data_movie_image_background)?.getResourceId(9,-1)),
                ""+ (context?.resources?.obtainTypedArray(R.array.data_movie_image_related1)?.getResourceId(9,-1)),
                ""+ (context?.resources?.obtainTypedArray(R.array.data_movie_image_related2)?.getResourceId(9,-1)),
                ""+ (context?.resources?.obtainTypedArray(R.array.data_movie_image_related3)?.getResourceId(9,-1)),

                ""+ (context?.resources?.getStringArray(R.array.data_movie_name)?.get(9)), //movieName

                ""+ (passingDataArrayResources(R.array.data_movie_release,9)), //movieRelease
                ""+ (passingDataArrayResources(R.array.data_movie_overview,9)),
                ""+ (passingDataArrayResources(R.array.data_movie_last_today,9)),
                ""+ (passingDataArrayResources(R.array.data_movie_last_week,9)),
                ""+ (passingDataArrayResources(R.array.data_movie_director1,9)),
                ""+ (passingDataArrayResources(R.array.data_movie_director2,9)),
                ""+ (passingDataArrayResources(R.array.data_movie_original_language,9)),
                ""+ (passingDataArrayResources(R.array.data_movie_runtime, 9)),
                ""+ (passingDataArrayResources(R.array.data_movie_budget,9)),
                ""+ (passingDataArrayResources(R.array.data_movie_revenue,9)),
                ""+ (passingDataArrayResources(R.array.data_movie_screen_play1,9)),
                ""+ (passingDataArrayResources(R.array.data_movie_screen_play2,9)),
                ""+ (passingDataArrayResources(R.array.data_movie_genres,9)),
                ""+ (passingDataArrayResources(R.array.data_movie_keywords,9)),
                ""+ (passingDataArrayResources(R.array.data_movie_score,9)),
                ""+ (passingDataArrayResources(R.array.data_movie_viewer,9))
            )
        )
*/
        return arrayOf()
    }

    /**
     * END OF This MovieDataObject.kt Object Array
     */

}