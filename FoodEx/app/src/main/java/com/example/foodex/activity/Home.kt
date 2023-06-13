package com.example.foodex.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.FrameLayout
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.foodex.R
import com.example.foodex.fragment.HomeFragment
import com.google.android.material.navigation.NavigationView

lateinit var toolbar:Toolbar
lateinit var drawerlayout:DrawerLayout
lateinit var navigationView: NavigationView
lateinit var frameLayout: FrameLayout
lateinit var coordinatorLayout: CoordinatorLayout
class Home : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home)

        toolbar=findViewById<Toolbar>(R.id.home_toolbar)
        drawerlayout=findViewById(R.id.drawerlayout)
        navigationView=findViewById(R.id.home_navigation_view)
        frameLayout=findViewById(R.id.home_frame_layout)
        coordinatorLayout=findViewById(R.id.home_coordinator_layout)
        var previousItem:MenuItem?=null
        setUpToolbar()
        openHome()
        val actionBarDrawerToggle=ActionBarDrawerToggle(
            this@Home,
            drawerlayout,
            R.string.open_drawer,
            R.string.close_drawer
        )
        drawerlayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()
        actionBarDrawerToggle.setDrawerIndicatorEnabled(false)
        toolbar.setNavigationIcon(R.drawable.hamburger)


        navigationView.setNavigationItemSelectedListener {
            if(previousItem!=null)
            {
                previousItem?.isChecked=false
            }
            it.isCheckable=true
            it.isChecked=true
            previousItem=it
            when(it.itemId)
            {
                R.id.home->{
                    openHome()
                    drawerlayout.closeDrawers()
                }

            }

            return@setNavigationItemSelectedListener true
        }


    }
    fun setUpToolbar()
    {
        setSupportActionBar(toolbar)
        supportActionBar?.title="Foodex"
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id =item.itemId
        if(id==android.R.id.home)
        {
            drawerlayout.openDrawer(GravityCompat.START)
        }
        return super.onOptionsItemSelected(item)
    }
    fun openHome()
    {
        val fragment=HomeFragment()
        val transaction=supportFragmentManager.beginTransaction()
        transaction.replace(R.id.home_frame_layout,fragment)
        transaction.commit()
        supportActionBar?.title="All restaurants"
        navigationView.setCheckedItem(R.id.home)
    }


}
