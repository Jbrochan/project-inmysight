package com.example.inmysight

import android.content.ContentValues.TAG
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.inmysight.databinding.ShelfListBinding
import org.w3c.dom.Text


class ShelfListAdapter(val itemList: ArrayList<Shelf>): RecyclerView.Adapter<ShelfListAdapter.ViewHolder>() {
    private lateinit var binding: ShelfListBinding
    /*
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.shelf_list, parent, false)
        return ViewHolder(view)
    }
     */

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ShelfListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.shelf.text = itemList[position].shelf

        binding.shelfButton.setOnClickListener {
            Log.d(TAG, "click")
        }
    }

    /*
    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val shelf: TextView = itemView.findViewById(R.id.shelfButton)
    }
     */
    inner class ViewHolder(binding: ShelfListBinding): RecyclerView.ViewHolder(binding.root){
        val shelf: TextView = binding.shelfButton
    }
}