package com.example.foodex.activity

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.foodex.R
import com.example.foodex.util.ConnectionManager
import org.json.JSONException
import org.json.JSONObject

class Otp : AppCompatActivity() {
    lateinit var etotp:EditText
    lateinit var etpassword:EditText
    lateinit var etConfirmPassword:EditText
    lateinit var btnSubmit: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.otp)
        etotp=findViewById(R.id.otp_text)
        etpassword=findViewById(R.id.otp_new_password)
        etConfirmPassword=findViewById(R.id.otp_confirm_password)
        btnSubmit=findViewById(R.id.btn_otp_submit)

        toolbar=findViewById(R.id.otp_toolbar) as Toolbar
        toolbar.setTitle(R.string.otp)
        toolbar.setTitleTextColor(Color.WHITE)
        toolbar.setBackgroundColor(getResources().getColor(R.color.background))
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.back_arrow))
        toolbar.setNavigationOnClickListener {
            val intent= Intent(this@Otp,ForgotPassword::class.java)
            startActivity(intent)
        }

        btnSubmit.setOnClickListener {
            val otp=etotp.text.toString()
            val new_password=etpassword.text.toString()
            val confirm_password=etConfirmPassword.text.toString()
            val bundle=intent.extras
            val mobileNumber= bundle!!.getString("mobile_number")


            if(new_password==confirm_password){
                val queue= Volley.newRequestQueue(this@Otp)
                val url="http://13.235.250.119/v2/reset_password/fetch_result"
                val jsonParams=JSONObject()
                jsonParams.put("mobile_number",mobileNumber)
                jsonParams.put("otp",otp)
                jsonParams.put("password",new_password)
                if(ConnectionManager().checkConnectivity(this))
                {
                    val jsonObjectRequest=object:JsonObjectRequest(Method.POST,url,jsonParams, Response.Listener {
                            try{
                                val data=it.getJSONObject("data")
                                val success=data.getBoolean("success")
                                if(success)
                                {
                                    Toast.makeText(this@Otp,data.getString("successMessage"),Toast.LENGTH_SHORT).show()
                                }
                                else
                                {
                                    Toast.makeText(this@Otp,"Error",Toast.LENGTH_SHORT).show()

                                }
                            }
                            catch (e:JSONException)
                            {
                                Toast.makeText(this@Otp,"Json Exception",Toast.LENGTH_SHORT).show()

                            }

                    },Response.ErrorListener {  })
                    {
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
}