/*
 * Copyright (c) 2019. SCODEID
 */

package com.scodeid.scholarshipexpertscodeidev2019.submission.submission3


import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import com.scodeid.scholarshipexpertscodeidev2019.R
import com.scodeid.scholarshipexpertscodeidev2019.submission.model.MovieTabColorModel

class ComingSoonFragment : Fragment() {

    companion object{
        @JvmStatic
        val TAG_LOG: String = ComingSoonFragment::class.java.simpleName
        @JvmStatic
        val KEY = "keyPojoSubTab"
        fun newInstanceData(pojoSubTab: MovieTabColorModel) : MoviesWapiHomeFragment
        {
            val fragment = MoviesWapiHomeFragment()
            val args = Bundle()
            args.putParcelable(KEY, pojoSubTab)
            fragment.arguments = args
            return fragment
        }
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.d(TAG_LOG,"onAttachFragment")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG_LOG,"onCreate")
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_coming_soon, container, false)
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log.d(TAG_LOG, "onActivityCreated")
    }
    override fun onStart() {
        super.onStart()
        Log.d(TAG_LOG,"onStart")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val pojoSubTab: MovieTabColorModel? = arguments?.getParcelable(""+ KEY)
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
    }
    override fun onPause() {
        super.onPause()
        Log.d(TAG_LOG,"onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG_LOG,"onStop")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d(TAG_LOG,"onDestroyView")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG_LOG,"on Destroy")
    }

    override fun onDetach() {
        super.onDetach()
        Log.d(TAG_LOG,"onDetach ")
    }


}
