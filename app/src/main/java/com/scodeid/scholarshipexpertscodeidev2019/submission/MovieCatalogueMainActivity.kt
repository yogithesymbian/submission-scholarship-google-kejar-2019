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
package com.scodeid.scholarshipexpertscodeidev2019.submission

import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.google.android.material.tabs.TabLayout
import com.scodeid.scholarshipexpertscodeidev2019.R
import com.scodeid.scholarshipexpertscodeidev2019.submission.model.MovieDataModels
import com.scodeid.scholarshipexpertscodeidev2019.submission.model.MovieDataModelsRecycler
import com.scodeid.scholarshipexpertscodeidev2019.submission.notification.ComingSoonActivity
import com.scodeid.scholarshipexpertscodeidev2019.submission.submission3.MoviesTvWapiHomeFragment
import com.scodeid.scholarshipexpertscodeidev2019.submission.view.MovieViewViewers
import kotlinx.android.synthetic.main.activity_movie_catalogue_main.*
import kotlinx.android.synthetic.main.activity_movie_catalogue_main_bar.*
import kotlinx.android.synthetic.main.activity_movie_catalogue_main_content.*
import kotlinx.android.synthetic.main.fragment_movies_home_recycler.*
import kotlinx.android.synthetic.main.nav_header_home_movies.*


/**
 * README THIS GUIDE SUBMISSION 1 , SUBMISSION 2 : NOTE FOR REVIEW and MySelf on FUTURE
 *   ____       _     _
 * / ___|_   _(_) __| | ___
 * | |  _| | | | |/ _` |/ _ \
 * | |_| | |_| | | (_| |  __/
 * \____|\__,_|_|\__,_|\___|
 * from FIGLet
 * Thank's for advance
 *====================================================================
 * Submission 1
 * Submission 2
 * ------------FOR SEARCH THE FUNCTION WHAT WAS | USING --------------
 * CTRL + F , then Type                      without Double Quota " "
 *                      "Submission 1 Func"
 *                              or
 *                      "Submission 2 Func"
 *                              or
 *                      "END OF Submission 1 Func"
 *                              or
 *                      "END OF Submission 1 Func"
 *
 * !i-was-comment-out-for-submission1
 * !just-for-submission-2-submit-on-class
 *===================================================================
 * Change to Submission 1 MODE
 * ==========================================================================
 *      1. CTRL + F , then type
 *          "// homeMovie()//this function for Submission 1 Func 1"
 *          and
 *          "// homeMovie()//this function for Submission 1 Func 2"
 *      2. Just Comment Out in , and that's will be look like these
 *           homeMovie()//this function for Submission 1 Func 1
 *          and
 *           homeMovie()//this function for Submission 1 Func 2
 *       3. at the line code of
 *                 setContentView(R.layout.activity_movie_catalogue_main)
 *          HOLD ctrl + click the activity_movie_catalogue_main
 *          then UNCOMMENT THE LIST VIEW component inside include <activity_content>
 *          then ->
 *          and change the
 *              ANDROID:VISIBLE="Gone'
 *                  to
 *                      ANDROID:VISIBLE="View"
 * ==============================================================================
 * Change to Submission 2 MODE
 *  ==========================================================================
 *      1. later's
 *      2. later's
 *      3. later's
 *      before i would duplicate and re-create but i don't have enough times for that's
 *      so i just comment out the func what i don't need on submission 1
 *      and
 *      using func for submission 2
 */

class MovieCatalogueMainActivity : AppCompatActivity(),
    MovieViewViewers, NavigationView.OnNavigationItemSelectedListener {

    /**
     * Presenter Like Unit Testing with MPV
     * implement on Submission ListView
     * for Viewers Toast
     * testing
     * Intent MoviesDataResult Activity
     */
    override fun allMovieData(models: MovieDataModels) {
        // coming soon :D ....
        Log.d(tagLog, "V-I-E-W (MPV) view unit for viewer is run ? ")
    }

    override fun homeMovies(models: MovieDataModelsRecycler) {
        Log.d(tagLog,"later's")
    }


    private val tagLog = "MovieHomeActivity"

    /**
     * Variable for Submission 2 this
     */
    private lateinit var mMainSectionsPagerAdapter: MainSectionsPagerAdapter
//    private lateinit var arrayDataModelMoviesRecycler: ArrayList<MovieDataModelsRecycler>

    /**
     * MovieAdapter Initialize Model for the string.xml
     *======================================================
     *
     */
    /**
     * Variable for Submission 1 this
     */

//    private lateinit var moviePicture: TypedArray
//    private lateinit var moviePictureBackground: TypedArray
//    private lateinit var moviePictureRel1: TypedArray
//    private lateinit var moviePictureRel2: TypedArray
//    private lateinit var moviePictureRel3: TypedArray
//
//    private lateinit var movieName: Array<String>
//    private var movieRelease: Array<String>? = null
//    private var movieOverview: Array<String>? = null
//
//    private var movieRankLastToday: Array<String>? = null
//    private var movieRankLastWeek: Array<String>? = null
//
//    private var movieDirector1: Array<String>? = null
//    private var movieDirector2: Array<String>? = null
//
//    private var movieOrigLang: Array<String>? = null
//    private var movieRunTime: Array<String>? = null
//    private var movieBudget: Array<String>? = null
//    private var movieRevenue: Array<String>? = null
//
//    private var movieScreenPlay1: Array<String>? = null
//    private var movieScreenPlay2: Array<String>? = null
//
//    private var movieGenres: Array<String>? = null
//    private var movieKeywords: Array<String>? = null
//
//    private var movieScore: Array<String>? = null
//    private var movieViewers: Array<String>? = null
//
//    private var adapterMovie: MovieAdapter? = null
//
//    private lateinit var dataModelMovies: ArrayList<MovieDataModels>


    /**
     * Listener BottomNavigationView
     *  -> fragment implement
     *  -> Toolbar Customize
     *  -> other feature's
     */

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                Log.d(tagLog, "Try opening home movie activity")
                hideHomeTvShow()
                // already by default tabs homeActivity
                // homeMovie()//this function for Submission 1 Func 1
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_tv_show -> {
                Log.d(tagLog, "Try opening 2 movie activity")
                hideHome()
                handleFragmentApiTvShow()

                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications -> {
                Log.d(tagLog, "Try opening 3 movie activity")
                startActivity(Intent(this@MovieCatalogueMainActivity, ComingSoonActivity::class.java))
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

//    private fun handleFragmentTvShow() {
//        // instance fragmentManager
//        val mFragmentManager = supportFragmentManager
//        // fragment transaction to operate add(), replace(), commit() , etc
//        val mFragmentTransaction = mFragmentManager.beginTransaction()
//        // create object fragment
//        val mTvHome = MoviesTvShowRecyclerFragment()
//
//        val fragment = mFragmentManager.findFragmentByTag(MoviesTvShowRecyclerFragment::class.java.simpleName)
//        if (fragment !is MoviesTvShowRecyclerFragment) {
//            // add()
//            mFragmentTransaction.add(R.id.frame_container_tv_show, mTvHome, MoviesTvShowRecyclerFragment::class.java.simpleName)
//            //commit()
//            mFragmentTransaction.commit()
//        }
//    }

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
            mFragmentTransaction.add(R.id.frame_container_tv_show, mTvHome, MoviesTvWapiHomeFragment::class.java.simpleName)
            //commit()
            mFragmentTransaction.commit()
        }
    }

    /**
     * Listener Drawer Nav
     */
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_home -> {
                Log.d(tagLog, "Home Drawer Clicked")
            }
            R.id.nav_gallery -> {
                Log.d(tagLog, "Gallery Drawer Clicked")
            }
            R.id.nav_language -> {
                startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
            }
        }
        // close the drawer with anim start
        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }
    /**
     * onCreate
     */
    @Suppress("DEPRECATION") //getColor
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_catalogue_main)
        setSupportActionBar(toolbarManual)

//        toolbarManual.overflowIcon?.setColorFilter(resources.getColor(R.color.black70), PorterDuff.Mode.SRC_ATOP)
//        toolbarManual.setNavigationOnClickListener {
//            drawer_layout.openDrawer(GravityCompat.START)
//        }
        // bottom navigation listener ON
//        val moviesHomeBotNavAdapter = MoviesHomeBotNavAdapter(this@MovieCatalogueMainActivity)
//        navigation.setOnNavigationItemSelectedListener(moviesHomeBotNavAdapter.mOnNavigationItemSelectedListener)
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        // make implement work
        val navigationView = findViewById<NavigationView>(R.id.navigationView)
        navigationView.setNavigationItemSelectedListener(this)

        /**
         * TabLayout View Pager Like ButtonNavigationView
         */

        mMainSectionsPagerAdapter = MainSectionsPagerAdapter(
            supportFragmentManager,
            tabs
        )

        // Set up the ViewPager with the sections adapter.
        view_pager_container_home.adapter = mMainSectionsPagerAdapter

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
                        Log.d(tagLog,"Home Tab Got Clicked")

                        app_bar_layout_out.setBackgroundColor(ResourcesCompat.getColor(resources, R.color.colorPrimary, theme))
                        app_bar_layout_out.invalidate()
                        app_bar_for_drawer.setBackgroundColor(ResourcesCompat.getColor(resources, R.color.colorPrimary, theme))
                        app_bar_for_drawer.invalidate()
                        tabs.setBackgroundColor(ResourcesCompat.getColor(resources, R.color.colorPrimary, theme))
                        tabs.invalidate()

                        window.statusBarColor = (ResourcesCompat.getColor(resources, R.color.colorPrimary, theme))
                        linear_nav_header.setBackgroundColor(ResourcesCompat.getColor(resources, R.color.colorPrimary, theme))
                        linear_nav_header.invalidate()
                        toolbarManual.setLogo(R.drawable.ic_airplay_blue_24dp)
                        toolbarManual.invalidate() // ctrl + click to see what is the invalidate
                        image_option_drawer.borderColor = (ResourcesCompat.getColor(resources, R.color.colorPrimary, theme))
                        image_option_drawer.invalidate()

                        val tabs = tabs.getTabAt(home - 6)
                        tabs?.select()
                    }
                    popular -> {
                        Log.d(tagLog,"Popular Tab Got Clicked")
/*
Testing
                        val moviePictures= resources.getStringArray(R.array.data_movie_name)
                        val moviePict= resources.obtainTypedArray(R.array.data_movie_image)
                        Log.d(tagLog,"movies name "+ (moviePictures[0]) + "and "+ (moviePictures[2]) )
                        Log.d(tagLog,"image : "+moviePict.getResourceId(0, -1) +" and " +moviePict.getResourceId(1, -1 ))
*/
                        /**
                         * animation change color bar layout
                         */
                        changeColorBarMovie(R.color.colorBarTabGreen, R.color.colorBarTabGreen)
                        /**
                         * END of animation bar layout UI/UX Designer
                         */

                        toolbarManual.setLogo(R.drawable.ic_airplay_green_24dp)

                        val tabs = tabs.getTabAt(popular - 6)
                        tabs?.select()
                    }
                    trailer -> {
                        Log.d(tagLog,"Trailer Tab Got Clicked")

                        /**
                         * animation change color bar layout
                         */
                        changeColorBarMovie(R.color.colorBarTabGreen, R.color.colorBarTabPink)
                        /**
                         * END of animation bar layout UI/UX Designer
                         */

                        toolbarManual.setLogo(R.drawable.ic_airplay_pink_24dp)

                        val tabs = tabs.getTabAt(trailer - 6)
                        tabs?.select()
                    }
                    saved -> {
                        Log.d(tagLog,"Saved Tab Got Clicked")

                        /**
                         * animation change color bar layout
                         */
                        changeColorBarMovie(R.color.colorBarTabPink, R.color.colorBarTabPurple)
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
            .apply(RequestOptions()
                .override(36, 36))
            .into(image_option_drawer)

        /**
         * README PLEASE ... Dunno why this code doesn't work i have set properly the xml layout like android
         * but in androidX arch i dunno why cant hide , then i was explore all documentation
         * only bottomAppBar has work , in this case i still exploring ,
         * coordinator with constraint is hard
         * in android library i was solved and work like a charm ( only with xml , not use programmatically to hide toolbar / tabLayout like Google Play Store
         * but in androidX i use xml not work
         * in androidX i use programmatically code to hide like these code (addOnScrollListener) doesn't work too
         */
        recycler_view_home?.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0 || dy<0)
                {
                    navigationView.visibility = View.GONE
                }
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE)
                {
                    navigationView.visibility = View.VISIBLE
                }
                super.onScrollStateChanged(recyclerView, newState)
            }
        })



        /**
         * End Of TabLayout Programmatically
         */

//        ViewCompat.setNestedScrollingEnabled(lv_list_movie_home, true)
//        https://bumptech.github.io/glide/doc/getting-started.html
//        val futureTarget = Glide.with(this)
//            .asBitmap()
//            .load("https://www.dicoding.com/images/small/avatar/201809161240165eca9bd5cbaeebed25cc7afcfbff45d5.jpg")
//            .submit(36, 36)
//
//        val bitmap = futureTarget.get()
//
//        // Do something with the Bitmap and then when you're done with it:
//        Glide.with(this).clear(futureTarget)
        // avoid skipped 34 frame of my code
        /**
         * for testing
         */
//        val doInBackGroundMovie = 2000 //in just start
//        Handler().postDelayed(object : Runnable {
//            override fun run() {
//                frame_progress.visibility = View.VISIBLE
//                this.finish()
////                Log.d(tagLog,"DONE ... for get and load data in background")
//            }
//
//            private fun finish() {
////                Log.d(tagLog,"Loading ... for get and load data in background")
//                frame_progress.visibility = View.GONE
//                //do here
//            }
//        }, doInBackGroundMovie.toLong())

//         homeMovie()//this function for Submission 1 Func 2
        //for my child


        /**
         * Listener Click RecyclerView MoviesList
         */

//        ItemClickRecyclerSupport.addTo(recycler_view_home).setOnItemClickListener(
//            object : ItemClickRecyclerSupport.OnItemClickListener{
//                override fun onItemClicked(recyclerView: RecyclerView, position: Int, v: View) {
//                    onClickMoviesRecycler(arrayDataModelMoviesRecycler[position])
//                }
//            })

        /**
         * END OF listener
         */

    }

//    private fun onClickMoviesRecycler(movieDataModelsRecycler: MovieDataModelsRecycler) {
//        Toast.makeText(this,"Try opening "+movieDataModelsRecycler.movieName, Toast.LENGTH_SHORT)
//            .show()
//    }a

    /**
     * End Of OnCreate
     */

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun changeColorBarMovie(colorFromColor: Int, colorToColor: Int) {

        val colorFrom = ResourcesCompat.getColor(resources, colorFromColor, theme)
        val colorTo = ResourcesCompat.getColor(resources, colorToColor, theme)

        val colorAnimation = ValueAnimator.ofObject(ArgbEvaluator(), colorFrom, colorTo)
        colorAnimation.duration = 500 // milliseconds
        colorAnimation.addUpdateListener { animator ->
            tabs?.setBackgroundColor(animator.animatedValue as Int)
            tabs?.invalidate()// avoid NPE
            app_bar_layout_out?.setBackgroundColor(animator.animatedValue as Int)
            app_bar_layout_out?.invalidate()// avoid NPE
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

    private fun hideHome()
    {
        view_pager_container_home.visibility = View.GONE
        frame_container_tv_show.visibility = View.VISIBLE
        tabs.visibility = View.GONE
    }
    private fun hideHomeTvShow()
    {
        view_pager_container_home.visibility = View.VISIBLE
        frame_container_tv_show.visibility = View.GONE
        tabs.visibility = View.VISIBLE
    }


    /**
     * LifeCycle Android
     * onCreate -> onStart -> onResum -> onPause -> onStop -> onRestart -> onDestroy
     */
    override fun onStart() {
        super.onStart()
        Log.d(tagLog, "onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d(tagLog, "onResume | i did'nt save barColor on instanceSaveState| and onResume will be set randomColor that is feature :D not bug or anymore :v ")

        // some bug maybe come by fragment fixed | relative small so i need limit the fragment loaded
        view_pager_container_home.offscreenPageLimit = 3


        //select home from other on destroy an activity
        val tabs = tabs.getTabAt(4 - 6)
        tabs?.select()
    }

    override fun onPause() {
        super.onPause()
        /**
         * if need memory got killed and back to the onCreate
         * if user return to activity back to the onResume
         */
        Log.d(tagLog, "onPause")
        val toggle: ActionBarDrawerToggle? = null
        toggle?.let { drawer_layout.removeDrawerListener(it) }
    }

    override fun onStop() {
        super.onStop()
        /**
         * if need navigate's to the activity
         * if user return to activity back to the onResume then run onStart
         */
        Log.d(tagLog, "onStop")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d(tagLog, "onRestart")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(tagLog, "Home has onDestroy")
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.option_movies, menu)
        // search view with
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu?.findItem(R.id.option_search)?.actionView as SearchView
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
//
//        val searchHint = searchView.findViewById<View>(R.id.option_search)
//        searchHint.setHint

        searchView.queryHint = resources.getString(R.string.option_hint)
//        searchView.queryHint = ResourcesCompat.getColor(resources, R.color.black70).toString()

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                Log.d(tagLog, "Search View onQueryTextSubmit")
                Toast.makeText(this@MovieCatalogueMainActivity, query, Toast.LENGTH_SHORT)
                    .show()
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                Log.d(tagLog, "Search View onQueryTextChange")
                return false
            }
        })

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.option_drawer -> {
                Log.d(tagLog, "Option Drawer got clicked")
                drawer_layout.openDrawer(GravityCompat.START)
            }
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        Log.d(tagLog, "onPrepareOptionMenu For Icon")
//        val settingsItem = menu?.findItem(R.id.option_drawer)
//        settingsItem?.icon = ContextCompat.getDrawable(this, R.drawable.ic_golf_course_black_24dp)
        return super.onPrepareOptionsMenu(menu)


    }

/*
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == REQUEST_CODE_VIEWER_MOVIE)
        {
            if (resultCode == MovieCatalogueDetailActivity.RESULT_CODE)
            {
                val tvViewer = data.getIntExtra(MovieCatalogueDetailActivity.EXTRA_VIEWER,0)
                // button for text_label_viewer
                button_icon_viewer.text = "$tvViewer Viewers"
            }
        }
    }
    */



    //==============================================================================
    /**
     * Submission 1 Func
     * this homeMovie function for submission 1 !READ THE GUIDE for knowing my CODE </>
     * Check This Header for GUIDE
     * Android Lint Pleasure Please don't delete me ! :D
     */

    /*
    private fun homeMovie() {
        Log.d(tagLog, "Home Movie Loading .... for set adapter ListView")
//        lv_list_movie_home_favorite.adapter = adapterMovie
//        delayAsync = DelayAsync()
//        delayAsync.execute()
        // timeOut
        adapterMovie = MovieAdapter(this)
        // avoid skipped 34 frame of my code
        val doInBackGroundMovie = 1000 //in just start
        Handler().postDelayed(object : Runnable {
            override fun run() {
//                frame_progress.visibility = View.VISIBLE
                this.finish()
                Log.d(tagLog, "DONE ... for get and load data in background")
            }

            private fun finish() {
                Log.d(tagLog, "Loading ... for get and load data in background")
                lv_list_movie_home.adapter = adapterMovie

                loadDataMovie()
                setMovieAdd()
                bindMovieHome()
//                frame_progress.visibility = View.GONE
            }
        }, doInBackGroundMovie.toLong())
    }

    fun bindMovieHome() {

        lv_list_movie_home.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->


            val imagePoster = dataModelMovies[position].moviePicture
            val imagePosterBack = dataModelMovies[position].moviePictureBackground
            val imagePosterRel1 = dataModelMovies[position].moviePictureRelated1
            val imagePosterRel2 = dataModelMovies[position].moviePictureRelated2
            val imagePosterRel3 = dataModelMovies[position].moviePictureRelated3
            val movieName = dataModelMovies[position].movieName

            Log.d(tagLog, "List View movie :  $movieName got clicked")

            // get PoJo data from onClickListener
            val dataModelMovie = MovieDataModels(
                +imagePoster,
                +imagePosterBack,
                +imagePosterRel1,
                +imagePosterRel2,
                +imagePosterRel3,
                "" + movieName,
                "" + dataModelMovies[position].movieRelease,
                "" + dataModelMovies[position].movieOverview,
                "" + dataModelMovies[position].movieRankLastToday,
                "" + dataModelMovies[position].movieRAnkLastWeek,
                "" + dataModelMovies[position].movieDirector1,
                "" + dataModelMovies[position].movieDirector2,
                "" + dataModelMovies[position].movieOrigLang,
                "" + dataModelMovies[position].movieRuntime,
                "" + dataModelMovies[position].movieBudget,
                "" + dataModelMovies[position].movieRevenue,
                "" + dataModelMovies[position].movieScreenPlay1,
                "" + dataModelMovies[position].movieScreenPlay2,
                "" + dataModelMovies[position].movieGenres,
                "" + dataModelMovies[position].movieKeywords,
                "" + dataModelMovies[position].movieScore,
                "" + dataModelMovies[position].movieViewers
            )
            // moveIntent with PoJo
            val intent = Intent(this@MovieCatalogueMainActivity, MovieCatalogueDetailActivity::class.java)
            intent.putExtra(MovieCatalogueDetailActivity.EXTRA_MOVIE_DATA, dataModelMovie)
//            startActivityForResult(intent, REQUEST_CODE_VIEWER_MOVIE)
            startActivity(intent)

        }
    }

    fun loadDataMovie() {

        Log.d(tagLog, "Load Data Movie from get string with array")

//        moviePicture = resources.obtainTypedArray(R.array.data_movie_image)
//        moviePictureBackground = resources.obtainTypedArray(R.array.data_movie_image_background)
//        moviePictureRel1 = resources.obtainTypedArray(R.array.data_movie_image_related1)
//        moviePictureRel2 = resources.obtainTypedArray(R.array.data_movie_image_related2)
//        moviePictureRel3 = resources.obtainTypedArray(R.array.data_movie_image_related3)
//
//        movieName = resources.getStringArray(R.array.data_movie_name)
//        movieRelease = resources.getStringArray(R.array.data_movie_release)
//        movieOverview = resources.getStringArray(R.array.data_movie_overview)
//
//        movieRankLastToday = resources.getStringArray(R.array.data_movie_last_today)
//        movieRankLastWeek = resources.getStringArray(R.array.data_movie_last_week)
//
//        movieDirector1 = resources.getStringArray(R.array.data_movie_director1)
//        movieDirector2 = resources.getStringArray(R.array.data_movie_director2)
//
//        movieOrigLang = resources.getStringArray(R.array.data_movie_original_language)
//        movieRunTime = resources.getStringArray(R.array.data_movie_runtime)
//        movieBudget = resources.getStringArray(R.array.data_movie_budget)
//        movieRevenue = resources.getStringArray(R.array.data_movie_revenue)
//
//        movieScreenPlay1 = resources.getStringArray(R.array.data_movie_screen_play1)
//        movieScreenPlay2 = resources.getStringArray(R.array.data_movie_screen_play2)
//
//        movieGenres = resources.getStringArray(R.array.data_movie_genres)
//        movieKeywords = resources.getStringArray(R.array.data_movie_keywords)
//        movieScore = resources.getStringArray(R.array.data_movie_score)
//        movieViewers = resources.getStringArray(R.array.data_movie_viewer)

        Log.d(tagLog, "Load Data Movie have done ")

    }

    fun setMovieAdd() {

        Log.d(tagLog, "Set data string")
        dataModelMovies = ArrayList()
        for (i in movieName.indices) {

            val moviePic = moviePicture.getResourceId(i, -1)
            val moviePicBack = moviePictureBackground.getResourceId(i, -1)
            val moviePicRel1 = moviePictureRel1.getResourceId(i, -1)
            val moviePicRel2 = moviePictureRel2.getResourceId(i, -1)
            val moviePicRel3 = moviePictureRel3.getResourceId(i, -1)
            val movieName = movieName[i]
            val movieRelease = movieRelease?.get(i)
            val movieOverView = movieOverview?.get(i)
            val movieLastToday = movieRankLastToday?.get(i)
            val movieLastWeek = movieRankLastWeek?.get(i)
            val movieDir1 = movieDirector1?.get(i)
            val movieDir2 = movieDirector2?.get(i)
            val movieOriLang = movieOrigLang?.get(i)
            val movieRunTime = movieRunTime?.get(i)
            val movieBudget = movieBudget?.get(i)
            val movieRevenue = movieRevenue?.get(i)
            val movieScr1 = movieScreenPlay1?.get(i)
            val movieScr2 = movieScreenPlay2?.get(i)
            val movieGenre = movieGenres?.get(i)
            val movieKeywords = movieKeywords?.get(i)
            val movieScore = movieScore?.get(i)
            val movieViewer = movieViewers?.get(i)

            val movie = MovieDataModels(
                moviePic,
                moviePicBack,
                moviePicRel1,
                moviePicRel2,
                moviePicRel3,
                movieName,
                movieRelease,
                movieOverView,
                movieLastToday,
                movieLastWeek,
                movieDir1,
                movieDir2,
                movieOriLang,
                movieRunTime,
                movieBudget,
                movieRevenue,
                movieScr1,
                movieScr2,
                movieGenre,
                movieKeywords,
                movieScore,
                movieViewer
            )
            dataModelMovies.add(movie)
        }
        // for in adapter arguments
        adapterMovie?.dataModelMovies = dataModelMovies
        Log.d(tagLog, "Movie Adapter Has been set UP ")
    }

    /**
     * END OF Submission 1 Func
     */
*/

}