package com.example.inmysight.ui.record

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.inmysight.databinding.ActivityRecordBinding
import com.example.inmysight.model.Record
import com.google.firebase.firestore.FirebaseFirestore

class RecordActivity : AppCompatActivity() {
    // Member variables
    private lateinit var binding: ActivityRecordBinding
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

        // Re-order record list compared by its kind of trade
        val tradeText: TextView = binding.recordCategoryTrade
        tradeText.setOnClickListener{
            // Set comparator of Record class compared by its shelf
            val comparator: Comparator<Record> = compareBy {
                it.trade
            }
            // Re-order
            val newItemList = itemList.sortedWith(comparator)
            itemList.clear()
            for(item in newItemList){
                itemList.add(item)
            }
            adapter.notifyDataSetChanged()
            Log.d(TAG, "Re-set by order of trade")
            Toast.makeText(this, "거래종류순으로 정렬하였습니다.", Toast.LENGTH_SHORT).show()
        }

        // Re-order record list compared by its kind of name
        val nameText: TextView = binding.recordCategoryName
        nameText.setOnClickListener{
            // Set comparator of Record class compared by its shelf
            val comparator: Comparator<Record> = compareBy {
                it.name
            }
            // Re-order
            val newItemList = itemList.sortedWith(comparator)
            itemList.clear()
            for(item in newItemList){
                itemList.add(item)
            }
            adapter.notifyDataSetChanged()
            Log.d(TAG, "Re-set by order of name")
            Toast.makeText(this, "이름순으로 정렬하였습니다.", Toast.LENGTH_SHORT).show()
        }

        // Re-order record list compared by its kind of quantity
        val quantityText: TextView = binding.recordCategoryQuantity
        quantityText.setOnClickListener{
            // Set comparator of Record class compared by its shelf
            val comparator: Comparator<Record> = compareBy {
                it.quantity
            }
            // Re-order
            val newItemList = itemList.sortedWith(comparator)
            itemList.clear()
            for(item in newItemList){
                itemList.add(item)
            }
            adapter.notifyDataSetChanged()
            Log.d(TAG, "Re-set by order of quantity")
            Toast.makeText(this, "거래수량순으로 정렬하였습니다.", Toast.LENGTH_SHORT).show()
        }

        // Re-order record list compared by its kind of customer
        val customerText: TextView = binding.recordCategoryCustomer
        customerText.setOnClickListener{
            // Set comparator of Record class compared by its shelf
            val comparator: Comparator<Record> = compareBy {
                it.customer
            }
            // Re-order
            val newItemList = itemList.sortedWith(comparator)
            itemList.clear()
            for(item in newItemList){
                itemList.add(item)
            }
            adapter.notifyDataSetChanged()
            Log.d(TAG, "Re-set by order of customer")
            Toast.makeText(this, "거래처순으로 정렬하였습니다.", Toast.LENGTH_SHORT).show()
        }

        // Re-order record list compared by its kind of date
        val dateText: TextView = binding.recordCategoryDate
        dateText.setOnClickListener{
            // Set comparator of Record class compared by its shelf
            val comparator: Comparator<Record> = compareBy {
                it.date
            }
            // Re-order
            val newItemList = itemList.sortedWith(comparator)
            itemList.clear()
            for(item in newItemList){
                itemList.add(item)
            }
            adapter.notifyDataSetChanged()
            Log.d(TAG, "Re-set by order of date")
            Toast.makeText(this, "거래일순으로 정렬하였습니다.", Toast.LENGTH_SHORT).show()
        }
    }
}