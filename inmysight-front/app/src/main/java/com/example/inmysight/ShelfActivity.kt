package com.example.inmysight

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.inmysight.databinding.ActivityShelfBinding
import com.google.firebase.firestore.FirebaseFirestore

class ShelfActivity : AppCompatActivity() {
    // Member variables
    private lateinit var binding: ActivityShelfBinding
    private val db = FirebaseFirestore.getInstance()
    private var itemList = arrayListOf<Shelf>()
    private var adapter = ShelfListAdapter(itemList)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShelfBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        Log.d(TAG, "ShelfActivity is started successfully")

        binding.shelfRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.shelfRecyclerView.adapter = adapter
        binding.shelfRecyclerView.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))

        // Variables
        val userCompany = intent. getStringExtra("userCompany")

        //Show all of stocking, releasing records
        if (userCompany != null) {
            db.collection("root").document("company")
                .collection("companies").document(userCompany)
                .collection("shelf").get()
                .addOnSuccessListener {
                    itemList.clear()
                    for(document in it){
                        val item = Shelf(document["productShelf"] as String)
                        itemList.add(item)
                    }
                    adapter.notifyDataSetChanged()
                    Log.d(TAG, "Recyclerview success!")
                }
                .addOnFailureListener {
                    Log.d(TAG, "Recyclerview fail!")
                }
        }

        // Click listener
        intent = Intent(this, ShelfManagementActivity::class.java)
        intent.putExtra("userCompany", userCompany)
        adapter.setOnShelfClickListener(object : ShelfListAdapter.OnShelfClickListener{
            override fun onShelfClick(view: View, position: Int) {
                intent.putExtra("productShelf", itemList[position].shelf)
                startActivity(intent)
            }
        })
    }
}