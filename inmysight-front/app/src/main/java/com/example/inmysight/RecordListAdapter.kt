package com.example.inmysight

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecordListAdapter (val itemList: ArrayList<Record>): RecyclerView.Adapter<RecordListAdapter.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.record_list, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.trade.text = itemList[position].trade
        holder.name.text = itemList[position].name
        holder.customer.text = itemList[position].customer
        holder.quantity.text = itemList[position].quantity
        holder.date.text = itemList[position].date
    }
    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val trade: TextView = itemView.findViewById(R.id.recordListTrade)
        val name: TextView = itemView.findViewById(R.id.recordListName)
        val customer: TextView = itemView.findViewById(R.id.recordListCustomer)
        val quantity: TextView = itemView.findViewById(R.id.recordListQuantity)
        val date: TextView = itemView.findViewById(R.id.recordListDate)
    }
}