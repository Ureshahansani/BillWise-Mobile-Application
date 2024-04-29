package com.example.billwise.adaptors

import android.view.LayoutInflater

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.billwise.R
import com.example.billwise.models.Item


class MyAdaptor(private val itemList : ArrayList<Item>) : RecyclerView.Adapter<MyAdaptor.MyViewHolder>() {

    private lateinit var mListner: onItemClickListner

    interface onItemClickListner{
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListner(clickListner: onItemClickListner){
        mListner = clickListner
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.rv_cat_items,parent,false)
        return MyViewHolder(itemView, mListner)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentitem = itemList[position]

        holder.name.text = currentitem.name

    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    class MyViewHolder(itemView : View, clickListner: onItemClickListner) : RecyclerView.ViewHolder(itemView){

        val name : TextView = itemView.findViewById(R.id.tvItemName)


        init {
            itemView.setOnClickListener{
                clickListner.onItemClick(adapterPosition)
            }
        }


    }


}