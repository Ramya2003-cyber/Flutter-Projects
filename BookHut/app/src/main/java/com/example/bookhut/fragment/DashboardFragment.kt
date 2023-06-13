package com.example.bookhut.fragment

import android.app.Activity
import android.app.AlertDialog
import android.app.DownloadManager.Request
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.*
import android.widget.Button
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.bookhut.R
import com.example.bookhut.adapter.DashboardRecyclerAdapter
import com.example.bookhut.model.Book
import com.example.bookhut.util.ConnectionManager
import org.json.JSONException
import java.util.Collections
import java.util.zip.Inflater


class DashboardFragment : Fragment() {


    lateinit var recyclerDashboard: RecyclerView
    lateinit var layoutManager: RecyclerView.LayoutManager

    lateinit var recyclerAdapter: DashboardRecyclerAdapter
    lateinit var progresslayout:RelativeLayout
    lateinit var progressbar:ProgressBar
    val bookInfoList = arrayListOf<Book>()
    var ratingComparator=Comparator<Book>{book1,book2->
        if(book1.bookRating.compareTo(book2.bookRating,true)==0)
        {
            book1.bookName.compareTo(book2.bookName,true)
        }
        else{
            book1.bookRating.compareTo(book2.bookRating,true)
        }
   }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment(just like setcontentview in activity)
        val view = inflater.inflate(R.layout.fragment_dashboard, container, false)
        setHasOptionsMenu(true)
        progresslayout=view.findViewById(R.id.rlprogressbar)
        progressbar=view.findViewById(R.id.progressbar)
        progresslayout.visibility=View.VISIBLE
        recyclerDashboard = view.findViewById(R.id.rvdashboard) as RecyclerView
        layoutManager = LinearLayoutManager(context)


        //it turns a complete layout file into a view
        //we want to put the fragment layout in the container(a view group) that the activity provides for us
        //attach to root is false becoz we dont want this layout to attach permanently to the root activity

        val queue = Volley.newRequestQueue(
            activity as Context
        )
        val url = "http://13.235.250.119/v1/book/fetch_books"
        if(activity!=null)
        if (ConnectionManager().checkConnectivity(activity as Context)) {

            val jsonObjectRequest =
                object : JsonObjectRequest(Method.GET, url, null, Response.Listener {
                    try {
                        progresslayout.visibility=View.GONE
                        val success = it.getBoolean("success")
                        if (success) {
                            val data = it.getJSONArray("data")
                            for (i in 0 until data.length()) {
                                val bookJsonObject = data.getJSONObject(i)
                                val bookObject = Book(
                                    bookJsonObject.getString("book_id"),
                                    bookJsonObject.getString("name"),
                                    bookJsonObject.getString("author"),
                                    bookJsonObject.getString("rating"),
                                    bookJsonObject.getString("price"),
                                    bookJsonObject.getString("image")
                                )
                                bookInfoList.add(bookObject)
                                recyclerAdapter =
                                    DashboardRecyclerAdapter(activity as Context, bookInfoList)
////        as is used for typecasting
                                recyclerDashboard.adapter = recyclerAdapter
                                recyclerDashboard.layoutManager = layoutManager
                                recyclerDashboard.addItemDecoration(
                                    DividerItemDecoration(
                                        recyclerDashboard.context,
                                        (layoutManager as LinearLayoutManager).orientation
                                    )
                                )
                            }


                        }
                    }
                    catch (e:JSONException)
                    {
                        if(activity!=null)
                         Toast.makeText(activity as Context,"Some Exception in Json",Toast.LENGTH_SHORT).show()
                    }



                }, Response.ErrorListener {
                    if(activity!=null)
                     Toast.makeText(activity as Context,"Volley error occured",Toast.LENGTH_SHORT)
                }) {
                    override fun getHeaders(): MutableMap<String, String> {
                        val headers = HashMap<String, String>()
                        headers["Content-Type"] = "application/json"
                        headers["token"] = "3b515816bb8f4b"
                        return headers

                    }
                }


            queue.add(jsonObjectRequest)
        } else {
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



        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater?.inflate(R.menu.menu_dashboard,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id=item?.itemId
        if(id==R.id.action_sort)
        {
            Collections.sort(bookInfoList,ratingComparator)
            bookInfoList.reverse()
        }
        recyclerAdapter.notifyDataSetChanged()
        return super.onOptionsItemSelected(item)
    }
}