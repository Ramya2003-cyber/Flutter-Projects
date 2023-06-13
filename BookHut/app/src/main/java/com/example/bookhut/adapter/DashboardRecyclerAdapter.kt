package com.example.bookhut.adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.bookhut.R
import com.example.bookhut.activity.Description
import com.example.bookhut.model.Book
import com.squareup.picasso.Picasso

class DashboardRecyclerAdapter(val context: Context, val itemList:ArrayList<Book>) :RecyclerView.Adapter<DashboardRecyclerAdapter.DashboardViewHolder>(){
    class DashboardViewHolder(view: View):RecyclerView.ViewHolder(view)
    {
       val txtBookName:TextView=view.findViewById(R.id.txtBookName)
        val txtAuthorName:TextView=view.findViewById(R.id.tvnameoftheauthor)
        val txtBookPrice:TextView=view.findViewById(R.id.tvBookPrice)
        val txtRating:TextView=view.findViewById(R.id.tvrating)
        val imgBookImage:ImageView=view.findViewById(R.id.imgBookmage)
       // val llContent:LinearLayout=view.findViewById(R.id.llContent)
        val singlerow:RelativeLayout=view.findViewById(R.id.singlerow)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DashboardViewHolder {
        //Responsible for holding the displayable no.of items
        val view=LayoutInflater.from(parent.context).inflate(R.layout.recycler_dashboard_single_row,parent,false)
//        parent.context is for  view holder and the second parameter is for position
        return DashboardViewHolder(view)
    }

    override fun getItemCount(): Int {

        return itemList.size
    }

    override fun onBindViewHolder(holder: DashboardViewHolder, position: Int) {
        //responsible for recycling the view holders

        val book=itemList[position]
        holder.txtBookName.text=book.bookName
        holder.txtAuthorName.text=book.bookAuthor
        holder.txtBookPrice.text=book.bookPrice
        holder.txtRating.text=book.bookRating
        Picasso.get().load(book.bookImage).error(R.drawable.book).into(holder.imgBookImage);
     //   holder.imgBookImage.setImageResource(book.bookImage)
        holder.singlerow.setOnClickListener{
            val intent = Intent(context,Description::class.java)
            intent.putExtra("book_id",book.bookid)
            context.startActivity(intent)
        }

    }

}