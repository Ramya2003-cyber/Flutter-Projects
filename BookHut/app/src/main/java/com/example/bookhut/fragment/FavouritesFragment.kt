package com.example.bookhut.fragment

import android.content.Context
import android.os.AsyncTask
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.RelativeLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import androidx.recyclerview.widget.RecyclerView.Recycler
import androidx.room.Room
import com.example.bookhut.Database.BookDatabase
import com.example.bookhut.Database.BookEntity

import com.example.bookhut.R
import com.example.bookhut.adapter.FavouriteRecyclerAdapter
import com.example.bookhut.model.Book

/**
 * A simple [Fragment] subclass.
 * Use the [FavouritesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FavouritesFragment : Fragment() {

   lateinit var recyclerFavourite:RecyclerView
   lateinit var progressbar:ProgressBar
   lateinit var progressLayout:RelativeLayout
   lateinit var layoutManager: RecyclerView.LayoutManager
   lateinit var recyclerAdapter:FavouriteRecyclerAdapter
   var dbBookList= listOf<BookEntity>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view= inflater.inflate(R.layout.fragment_favourites, container, false)
         recyclerFavourite=view.findViewById(R.id.recyclerFavourite)
        progressLayout=view.findViewById(R.id.progresslayout)
        progressbar=view.findViewById(R.id.fav_progressbar)

        layoutManager=GridLayoutManager(activity as Context,2)

        dbBookList=RetrieveFavourites(activity as Context).execute().get()

        if(activity!=null)
        {
            progressLayout.visibility=View.GONE
            recyclerAdapter= FavouriteRecyclerAdapter(activity as Context,dbBookList)
            recyclerFavourite.adapter=recyclerAdapter
            recyclerFavourite.layoutManager=layoutManager
        }
        
        return view
    }
    class RetrieveFavourites(val context:Context): AsyncTask<Void, Void, List<BookEntity>>() {
        override fun doInBackground(vararg p0: Void?): List<BookEntity> {
            val db= Room.databaseBuilder(context,BookDatabase::class.java,"books-db").build()
            return db.bookDao().getAllBooks()
        }

    }



}