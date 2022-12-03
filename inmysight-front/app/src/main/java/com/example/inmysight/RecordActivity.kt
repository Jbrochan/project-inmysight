package com.example.inmysight

import android.content.ContentValues.TAG
import android.os.Binder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.inmysight.databinding.ActivityRecordBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class RecordActivity : AppCompatActivity() {
    // Member variables
    private  lateinit var binding: ActivityRecordBinding
    private val db = FirebaseFirestore.getInstance()
    private var itemList = arrayListOf<Record>()
    private var adapter = RecordListAdapter(itemList)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecordBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        Log.d(TAG, "RecordActivity is started successfully")

        binding.recordRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.recordRecyclerView.adapter = adapter
        binding.recordRecyclerView.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))

        // Variables
        val userCompany = intent.getStringExtra("userCompany")

        //Show all of stocking, releasing records
        if (userCompany != null) {
            db.collection("root").document("company")
                .collection("companies").document(userCompany)
                .collection("records").get()
                .addOnSuccessListener {
                    itemList.clear()
                    for(document in it){
                        val item = Record(document["productTrade"] as String, document["productName"] as String, document["productQuantity"] as String
                            , document["productCustomer"] as String, document["productDate"] as String)
                        itemList.add(item)
                    }
                    adapter.notifyDataSetChanged()
                    Log.d(TAG, "Recyclerview success!")
                }
                .addOnFailureListener {
                    Log.d(TAG, "Recyclerview fail!")
                }
        }

        // Show all of stocking, releasing records when press show-all button
        val showAllButton: Button = binding.recordShowAllButton
        showAllButton.setOnClickListener {
            if (userCompany != null) {
                db.collection("root").document("company")
                    .collection("companies").document(userCompany)
                    .collection("records").get()
                    .addOnSuccessListener {
                        itemList.clear()
                        for(document in it){
                            val item = Record(document["productTrade"] as String, document["productName"] as String, document["productQuantity"] as String
                                , document["productCustomer"] as String, document["productDate"] as String)
                            itemList.add(item)
                        }
                        adapter.notifyDataSetChanged()
                        Log.d(TAG, "Recyclerview success!")
                    }
                    .addOnFailureListener {
                        Log.d(TAG, "Recyclerview fail!")
                    }
            }
        }

        // Show records of user input when press search button
        val searchButton: Button = binding.recordSearchButton
        searchButton.setOnClickListener {
            val product = binding.recordSearchInput.text.toString()
            if (userCompany != null) {
                db.collection("root").document("company")
                    .collection("companies").document(userCompany)
                    .collection("records").get()
                    .addOnSuccessListener {
                        itemList.clear()
                        for(document in it){
                            val item = Record(document["productTrade"] as String, document["productName"] as String, document["productQuantity"] as String
                                , document["productCustomer"] as String, document["productDate"] as String)
                            if(item.name == product){
                                itemList.add(item)
                            }
                        }
                        adapter.notifyDataSetChanged()
                        Log.d(TAG, "Recyclerview success!")
                    }
                    .addOnFailureListener {
                        Log.d(TAG, "Recyclerview fail!")
                    }
            }
        }
    }
}