package com.example.activitylifecycle

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import com.example.activitylifecycle.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {//AppCompatActivity class have all the functions of an activity lifecycle
    var titleName:String ?="";

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var sharedPreferences=getSharedPreferences(getString(R.string.file_name_preferences),
            MODE_PRIVATE)
        setContentView(R.layout.scrollview_example1)
        if(intent!=null)
         titleName= intent.getStringExtra("Name");

    }


    override fun onStart() {
        super.onStart()
        println("onStart called")
    }

    override fun onRestart() {
        super.onRestart()
        println("onRestart called")
    }

    override fun onPause() {
        super.onPause()
        println("onPause called")
    }

    override fun onResume() {
        super.onResume()
        println("onResume called")
    }

    override fun onStop() {
        super.onStop()
        println("onStop called")
    }

    override fun onDestroy() {
        super.onDestroy()
        println("onDestroy called")
    }
}
