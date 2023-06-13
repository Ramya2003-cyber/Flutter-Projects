package com.example.bookhut.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.bookhut.Database.BookEntity
import com.example.bookhut.R
import com.example.bookhut.model.Book
import com.squareup.picasso.Picasso

class FavouriteRecyclerAdapter(val context: Context,val bookList: List<BookEntity>) :RecyclerView.Adapter<FavouriteRecyclerAdapter.FavouriteViewHolder>(){
    class FavouriteViewHolder(view: View):RecyclerView.ViewHolder(view)
    {
        val txtBookName:TextView=view.findViewById(R.id.fav_tvBookName)
        val txtBookAuthor:TextView=view.findViewById(R.id.fav_tvBookAuthor)
        val txtBookPrice:TextView=view.findViewById(R.id.fav_tvBookPrice)
        val txtBookRating:TextView=view.findViewById(R.id.fav_tvrating)
        val imgBookImage: ImageView =view.findViewById(R.id.fav_imgBookImage)
        val rlContent:RelativeLayout=view.findViewById(R.id.fav_singlerow)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouriteViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.recycler_favourite_single_row,parent,false)
        return FavouriteViewHolder(view)
    }

    override fun getItemCount(): Int {
        return bookList.size
    }

    override fun onBindViewHolder(holder: FavouriteViewHolder, position: Int) {
        val book=bookList[position]
        holder.txtBookName.text=book.bookName
        holder.txtBookAuthor.text=book.bookAuthor
        holder.txtBookPrice.text=book.bookPrice
        holder.txtBookRating.text=book.bookRating
        Picasso.get().load(book.bookImage).error(R.drawable.book).into(holder.imgBookImage)

    }

}