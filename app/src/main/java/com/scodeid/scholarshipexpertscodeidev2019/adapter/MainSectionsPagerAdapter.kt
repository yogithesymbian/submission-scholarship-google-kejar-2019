/*
 * Copyright (c) 2019. SCODEID
 */

@file:Suppress("DEPRECATION") //later's will be update soon ya

package com.scodeid.scholarshipexpertscodeidev2019.adapter

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.google.android.material.tabs.TabLayout
import com.scodeid.scholarshipexpertscodeidev2019.model.MovieTabColorModel
import com.scodeid.scholarshipexpertscodeidev2019.notification.ComingSoonFragment
import com.scodeid.scholarshipexpertscodeidev2019.homeFirstView.MoviesWapiHomeFragment


// section for Home | popular | trailer | saved
class MainSectionsPagerAdapter(fm: FragmentManager, private val tabs: TabLayout) : FragmentPagerAdapter(fm) {
    companion object{
        val TAG_LOG: String = MainSectionsPagerAdapter::class.java.simpleName
    }
    override fun getItem(position: Int): Fragment {

        return when (position) {
            0 -> {
                Log.d(TAG_LOG,"set color view pager home and return to the fragment (later's) ")
                MoviesWapiHomeFragment.newInstanceData(MovieTabColorModel("R.color.color0"))
//                MoviesHomeRecyclerFragment.newInstanceData(MovieTabColorModel("R.color.color0"))
            }
            1 -> {
                Log.d(TAG_LOG,"set color view pager popular and return to the fragment (later's) ")
                ComingSoonFragment.newInstanceData(MovieTabColorModel("R.color.color1"))
//                MoviesHomeRecyclerFragment.newInstanceData(MovieTabColorModel("R.color.color1"))
            }
            2 -> {
                Log.d(TAG_LOG,"set color view pager trailer and return to the fragment (later's) ")
                ComingSoonFragment.newInstanceData(MovieTabColorModel("R.color.color2"))
//  /              MoviesHomeRecyclerFragment.newInstanceData(MovieTabColorModel("R.color.color2"))
            }
            3 -> {
                Log.d(TAG_LOG,"set color view pager saved and return to the fragment (later's) ")
                ComingSoonFragment.newInstanceData(MovieTabColorModel("R.color.color3"))
            }
            // this just for null , but i can't solved without double bang operator at this time just only for this
            else -> return ComingSoonFragment.newInstanceData(MovieTabColorModel("R.color1"))
        }
    }

    override fun getCount(): Int {
        // Show 3 total pages.
        return tabs.tabCount
    }

    override fun getPageTitle(position: Int): CharSequence? {
        when (position) {
            0 -> return "1 Home"
            1 -> return "2 Popular"
            2 -> return "3 Trailer"
            3 -> return "4 Saved"
        }
        return null
    }


}