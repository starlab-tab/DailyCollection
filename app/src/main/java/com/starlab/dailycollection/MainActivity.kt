package com.starlab.dailycollection

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.GravityCompat
import androidx.core.view.isVisible
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import com.bumptech.glide.Glide
import com.starlab.dailycollection.logic.viewmodel.CustomViewModelProvider
import com.starlab.dailycollection.logic.viewmodel.UserViewModel
import com.starlab.dailycollection.ui.bottom_nav.create.CreateViewModel
import com.starlab.dailycollection.ui.collection_search.SearchActivity
import com.starlab.dailycollection.ui.drawer_nav.user.UserHomeActivity
import kotlinx.android.synthetic.main.activity_details.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.drawer_nav_header.*
import kotlinx.android.synthetic.main.fragment_user_home.*

class MainActivity : AppCompatActivity() {

    private var backPressTime = 0L

    private val userViewModel: UserViewModel by viewModels {
        CustomViewModelProvider.getUserViewModel(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        val navController = findNavController(R.id.nav_host_fragment)
        val appBarConfiguration = AppBarConfiguration(setOf(
            R.id.navigation_home, R.id.navigation_create, R.id.navigation_category),drawerLayout)
        setupActionBarWithNavController(navController, appBarConfiguration)
        nav_bottom.setupWithNavController(navController)
        nav_drawer.setCheckedItem(R.id.drawer_nav_summary)
        nav_drawer.setNavigationItemSelectedListener {

            when (it.itemId) {
                R.id.drawer_nav_summary -> {
                    Toast.makeText(this, "123", Toast.LENGTH_SHORT).show()
                    drawerLayout.closeDrawers()
                }
            }
            true
        }
        toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.bar_search -> {
                    val intent = Intent(this, SearchActivity::class.java)
                    startActivity(intent)
                }
            }
            true
        }

        drawerLayout.addDrawerListener(object: DrawerLayout.DrawerListener {
            override fun onDrawerStateChanged(newState: Int) {

            }

            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
            }

            override fun onDrawerClosed(drawerView: View) {
            }

            override fun onDrawerOpened(drawerView: View) {

                userViewModel.userLiveData.observe(this@MainActivity, Observer {
                    val user = it
                    if (it != null) {
                        if (it.avatar.length > 1) Glide.with(this@MainActivity).load(it.avatar).into(userDrawerAvatar)
                        userDrawerCodeName.setText(it.codeName)
                        userDrawerWord.setText(it.word)

                        userViewModel.dCountLiveData.observe(this@MainActivity, Observer {
                            user.count = it
                        })
                    }
                })

                drawerHead.setOnClickListener {
                    val intent = Intent(DailyCollectionApplication.context, UserHomeActivity::class.java)
                    intent.putExtra("user_parcelize", userViewModel.userLiveData.value)
                    startActivity(intent)
                }
            }
        })
    }



    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toobar, menu)
        return true
    }


    override fun onSupportNavigateUp(): Boolean {
        drawerLayout.openDrawer(GravityCompat.START)
        return true
    }

    override fun onBackPressed() {
        val nowTime = System.currentTimeMillis()
        if (nowTime - backPressTime > 2000) {
            backPressTime = nowTime
            Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show()
        } else {
            super.onBackPressed()
        }
    }
}
