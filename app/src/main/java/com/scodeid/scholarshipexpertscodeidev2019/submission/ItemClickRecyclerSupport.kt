/*
 * Copyright (c) 2019. SCODEID
 */

package com.scodeid.scholarshipexpertscodeidev2019.submission

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.scodeid.scholarshipexpertscodeidev2019.R

/**
 * @author
 * Created by scode on 04,July,2019
 * Yogi Arif Widodo
 * www.dicoding.com/users/297307
 * www.github.com/yogithesymbian
 * SCODEID company,
 * Indonesia, East Borneo.
 * ==============================================================
 * Android Studio 3.3.2
 * Build # AI-182.5107.16.33.5314842, built on February 15, 2019
 * JRE: 1.8.0_152-release-1248-b01 amd64
 * JVM: OpenJDK 64-Bit Server VM by JetBrains s.r.o
 * Linux 4.19.0-kali5-amd64
 * ==============================================================
 *            _               _         _               ____
 *  ___ _   _| |__  _ __ ___ (_)___ ___(_) ___  _ __   |___ \
 * / __| | | | '_ \| '_ ` _ \| / __/ __| |/ _ \| '_ \    __) |
 * \__ \ |_| | |_) | | | | | | \__ \__ \ | (_) | | | |  / __/
 * |___/\__,_|_.__/|_| |_| |_|_|___/___/_|\___/|_| |_| |_____|
 *
 */

class ItemClickRecyclerSupport constructor(private var mRecyclerView: RecyclerView) {
    private lateinit var mOnItemClickListener: OnItemClickListener
    private lateinit var mOnItemLongClickListener: OnItemLongClickListener
    private val mOnClickListener = View.OnClickListener { v ->
        val holder = mRecyclerView.getChildViewHolder(v)
        mOnItemClickListener.onItemClicked(mRecyclerView, holder.adapterPosition, v)
    }
    private val mOnLongClickListener = View.OnLongClickListener { v ->
        val holder = mRecyclerView.getChildViewHolder(v)
        mOnItemLongClickListener.onItemLongClicked(mRecyclerView, holder.adapterPosition, v)
    }
    private val mAttachListener = object : RecyclerView.OnChildAttachStateChangeListener {
        override fun onChildViewAttachedToWindow(view: View) {
            view.setOnClickListener(mOnClickListener)
            view.setOnLongClickListener(mOnLongClickListener)
        }

        override fun onChildViewDetachedFromWindow(view: View) {}
    }

    init {
        mRecyclerView.setTag(R.id.item_click_support, this)
        mRecyclerView.addOnChildAttachStateChangeListener(mAttachListener)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        mOnItemClickListener = listener
    }

    fun setOnItemLongClickListener(listener: OnItemLongClickListener) {
        mOnItemLongClickListener = listener
    }

//    private fun detach(view: RecyclerView) {
//        view.removeOnChildAttachStateChangeListener(mAttachListener)
//        view.setTag(R.id.item_click_support, null)
//    }

    interface OnItemClickListener {
        fun onItemClicked(recyclerView: RecyclerView, position: Int, v: View)
    }

    interface OnItemLongClickListener {
        fun onItemLongClicked(recyclerView: RecyclerView, position: Int, v: View): Boolean
    }

    companion object {
        fun addTo(view: RecyclerView): ItemClickRecyclerSupport {
            var recyclerSupport: ItemClickRecyclerSupport? = view.getTag(R.id.item_click_support) as ItemClickRecyclerSupport?
            if (recyclerSupport == null) {
                recyclerSupport = ItemClickRecyclerSupport(view)
            }
            return recyclerSupport
        }
//        fun addToFrag(viewNav: NavigationView): ItemClickRecyclerSupport {
//            var support: ItemClickRecyclerSupport? = viewNav.getTag(R.id.item_click_support) as ItemClickRecyclerSupport?
//            if (support == null) {
//                support = ItemClickRecyclerSupport(viewNav)
//            }
//            return support
//        }

//        fun removeFrom(view: RecyclerView): CustomItemClickSupportK? {
//            val support = view.getTag(R.id.item_click_support) as CustomItemClickSupportK?
//            support?.detach(view)
//            return support
//        }
    }
}