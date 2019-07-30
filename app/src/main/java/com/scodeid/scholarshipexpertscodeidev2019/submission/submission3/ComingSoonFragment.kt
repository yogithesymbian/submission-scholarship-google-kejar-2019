/*
 * Copyright (c) 2019. SCODEID
 */

package com.scodeid.scholarshipexpertscodeidev2019.submission.submission3


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import com.scodeid.scholarshipexpertscodeidev2019.R
import com.scodeid.scholarshipexpertscodeidev2019.submission.model.MovieTabColorModel

class ComingSoonFragment : Fragment() {

    companion object{
        val TAG_LOG: String = ComingSoonFragment::class.java.simpleName
        const val KEY = "keyPojoSubTab"
        fun newInstanceData(pojoSubTab: MovieTabColorModel) : MoviesWapiHomeFragment
        {
            val fragment = MoviesWapiHomeFragment()
            val args = Bundle()
            args.putParcelable(KEY, pojoSubTab)
            fragment.arguments = args
            return fragment
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_coming_soon, container, false)
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
}
