package com.example.foodex.fragment

import android.app.Activity
import android.app.AlertDialog
import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.*
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.SearchView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Header
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.foodex.Adapter.HomeRecyclerAdapter
import com.example.foodex.R
import com.example.foodex.model.Restaurant
import com.example.foodex.util.ConnectionManager
import org.json.JSONException
import org.json.JSONObject


class HomeFragment : Fragment() {
    lateinit var recyclerHome:RecyclerView
    lateinit var layoutManager: RecyclerView.LayoutManager
    lateinit var recyclerAdapter: HomeRecyclerAdapter
    lateinit var progresslayout:RelativeLayout
    lateinit var progressbar:ProgressBar

    val resInfoList=arrayListOf<Restaurant>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view=inflater.inflate(R.layout.home_fragment,container,false)
        setHasOptionsMenu(true)
        progresslayout=view.findViewById(R.id.rlprogressbar)
        progressbar=view.findViewById(R.id.progressbar)
        progresslayout.visibility=View.VISIBLE
        recyclerHome=view.findViewById(R.id.rvhome) as RecyclerView
        layoutManager=LinearLayoutManager(context)

        val queue= Volley.newRequestQueue(activity as Context)
        val url="http://13.235.250.119/v2/restaurants/fetch_result/"
        if(activity!=null)
        {
            if(ConnectionManager().checkConnectivity(activity as Context))
            {
                val jsonObjectRequest=object:JsonObjectRequest(Method.GET,url,null,
                Response.Listener {
                                try {
                                    progresslayout.visibility=View.GONE
                                    val data=it.getJSONObject("data")
                                    val success=data.getBoolean("success")
                                    if(success)
                                    {
                                        val jsonRestaurantArray=data.getJSONArray("data")
                                        for(i in 0 until jsonRestaurantArray.length())
                                        {
                                            val restaurantJsonObject=jsonRestaurantArray.getJSONObject(i)
                                            val restaurantObject=Restaurant(
                                                restaurantJsonObject.getString("name"),
                                                restaurantJsonObject.getString("rating"),
                                                restaurantJsonObject.getString("cost_for_one"),
                                                restaurantJsonObject.getString("image_url")
                                            )
                                            resInfoList.add(restaurantObject)
                                            recyclerAdapter=
                                                HomeRecyclerAdapter(activity as Context,resInfoList)
                                            recyclerHome.adapter=recyclerAdapter
                                            recyclerHome.layoutManager=layoutManager
                                            recyclerHome.addItemDecoration(
                                                DividerItemDecoration(
                                                    recyclerHome.context,
                                                    (layoutManager as LinearLayoutManager).orientation
                                                )
                                            )
                                        }
                                    }
                                }
                                catch (e:JSONException)
                                {
                                    if(activity!=null)
                                    {
                                        Toast.makeText(activity as Context,"Some Exception in Json",Toast.LENGTH_SHORT).show()
                                    }
                                }

                },Response.ErrorListener {
                        if(activity!=null)
                            Toast.makeText(activity as Context,"Volley error occured",Toast.LENGTH_SHORT)


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
            else
            {
                val dialog = AlertDialog.Builder(activity as Context)
                dialog.setTitle("Error")
                dialog.setMessage("Internet Connection Not Found")
                dialog.setPositiveButton("Open Settings") { text, listener ->
                    val settingsintent = Intent(Settings.ACTION_WIRELESS_SETTINGS)
                    startActivity(settingsintent)
                    activity?.finish()
                }
                dialog.setNegativeButton("Exit App") { text, listener ->
                    ActivityCompat.finishAffinity(activity as Activity)
                }
                dialog.create()
                dialog.show()
            }
        }
        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.menu_drawer,menu)
//        val searchManager=getSystemService(Context.SEARCH_SERVICE) as SearchManager
//        (menu.findItem(R.id.search_bar).actionView as SearchView).apply{
//            setSearchableInfo(searchManager.getSearchableInfo())
//        }


    }

}