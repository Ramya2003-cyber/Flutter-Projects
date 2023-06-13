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

class Register : AppCompatActivity() {
    lateinit var toolbar: Toolbar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register)

        val name: EditText
        val email:EditText
        val mobileNumber:EditText
        val address:EditText
        val password:EditText
        val confirmpassword:EditText
        val register:Button


        name=findViewById(R.id.name)
        email=findViewById(R.id.email)
        mobileNumber=findViewById(R.id.mobile)
        address=findViewById(R.id.address)
        password=findViewById(R.id.password)
        confirmpassword=findViewById(R.id.confirm_password)
        register=findViewById(R.id.register)

        toolbar=findViewById(R.id.register_toolbar) as Toolbar
        toolbar.setTitle(R.string.register_toolbar)
        toolbar.setTitleTextColor(Color.WHITE)
        toolbar.setBackgroundColor(getResources().getColor(R.color.background))
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.back_arrow))
        toolbar.setNavigationOnClickListener {
            val intent=Intent(this@Register,LoginPage::class.java)
            startActivity(intent)
        }




        register.setOnClickListener {
            val name1=name.text.toString()
            val email1=email.text.toString()
            val mobileNumber1=mobileNumber.text.toString()
            val address1=address.text.toString()
            val password1=password.text.toString()
            val confirmpassword1=confirmpassword.text.toString()
            if(password1==confirmpassword1)
            {
                val queue=Volley.newRequestQueue(this@Register)
                val url="http://13.235.250.119/v2/register/fetch_result"
                val jsonParams=JSONObject()
                jsonParams.put("name",name1)
                jsonParams.put("email",email1)
                jsonParams.put("mobile_number",mobileNumber1)
                jsonParams.put("address",address1)
                jsonParams.put("password",password1)
                if(ConnectionManager().checkConnectivity(this@Register))
                {
                    val jsonObjectRequest=object:JsonObjectRequest(
                        Method.POST,url,jsonParams,
                        Response.Listener {
                                          try {

                                              val data=it.getJSONObject("data")
                                              val success =
                                                  data.getBoolean("success")

                                              if(success)
                                              {
                                                  Toast.makeText(this@Register,
                                                      "Your registration is successful",Toast.LENGTH_SHORT).show()
                                              }
                                              else
                                              {

                                                  Toast.makeText(this@Register,
                                                      "Some Error Occured",Toast.LENGTH_SHORT).show()

                                              }

                                          }
                                          catch (e:JSONException)
                                          {

                                              Toast.makeText(this@Register,
                                                  "Some Json Error Occured",Toast.LENGTH_SHORT).show()

                                          }


                        },
                        Response.ErrorListener {
                            Toast.makeText(this@Register,
                                "Some Json Listener Error Occured",Toast.LENGTH_SHORT).show()

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

//                val bundle=Bundle()
//                bundle.putString("name",name1)
//                bundle.putString("emailid",email1)
//                bundle.putString("mobile",mobileNumber1)
//                bundle.putString("password",password1)
//                bundle.putString("address",address1)
//                val intent= Intent(this@Register, AfterRegister::class.java)
//                intent.putExtras(bundle)
//                startActivity(intent)
            }
            else
            {
                Toast.makeText(this,"Passwords are not same try again",Toast.LENGTH_SHORT).show()
            }

        }







    }
}