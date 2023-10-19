package com.example.inmysight.ui.shelf

import android.content.ContentValues.TAG
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.inmysight.databinding.ShelfListBinding
import com.example.inmysight.model.Shelf


class ShelfListAdapter(val itemList: ArrayList<Shelf>): RecyclerView.Adapter<ShelfListAdapter.ViewHolder>() {
    // Variables
    private lateinit var binding: ShelfListBinding    // Using binding
    private lateinit var shelfClickListener: OnShelfClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ShelfListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //holder.shelf.text = itemList[position].shelf

        /*
        binding.shelfButton.setOnClickListener {
            Log.d(TAG, binding.shelfButton.text.toString())
        }
         */

        // new
        val item = itemList[position]
        holder.bind(item)

    }

    inner class ViewHolder(binding: ShelfListBinding): RecyclerView.ViewHolder(binding.root){
        val shelf: TextView = binding.shelfButton

        // new
        fun bind(item: Shelf){
            binding.shelfButton.text = item.shelf
            binding.shelfButton.setOnClickListener {
                val pos = adapterPosition
                if(pos != RecyclerView.NO_POSITION && shelfClickListener != null){
                    shelfClickListener.onShelfClick(binding.root, pos)
                    Log.d(TAG, "Click")
                }
            }
        }
    }

    // Custom listener interface
    interface OnShelfClickListener{
        fun onShelfClick(view: View, position: Int)
    }

    // Deliver listener interface object
    fun setOnShelfClickListener(onShelfClickListener: OnShelfClickListener){
        shelfClickListener = onShelfClickListener
    }
}