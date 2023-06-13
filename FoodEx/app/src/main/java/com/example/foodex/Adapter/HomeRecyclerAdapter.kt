package com.example.foodex.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.foodex.R
import com.example.foodex.model.Restaurant
import com.squareup.picasso.Picasso

class HomeRecyclerAdapter (val context: Context, val itemList:ArrayList<Restaurant>):RecyclerView.Adapter<HomeRecyclerAdapter.HomeViewHolder>(){
    class HomeViewHolder(view: View):RecyclerView.ViewHolder(view)
    {
        val resName:TextView=view.findViewById(R.id.txtResName)
        val resCost:TextView=view.findViewById(R.id.txtResCost)
        val resRating:TextView=view.findViewById(R.id.txtResRating)
        val resImage:ImageView=view.findViewById(R.id.imgRes)
        val favImage:ImageView=view.findViewById(R.id.imgFavourite)
        val singlerow:LinearLayout=view.findViewById(R.id.resSingleRow)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.restaurant_single_row,parent,false)
        return HomeViewHolder(view)
     }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        val restaurant=itemList[position]
        holder.resName.text=restaurant.resName
        holder.resCost.text="Rs."+restaurant.costForOne+"/person"
        holder.resRating.text=restaurant.resRating
        Picasso.get().load(restaurant.resImage).error(R.drawable.foodexappicon).into(holder.resImage)
        holder.singlerow.setOnClickListener {
            Toast.makeText(context,"hi",Toast.LENGTH_SHORT).show()
        }

    }

}