package com.example.inmysight

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.w3c.dom.Text

class ShelfListAdapter(val itemList: ArrayList<Shelf>): RecyclerView.Adapter<ShelfListAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.shelf_list, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.shelf.text = itemList[position].shelf
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val shelf: TextView = itemView.findViewById(R.id.shelfButton)
    }
}