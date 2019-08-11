/*
 * Copyright (c) 2019. SCODEID
 * v4 test
 */

package com.scodeid.scholarshipexpertscodeidev2019.dialogView


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.scodeid.scholarshipexpertscodeidev2019.R
import kotlinx.android.synthetic.main.fragment_movie_dialog.view.*

// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//private const val ARG_PARAM1 = "param1"
//private const val ARG_PARAM2 = "param2"

/**
 *
 * with try give same name folder cause xml layout just layout/file onCreating this fragment default
 *
 *
 * THIS CLASS NOT USED , LATER'S ...............
 */


class MovieDialogFragment : DialogFragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG_LOG,"onViewCreated")

        view.button_close.setOnClickListener{
            dialog?.cancel()
        }


    }

    companion object {
        val TAG_LOG: String = MovieDialogFragment::class.java.simpleName
    }



}