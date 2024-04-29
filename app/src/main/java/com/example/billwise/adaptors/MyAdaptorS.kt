package com.example.billwise.adaptors

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.billwise.R
import com.example.billwise.models.Bill

class MyAdaptorS (private val itemList : ArrayList<Bill>) : RecyclerView.Adapter<MyAdaptorS.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.home_usage_items,parent,false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentitem = itemList[position]


        holder.wattage.text = currentitem.wattage
        holder.quantity.text = currentitem.quantity
        holder.dailyUsage.text = currentitem.dailyUsage
        holder.unitPrice.text = currentitem.unitPrice
        holder.results.text = currentitem.results.toString()



    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    class MyViewHolder(itemView : View,) : RecyclerView.ViewHolder(itemView){


        val wattage : TextView = itemView.findViewById(R.id.tvWattage)
        val quantity : TextView = itemView.findViewById(R.id.tvQuantity)
        val dailyUsage : TextView = itemView.findViewById(R.id.tvHours)
        val unitPrice : TextView = itemView.findViewById(R.id.tvUnit)
        val results : TextView = itemView.findViewById(R.id.tvRes)




    }


}