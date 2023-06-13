package com.example.activitylifecycle

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class NewActivity : AppCompatActivity(), View.OnClickListener {
    lateinit var etMobileNumber:EditText
    lateinit var etPassword:EditText
    lateinit var btnLogin:Button
    lateinit var txtForgotPassword:TextView
    lateinit var txtRegister:TextView
    val validMobileNumber="9059132009"
    val validPassword= arrayOf("ramya","tanya","saranya","santhu");
    var nameOfAvenger ="Avengers";
    lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new)
        setContentView(R.layout.activity_new)
        sharedPreferences=getSharedPreferences(getString(R.string.file_name_preferences), MODE_PRIVATE)
        title="Login"
        etMobileNumber=findViewById(R.id.etMobileNumber)
        etPassword=findViewById(R.id.etPassword)
        btnLogin=findViewById(R.id.btnlogin)
        txtForgotPassword=findViewById(R.id.tvForgotPassword)
        txtRegister=findViewById(R.id.tvRegister)
        var isLoggedIn=sharedPreferences.getBoolean("isLoggedIn",false);
        if(isLoggedIn)
        {
            val intent =Intent(this@NewActivity,MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        btnLogin.setOnClickListener{

            val mobileNumber=etMobileNumber.text.toString();
            val password=etPassword.text.toString();
            if(mobileNumber==validMobileNumber )
            {
                if(password==validPassword[0])
                {

                    nameOfAvenger="ramya";
                    sharedPreferences1(nameOfAvenger)
                    intent.putExtra("Name",nameOfAvenger);
                    startActivity(intent)
                }
                if(password==validPassword[1])
                {
                    nameOfAvenger="tanya"
                    sharedPreferences1(nameOfAvenger)
                    intent.putExtra("Name",nameOfAvenger);
                    startActivity(intent)
                }
                if(password==validPassword[2])
                {
                    nameOfAvenger="saranya"
                    sharedPreferences1(nameOfAvenger)
                    intent.putExtra("Name",nameOfAvenger);
                    startActivity(intent)
                }
                if(password==validPassword[3])
                {
                    nameOfAvenger="santhu"
                    sharedPreferences1(nameOfAvenger)
                    intent.putExtra("Name",nameOfAvenger);
                    startActivity(intent)
                }

            }
            else
            {
                Toast.makeText(this@NewActivity,"CLicked Login",Toast.LENGTH_LONG).show()
            }

        }
        }


    override fun onClick(p0: View?) {
        TODO("Not yet implemented")
    }

    override fun onPause() {
        super.onPause()
        finish()
    }

    fun sharedPreferences1(title:String)
    {
        sharedPreferences.edit().putBoolean("isLoggedIn",true).apply();
        sharedPreferences.edit().putString("Title",title).apply()
    }

}


