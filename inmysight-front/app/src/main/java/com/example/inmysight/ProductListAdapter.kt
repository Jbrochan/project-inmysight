package com.example.inmysight

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ProductListAdapter(val itemList: ArrayList<Product>): RecyclerView.Adapter<ProductListAdapter.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductListAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.product_list, parent, false)
        return ViewHolder(view)
    }
    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: ProductListAdapter.ViewHolder, position: Int) {
        holder.shelf.text = itemList[position].shelf
        holder.name.text = itemList[position].name
        holder.quantity.text = itemList[position].quantity
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val shelf: TextView = itemView.findViewById(R.id.itemShelf)
        val name: TextView = itemView.findViewById(R.id.itemName)
        val quantity: TextView = itemView.findViewById(R.id.itemQuantity)
    }
}