package com.example.bookhut.activity

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.*
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.room.Room
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.bookhut.Database.BookDatabase
import com.example.bookhut.Database.BookEntity
import com.example.bookhut.R
import com.example.bookhut.util.ConnectionManager
import com.squareup.picasso.Picasso
import org.json.JSONObject
import java.lang.reflect.Method

class Description : AppCompatActivity() {
    lateinit var txtbookname:TextView
    lateinit var txtbookauthor:TextView
    lateinit var txtbookprice:TextView
    lateinit var txtbookrating:TextView
    lateinit var imgbookimage:ImageView
    lateinit var txtdescription:TextView
    lateinit var txtdescriptiontext:TextView
    lateinit var addtofavoutites:TextView
    lateinit var progressbar:ProgressBar
    lateinit var progressbarlayout:RelativeLayout
    lateinit var toolbar: androidx.appcompat.widget.Toolbar
    var bookId:String="100"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_description)
        txtbookname=findViewById(R.id.txtBookName)
        txtbookauthor=findViewById(R.id.tvnameoftheauthor)
        txtbookprice=findViewById(R.id.tvBookPrice)
        txtbookrating=findViewById(R.id.tvrating)
        imgbookimage=findViewById(R.id.imgBookImage)
        txtdescription=findViewById(R.id.tvdescription)
        addtofavoutites=findViewById(R.id.addtofavourites)
        progressbar=findViewById(R.id.description_progressbar)
        progressbarlayout=findViewById(R.id.progress_bar_layout)
        txtdescriptiontext=findViewById(R.id.description_text)
        progressbar.visibility= View.VISIBLE
        progressbarlayout.visibility=View.VISIBLE
        toolbar=findViewById(R.id.description_toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title="Book Details"
        if(intent!=null)
        {
            bookId= intent.getStringExtra("book_id").toString()
        }
        else
        {
            finish()
            Toast.makeText(this@Description,"Some unexpected error occured",Toast.LENGTH_SHORT).show()
        }

        if(bookId=="100")
        {
            finish()
            Toast.makeText(this@Description,"Some unexpected error occured",Toast.LENGTH_SHORT).show()
        }

        val queue= Volley.newRequestQueue(this@Description)
        val url="http://13.235.250.119/v1/book/get_book/"
        val jsonParams=JSONObject()
        jsonParams.put("book_id",bookId)
        if(ConnectionManager().checkConnectivity(this@Description))
        {
            val jsonObject=object:JsonObjectRequest(
                Method.POST,url,jsonParams,
                Response.Listener {
                    try{
                        val success=it.getBoolean("success")
                        if(success)
                        {
                            val bookJsonObject=it.getJSONObject("book_data")
                            progressbarlayout.visibility=View.GONE
                            val bookImageUrl=bookJsonObject.getString("image")
                            Picasso.get().load(bookJsonObject.getString("image")).into(imgbookimage)
                            txtbookname.text=bookJsonObject.getString("name")

                            txtbookauthor.text=bookJsonObject.getString("author")
                            txtbookprice.text=bookJsonObject.getString("price")
                            txtbookrating.text=bookJsonObject.getString("rating")
                            txtdescriptiontext.text=bookJsonObject.getString("description")

                            val bookEntity=BookEntity(
                                bookId?.toInt() as Int,
                                txtbookname.text.toString(),
                                txtbookauthor.text.toString(),
                                txtbookprice.text.toString(),
                                txtbookrating.text.toString(),
                                txtdescriptiontext.text.toString(),
                                bookImageUrl

                            )
                    val checkFav=DBAsyncTask(applicationContext,bookEntity,1).execute()
                            val isFav= checkFav.get()
                            if(isFav)
                            {
                                addtofavoutites.text="Remove from favourites"
                                val favColor=ContextCompat.getColor(applicationContext,R.color.favourites)
                                addtofavoutites.setBackgroundColor(favColor)
                            }
                            else
                            {
                                addtofavoutites.text="Add to favourites"
                            }
                            addtofavoutites.setOnClickListener {
                                if(!DBAsyncTask(applicationContext,bookEntity,1).execute().get())
                                {
                                    val async=DBAsyncTask(applicationContext,bookEntity,2).execute()
                                    val result=async.get()//boolean
                                    if(result)
                                    {
                                        Toast.makeText(
                                            this@Description,
                                            "Book added to favourites ",
                                            Toast.LENGTH_SHORT

                                        ).show()
                                        addtofavoutites.text="Remove from Favourites"
                                        val favColor=ContextCompat.getColor(applicationContext,R.color.favourites)
                                        addtofavoutites.setBackgroundColor(favColor)
                                    }
                                    else
                                    {
                                        Toast.makeText(
                                            this@Description,
                                            "Some Error Occured",
                                            Toast.LENGTH_SHORT

                                        ).show()
                                    }

                                }
                                else
                                {
                                    val async=DBAsyncTask(applicationContext,bookEntity,3).execute()
                                    val result=async.get()
                                    if(result)
                                    {
                                        Toast.makeText(
                                            this@Description,
                                            "Book Removed from Favourites",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                        addtofavoutites.text="Add to Favourites"
                                        val favColor=ContextCompat.getColor(applicationContext,R.color.purple_700)
                                        addtofavoutites.setBackgroundColor(favColor)


                                    }
                                }
                            }
                        }
                        else{
                            Toast.makeText(this@Description,"Some Error Occured",Toast.LENGTH_SHORT).show()

                        }
                    }
                    catch (e:Exception)
                    {
                        Toast.makeText(this@Description,"Some Error Occured",Toast.LENGTH_SHORT).show()

                    }

                },
                Response.ErrorListener {
                    Toast.makeText(this@Description,"Volley Error Occured",Toast.LENGTH_SHORT).show()


                })
            {
                override fun getHeaders():MutableMap<String,String>
                {
                    val headers=HashMap<String,String>()
                    headers["Content-Type"]="application/json"
                    headers["token"]="3b515816bb8f4b"
                    return headers
                }


            }
            queue.add(jsonObject)
        }
      else
        {
            val dialog = AlertDialog.Builder(this@Description)
            dialog.setTitle("Error")
            dialog.setMessage("Internet Connection Not Found")
            dialog.setPositiveButton("Open Settings") { text, listener ->
                val settingsintent = Intent(Settings.ACTION_WIRELESS_SETTINGS)
                startActivity(settingsintent)
                finish()
            }
            dialog.setNegativeButton("Exit App") { text, listener ->
                ActivityCompat.finishAffinity(this@Description)
            }
            dialog.create()
            dialog.show()
        }

    }
    /*
    Mode1->Check DB if the book is favourite or not
    Mode2->Save the book into DB as favorite
    Mode3->Remove the favourite book
     */
    class DBAsyncTask(val context: Context, val bookEntity: BookEntity, val mode:Int):
        AsyncTask<Void, Void, Boolean>()
    {
        val db= Room.databaseBuilder(context ,BookDatabase::class.java,"books-db").build()

        override fun doInBackground(vararg p0: Void?): Boolean {
            when(mode)
            {
                1->{
                    val book:BookEntity?=db.bookDao().getBookById(bookEntity.book_id.toString())
                    db.close()
                    return book!=null
                }
                2->{
                    db.bookDao().insertBook(bookEntity)
                    db.close()
                    return true
                }
                3->{
                    db.bookDao().deleteBook(bookEntity)
                    db.close()
                    return true
                }

            }
            return false

        }

    }

}