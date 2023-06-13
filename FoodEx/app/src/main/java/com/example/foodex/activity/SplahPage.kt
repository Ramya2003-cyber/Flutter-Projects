package com.example.foodex.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler;
import android.os.Looper
import com.example.foodex.R

class SplahPage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splahpage)
        Handler(Looper.getMainLooper()).postDelayed({
           var intent= Intent(this@SplahPage, LoginPage::class.java)
            startActivity(intent)
            finish()
        },2000)

    }
}