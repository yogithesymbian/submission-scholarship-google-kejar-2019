/*
 * Copyright (c) 2019. SCODEID
 */
/**
 * @author
 * Yogi Arif Widodo
 * www.dicoding.com/users/297307
 * www.github.com/yogithesymbian
 * For Google Kejar 2019
 * init branch submission 5
 */
package com.scodeid.scholarshipexpertscodeidev2019.homeInitView

import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.provider.Settings
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.GravityCompat
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.google.android.material.tabs.TabLayout
import com.scodeid.scholarshipexpertscodeidev2019.R
import com.scodeid.scholarshipexpertscodeidev2019.adapter.MainSectionsPagerAdapter
import com.scodeid.scholarshipexpertscodeidev2019.homeFavorite.MainFavoriteMovieActivity
import com.scodeid.scholarshipexpertscodeidev2019.homeFavorite.MainFavoriteTvActivity
import com.scodeid.scholarshipexpertscodeidev2019.homeFirstView.MoviesTvWapiHomeFragment
import com.scodeid.scholarshipexpertscodeidev2019.homeFirstView.MoviesWapiHomeFragment
import com.scodeid.scholarshipexpertscodeidev2019.notification.ComingSoonActivity
import com.scodeid.scholarshipexpertscodeidev2019.utils.MOVIE
import com.scodeid.scholarshipexpertscodeidev2019.utils.TV_SHOW
import kotlinx.android.synthetic.main.activity_movie_catalogue_main.*
import kotlinx.android.synthetic.main.activity_movie_catalogue_main_bar.*
import kotlinx.android.synthetic.main.activity_movie_catalogue_main_content.*
import kotlinx.android.synthetic.main.nav_header_home_movies.*

class MovieCatalogueMainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    companion object {
        var stateChangeVisible = ""
        var statusActivity = ""
        private val TAG_LOG: String = MovieCatalogueMainActivity::class.java.simpleName
    }


    /**
     * BOTTOM NAVIGATION
     */
    var clicked: Boolean = false
    private lateinit var mMainSectionsPagerAdapter: MainSectionsPagerAdapter
    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                Log.d(TAG_LOG, "Try opening home movie activity")
                // set activity
                statusActivity = MOVIE
                // visibility of home
                Handler().postDelayed(object : Runnable {
                    override fun run() {
                        frame_ui_change_progress.visibility = View.VISIBLE
                        this.finish()
                        frame_ui_change_progress.visibility = View.GONE
                    }

                    private fun finish() {
                        hideHomeTvShow()
                    }
                }, 100)
                // refresh menu
                invalidateOptionsMenu()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_tv_show -> {
                Log.d(TAG_LOG, "Try opening 2 movie activity")
                statusActivity = TV_SHOW
                Handler().postDelayed(object : Runnable {
                    override fun run() {
                        frame_ui_change_progress.visibility = View.VISIBLE
                        this.finish()
                        frame_ui_change_progress.visibility = View.GONE
                    }

                    private fun finish() {
                        hideHome()
                    }
                }, 100)
                handleFragmentApiTvShow()
                invalidateOptionsMenu()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications -> {
                Log.d(TAG_LOG, "Try opening 3 movie activity")
                startActivity(Intent(this@MovieCatalogueMainActivity, ComingSoonActivity::class.java))
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_favorite -> {
                Handler().postDelayed(object : Runnable {
                    override fun run() {
                        this.finish()
                    }

                    private fun finish() {
                        if (clicked) {
                            hideActionFavorite(applicationContext)
                            clicked = false
                        } else {
                            if (!clicked) {
                                showActionFavorite(applicationContext)
                                clicked = true
                            }
                        }
                    }
                }, 100)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }
    /**
     * END OF BOTTOM NAVIGATION
     */

    /**
     * FRAGMENT API TV SHOW
     */
    private fun handleFragmentApiTvShow() {
        // instance fragmentManager
        val mFragmentManager = supportFragmentManager
        // fragment transaction to operate add(), replace(), commit() , etc
        val mFragmentTransaction = mFragmentManager.beginTransaction()
        // create object fragment
        val mTvHome = MoviesTvWapiHomeFragment()

        val fragment = mFragmentManager.findFragmentByTag(MoviesTvWapiHomeFragment::class.java.simpleName)
        if (fragment !is MoviesTvWapiHomeFragment) {
            // add()
            mFragmentTransaction.add(
                R.id.frame_container_tv_show,
                mTvHome,
                MoviesTvWapiHomeFragment::class.java.simpleName
            )

            mFragmentTransaction.commit()
        }
    }
    /**
     * END OF FRAGMENT API TV SHOW
     */


    /**
     * Listener Drawer Nav
     */
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_language -> {
                startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
            }
        }
        // close the drawer with anim start
        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    /**
     * END OF LISTENER DRAWER NAV
     */

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(
            stateChangeVisible,
            statusActivity
        )
    }

    /**
     * onCreate
     */
    @Suppress("DEPRECATION") //getColor
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_catalogue_main)

        setSupportActionBar(toolbarManual)


//        moviesApiAdapter = MoviesApiAdapter(this, arrayListMovie)

        if (savedInstanceState != null) {
            val onChangeVisible = savedInstanceState.getString(stateChangeVisible)
            if (onChangeVisible != null) // NPE ? no , i think this good for avoid annotation
            {
                statusActivity = onChangeVisible
            }
        }

        /**
         *  NAVIGATION BOTTOM LISTENER
         */
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        // make implement work
        val navigationView = findViewById<NavigationView>(R.id.navigationView)
        navigationView.setNavigationItemSelectedListener(this)
        /**
         *  END OF NAVIGATION BOTTOM LISTENER
         */

        /**
         * TabLayout View Pager
         */
        mMainSectionsPagerAdapter = MainSectionsPagerAdapter(
            supportFragmentManager,
            tabs
        )
        /**
         * END OF TabLayout View Pager
         */

        // Set up the ViewPager with the sections adapter.
        view_pager_container_home.adapter = mMainSectionsPagerAdapter

        /**
         * listener view pager home
         */
        view_pager_container_home.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
                // later's
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                // later's
            }

            @SuppressLint("Recycle", "ResourceType")
            @TargetApi(Build.VERSION_CODES.LOLLIPOP) //lollipop for windowStatusBarColor :D
            override fun onPageSelected(position: Int) {
                // avoiding double bang operator
                val home = view_pager_container_home.adapter?.count?.minus(4)
                val popular = view_pager_container_home.adapter?.count?.minus(3)
                val trailer = view_pager_container_home.adapter?.count?.minus(2)
                val saved = view_pager_container_home.adapter?.count?.minus(1)

                when (position) {
                    home -> {
                        Log.d(TAG_LOG, "Home Tab Got Clicked")

                        app_bar_for_drawer.setBackgroundColor(
                            ResourcesCompat.getColor(
                                resources,
                                R.color.colorPrimary,
                                theme
                            )
                        )
                        app_bar_for_drawer.invalidate()
                        tabs.setBackgroundColor(
                            ResourcesCompat.getColor(
                                resources,
                                R.color.colorPrimary, theme
                            )
                        )
                        tabs.invalidate()

                        window.statusBarColor = (ResourcesCompat.getColor(
                            resources,
                            R.color.colorPrimary, theme
                        ))
                        linear_nav_header.setBackgroundColor(
                            ResourcesCompat.getColor(
                                resources,
                                R.color.colorPrimary,
                                theme
                            )
                        )
                        linear_nav_header.invalidate()
                        toolbarManual.setLogo(R.drawable.ic_airplay_blue_24dp)
                        toolbarManual.invalidate() // ctrl + click to see what is the invalidate
                        image_option_drawer.borderColor =
                            (ResourcesCompat.getColor(
                                resources,
                                R.color.colorPrimary, theme
                            ))
                        image_option_drawer.invalidate()

                        val tabs = tabs.getTabAt(home - 6)
                        tabs?.select()
                    }
                    popular -> {
                        Log.d(TAG_LOG, "Popular Tab Got Clicked")
                        /**
                         * animation change color bar layout
                         */
                        changeColorBarMovie(
                            R.color.colorBarTabGreen,
                            R.color.colorBarTabGreen
                        )
                        /**
                         * END of animation bar layout UI/UX Designer
                         */

                        toolbarManual.setLogo(R.drawable.ic_airplay_green_24dp)

                        val tabs = tabs.getTabAt(popular - 6)
                        tabs?.select()
                    }
                    trailer -> {
                        Log.d(TAG_LOG, "Trailer Tab Got Clicked")

                        /**
                         * animation change color bar layout
                         */
                        changeColorBarMovie(
                            R.color.colorBarTabGreen,
                            R.color.colorBarTabPink
                        )
                        /**
                         * END of animation bar layout UI/UX Designer
                         */

                        toolbarManual.setLogo(R.drawable.ic_airplay_pink_24dp)

                        val tabs = tabs.getTabAt(trailer - 6)
                        tabs?.select()
                    }
                    saved -> {
                        Log.d(TAG_LOG, "Saved Tab Got Clicked")

                        /**
                         * animation change color bar layout
                         */
                        changeColorBarMovie(
                            R.color.colorBarTabPink,
                            R.color.colorBarTabPurple
                        )
                        /**
                         * END of animation bar layout UI/UX Designer
                         */

                        toolbarManual.setLogo(R.drawable.ic_airplay_purple_24dp)

                        val tabs = tabs.getTabAt(saved - 6)
                        tabs?.select()
                    }

                }
            }
        })

        /**
         * End Of listener view pager home
         */

        /**
         * indicator select view tabLayout
         */
        view_pager_container_home.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabs))
        tabs.addOnTabSelectedListener(TabLayout.ViewPagerOnTabSelectedListener(view_pager_container_home))
        tabs.setupWithViewPager(ViewPager(this@MovieCatalogueMainActivity))

        // logic visible / gone for coloring by me , soon i create :D
        view_pager_container_home.visibility = View.VISIBLE
        Glide.with(this)
            .asBitmap()
            .load(R.drawable.ic_profile_home_user)
            .apply(
                RequestOptions()
                    .override(36, 36)
            )
            .into(image_option_drawer)

        image_option_drawer.setOnClickListener {
            drawer_layout.openDrawer(GravityCompat.START)
        }

        /**
         * End Of TabLayout Programmatically
         */

        /**
         * LISTENER FAVORITE BOTTOM SLIDE IN OUT2
         */
        fab_fav_movie.setOnClickListener {
            val intent = Intent(this@MovieCatalogueMainActivity, MainFavoriteMovieActivity::class.java)
            startActivity(intent)
        }
        fab_fav_tv.setOnClickListener {
            val intent = Intent(this@MovieCatalogueMainActivity, MainFavoriteTvActivity::class.java)
            startActivity(intent)
        }
        /**
         * END OF LISTENER FAVORITE
         */

    }

    /**
     * End Of OnCreate
     */

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun changeColorBarMovie(colorFromColor: Int, colorToColor: Int) {

        val colorFrom = ResourcesCompat.getColor(resources, colorFromColor, theme)
        val colorTo = ResourcesCompat.getColor(resources, colorToColor, theme)

        val colorAnimation = ValueAnimator.ofObject(ArgbEvaluator(), colorFrom, colorTo)
        colorAnimation.duration = 500 // milliseconds

        Handler().postDelayed(object : Runnable {
            override fun run() {
                this.finish()
            }

            private fun finish() {
                colorAnimation.addUpdateListener { animator ->
                    tabs?.setBackgroundColor(animator.animatedValue as Int)
                    tabs?.invalidate()// avoid NPE
                    app_bar_for_drawer?.setBackgroundColor(animator.animatedValue as Int)
                    app_bar_for_drawer?.invalidate()// avoid NPE
                    window?.statusBarColor = (animator.animatedValue as Int)
                    linear_nav_header?.setBackgroundColor(animator.animatedValue as Int)
                    linear_nav_header?.invalidate() // avoid NPE
                    image_option_drawer?.borderColor = (animator.animatedValue as Int)
                    image_option_drawer?.invalidate()// avoid NPE
                }
                colorAnimation.start()
            }
        }, 100) //10sec
    }

    /**
     * VISIBILITY VISIBLE && GONE
     */
    private fun hideHome() {
        view_pager_container_home.visibility = View.GONE
        frame_container_tv_show.visibility = View.VISIBLE
    }

    private fun hideHomeTvShow() {
        view_pager_container_home.visibility = View.VISIBLE
        frame_container_tv_show.visibility = View.GONE
    }

    private fun hideActionFavorite(applicationContext: Context) {
        fab_fav_movie.startAnimation(
            AnimationUtils.loadAnimation(
                applicationContext,
                R.anim.slide_out
            )
        )
        fab_fav_movie.visibility = View.GONE
        fab_fav_tv.startAnimation(
            AnimationUtils.loadAnimation(
                applicationContext,
                R.anim.slide_out
            )
        )
        fab_fav_tv.visibility = View.GONE
    }

    private fun showActionFavorite(applicationContext: Context) {
        fab_fav_movie.startAnimation(
            AnimationUtils.loadAnimation(
                applicationContext,
                R.anim.slide_in
            )
        )
        fab_fav_movie.visibility = View.VISIBLE
        fab_fav_tv.startAnimation(
            AnimationUtils.loadAnimation(
                applicationContext,
                R.anim.slide_in
            )
        )
        fab_fav_tv.visibility = View.VISIBLE
    }

    /**
     * END OF VISIBILITY VISIBLE && GONE
     */


    override fun onStart() {
        super.onStart()
        Log.d(TAG_LOG, "onStart")

        if (statusActivity == TV_SHOW) {
            Log.d(TAG_LOG, "Status activity $TV_SHOW")
            hideHome()
        } else if (statusActivity == MOVIE) {
            Log.d(TAG_LOG, "Status activity $MOVIE")
            hideHomeTvShow()
        }
    }

    override fun onResume() {
        super.onResume()
        Log.d(
            TAG_LOG,
            "onResume | i did'nt save barColor on instanceSaveState| and onResume will be set randomColor that is feature :D not bug or anymore :v "
        )

        view_pager_container_home.offscreenPageLimit = 3

        val tabs = tabs.getTabAt(4 - 6)
        tabs?.select()

        if (statusActivity == TV_SHOW) {
            navigation.selectedItemId = R.id.navigation_tv_show
        } else if (statusActivity == MOVIE) {
            navigation.selectedItemId = R.id.navigation_home
        }

    }

    override fun onPause() {
        super.onPause()
        /**
         * if need memory got killed and back to the onCreate
         * if user return to activity back to the onResume
         */
        Log.d(TAG_LOG, "onPause")
        val toggle: ActionBarDrawerToggle? = null
        toggle?.let { drawer_layout.removeDrawerListener(it) }
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
//
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.option_movies, menu)
        // search view with
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu?.findItem(R.id.option_search)?.actionView as SearchView
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))

        when (statusActivity) {
            MOVIE -> {
                Log.d(TAG_LOG, "search movie")
                searchView.queryHint = resources.getString(R.string.option_hint)

                searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

                    override fun onQueryTextSubmit(query: String): Boolean {
                        Log.d(TAG_LOG, "Search MOVIE onQueryTextSubmit")
                        Toast.makeText(this@MovieCatalogueMainActivity, query, Toast.LENGTH_SHORT)
                            .show()
                        Handler().postDelayed(object : Runnable {
                            override fun run() {
                                frame_ui_change_progress.visibility = View.VISIBLE
                                this.finish()
                                frame_ui_change_progress.visibility = View.GONE
                            }

                            private fun finish() {
                                MoviesWapiHomeFragment.movieViewModel.searchMovies(resources.getString(R.string.app_language), query, this@MovieCatalogueMainActivity)
                            }
                        }, 100)
                        return false
                    }

                    override fun onQueryTextChange(newText: String): Boolean {
                        Log.d(TAG_LOG, "Search MOVIE onQueryTextChange")
                        MoviesWapiHomeFragment.adapter.filter.filter(newText)
                        if (MoviesWapiHomeFragment.adapter.itemCount == 0){

                        }
                        return false
                    }
                })
            }
            TV_SHOW -> {
                Log.d(TAG_LOG, "search tv")
                searchView.queryHint = resources.getString(R.string.option_hint_tv)

                searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

                    override fun onQueryTextSubmit(query: String): Boolean {
                        Log.d(TAG_LOG, "Search TV_SHOW onQueryTextSubmit")
                        Toast.makeText(this@MovieCatalogueMainActivity, query, Toast.LENGTH_SHORT)
                            .show()
                        Handler().postDelayed(object : Runnable {
                            override fun run() {
                                frame_ui_change_progress.visibility = View.VISIBLE
                                this.finish()
                                frame_ui_change_progress.visibility = View.GONE
                            }

                            private fun finish() {
                                MoviesTvWapiHomeFragment.movieTvShowViewModel.searchTvShow(resources.getString(R.string.app_language), query, this@MovieCatalogueMainActivity)
                            }
                        }, 100)

                        return false
                    }

                    override fun onQueryTextChange(newText: String): Boolean {
                        Log.d(TAG_LOG, "Search TV_SHOW onQueryTextChange")
                        MoviesTvWapiHomeFragment.adapter.filter.filter(newText)
                        if (MoviesTvWapiHomeFragment.adapter.itemCount == 0){

                        }
                        return false
                    }
                })
            }
            else -> {
                Log.d(TAG_LOG, "search movie")
                searchView.queryHint = resources.getString(R.string.option_hint)

                searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

                    override fun onQueryTextSubmit(query: String): Boolean {
                        Log.d(TAG_LOG, "Search MOVIE onQueryTextSubmit")
                        Toast.makeText(this@MovieCatalogueMainActivity, query, Toast.LENGTH_SHORT)
                            .show()

                        Handler().postDelayed(object : Runnable {
                            override fun run() {
                                frame_ui_change_progress.visibility = View.VISIBLE
                                this.finish()
                                frame_ui_change_progress.visibility = View.GONE
                            }

                            private fun finish() {
                                MoviesWapiHomeFragment.movieViewModel.searchMovies(resources.getString(R.string.app_language), query, this@MovieCatalogueMainActivity)
                            }
                        }, 100)
                        return false
                    }

                    override fun onQueryTextChange(newText: String): Boolean {
                        Log.d(TAG_LOG, "Search MOVIE onQueryTextChange")
                        MoviesWapiHomeFragment.adapter.filter.filter(newText)
                        if (MoviesWapiHomeFragment.adapter.itemCount == 0){
                            Log.d(TAG_LOG,"item not found, try tap enter to search online")
                        }
                        return false
                    }
                })
            }
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.option_drawer -> {
                Log.d(TAG_LOG, "Option Drawer got clicked")
                drawer_layout.openDrawer(GravityCompat.START)
            }
        }
        return super.onOptionsItemSelected(item)
    }

}