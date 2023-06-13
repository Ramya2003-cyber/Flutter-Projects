package com.example.foodex.activity

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.foodex.R
import com.example.foodex.util.ConnectionManager
import org.json.JSONException
import org.json.JSONObject


class LoginPage : AppCompatActivity() ,View.OnClickListener{
    lateinit var etMobileNumber:EditText
    lateinit var etPassword:EditText
    lateinit var btnLogin:Button
    lateinit var txtForgotPassword:TextView
    lateinit var txtRegister:TextView


    lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.loginpage)

        sharedPreferences=getSharedPreferences(getString(R.string.file_name_preferences), MODE_PRIVATE)
        etMobileNumber=findViewById(R.id.etMobileNumber)
        etPassword=findViewById(R.id.etPassword)
        btnLogin=findViewById(R.id.btnLogIn)
        txtForgotPassword=findViewById(R.id.textForgotPassword)
        txtRegister=findViewById(R.id.textRegister)
        var isLoggedIn=sharedPreferences.getBoolean("isLoggedIn",false);
        if(isLoggedIn)
        {
            val intent =Intent(this@LoginPage, Home::class.java)
            startActivity(intent)
            finish()
        }

        btnLogin.setOnClickListener{

            val mobileNumber=etMobileNumber.text.toString();
            val password=etPassword.text.toString();

            val queue=Volley.newRequestQueue(this@LoginPage)
            val url="http://13.235.250.119/v2/login/fetch_result/"
            val jsonParams=JSONObject()
            jsonParams.put("mobile_number",mobileNumber)
            jsonParams.put("password",password)
            if(ConnectionManager().checkConnectivity(this@LoginPage))
            {
                val jsonObjectRequest=object :JsonObjectRequest(Method.POST,url,jsonParams,
                    Response.Listener {
                        try {

                            val data=it.getJSONObject("data")
                            val success=data.getBoolean("success")
                            if(success)
                            {
                                val userJsonObject=data.getJSONObject("data")
                                val username=userJsonObject.getString("name")
                                sharedPreferences()
                                val intent =Intent(this@LoginPage, Home::class.java)
                                startActivity(intent)
                                finish()
                            }
                            else
                            {
                                Toast.makeText(this@LoginPage,"Invalid Credentials",Toast.LENGTH_SHORT).show()
                            }
                        }
                        catch (e:JSONException)
                        {
                            Toast.makeText(this@LoginPage,"Error",Toast.LENGTH_SHORT).show()
                        }



                    },
                    Response.ErrorListener {

                    }
                ){
                    override fun getHeaders(): MutableMap<String, String> {
                        val headers=HashMap<String,String>()
                        headers["Content-Type"]="application/json"
                        headers["token"]="3b515816bb8f4b"
                        return headers
                    }
                }
                queue.add(jsonObjectRequest)
            }


        }

        txtForgotPassword.setOnClickListener {
            var intent=Intent(this@LoginPage, ForgotPassword::class.java)
            startActivity(intent)
        }
        txtRegister.setOnClickListener {
            var intent=Intent(this@LoginPage, Register::class.java)
            startActivity(intent);
        }

    }




    fun sharedPreferences()
    {
        sharedPreferences.edit().putBoolean("isLoggedIn",true).apply();

    }

    override fun onClick(p0: View?) {

    }


}