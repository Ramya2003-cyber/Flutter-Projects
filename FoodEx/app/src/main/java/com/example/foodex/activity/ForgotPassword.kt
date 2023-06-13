package com.example.foodex.activity

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.foodex.R
import com.example.foodex.util.ConnectionManager
import org.json.JSONException
import org.json.JSONObject

class ForgotPassword : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.forgotpassword)

        val etmobilenumber:EditText
        val etemailid:EditText
        val btnnext:Button
        val toolbar:Toolbar

        etmobilenumber=findViewById(R.id.forgot_etmobilenumber)
        etemailid=findViewById(R.id.forgot_etemailid)
        btnnext=findViewById(R.id.btnnext)
        toolbar=findViewById(R.id.forgot_toolbar)
        toolbar.setTitle(R.string.forgottoolbar)
        toolbar.setTitleTextColor(Color.WHITE)
        toolbar.setBackgroundColor(getResources().getColor(R.color.background))
        toolbar.setNavigationIcon(R.drawable.back_arrow)
        toolbar.setNavigationOnClickListener {
            val intent=Intent(this@ForgotPassword,LoginPage::class.java)
            startActivity(intent)
        }


        btnnext.setOnClickListener {
            val mobilenumber:String=etmobilenumber.text.toString()
            val emailid:String=etemailid.text.toString()

            val queue= Volley.newRequestQueue(this@ForgotPassword)
            val url="http://13.235.250.119/v2/forgot_password/fetch_result"
            val jsonParams=JSONObject();
            jsonParams.put("mobile_number",mobilenumber)
            jsonParams.put("email",emailid)
            if(ConnectionManager().checkConnectivity(this@ForgotPassword))
            {
                val jsonObjectRequest=object :JsonObjectRequest(Method.POST,url,jsonParams, Response.Listener {
                    try {
                        val data=it.getJSONObject("data")
                        val success=data.getBoolean("success")
                        if(success)
                        {
                            val bundle=Bundle();
                            bundle.putString("mobile_number",mobilenumber)

                            val intent=Intent(this@ForgotPassword,Otp::class.java);
                            intent.putExtras(bundle)
                            startActivity(intent)
                        }
                        else
                        {
                            Toast.makeText(this@ForgotPassword,"Invalid Credentials",Toast.LENGTH_SHORT)
                        }
                    }
                    catch (e:JSONException)
                    {
                        Toast.makeText(this@ForgotPassword,"Json Exception Occured",Toast.LENGTH_SHORT)
                    }



                },Response.ErrorListener {

                }){
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


    }


}