package com.example.inmysight

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.inmysight.databinding.ActivityManagementBinding
import com.google.firebase.firestore.FirebaseFirestore

class ManagementActivity : AppCompatActivity() {
    // Member variables
    private lateinit var binding : ActivityManagementBinding
    private val db = FirebaseFirestore.getInstance()
    private var itemList = arrayListOf<Product>()
    private var adapter = ListAdapter(itemList)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityManagementBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        Log.d(TAG, "LobbyActivity is started successfully")

        binding.managementRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.managementRecyclerView.adapter = adapter
        binding.managementRecyclerView.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))

        // Variables
        val userCompany = intent.getStringExtra("userCompany")
        Log.d(TAG, "Present userCompany in LobbyActivity is $userCompany")

        if (userCompany != null) {
            db.collection("root").document("company")
                .collection("companies").document(userCompany)
                .collection("product").get()
                .addOnSuccessListener {
                    itemList.clear()
                    for(document in it){
                        val item = Product(document["productShelf"] as String, document["productName"] as String, document["productQuantity"] as String)
                        itemList.add(item)
                        Log.d(TAG, "${item.shelf}, ${item.name}, ${item.quantity}")
                    }
                    adapter.notifyDataSetChanged()
                    Log.d(TAG, "Recyclerview success!")
                }
                .addOnFailureListener {
                    Log.d(TAG, "Recyclerview fail!")
                }
        }

        // Move to StockActivity when press stock button
        val stockButton: Button = findViewById(R.id.managementStockButton)
        stockButton.setOnClickListener {
            intent = Intent(this, StockActivity::class.java)
            intent.putExtra("userCompany", userCompany)
            startActivity(intent)
        }

        // Move to ReleaseActivity when press release button
        val releaseButton: Button = findViewById(R.id.managementReleaseButton)
        releaseButton.setOnClickListener {
            intent = Intent(this, ReleaseActivity::class.java)
            intent.putExtra("userCompany", userCompany)
            startActivity(intent)
        }

        // Show list of products through recycler view
        val showButton: Button = findViewById(R.id.managementSeeButton)
        showButton.setOnClickListener {
            if (userCompany != null) {
                db.collection("root").document("company")
                    .collection("companies").document(userCompany)
                    .collection("product").get()
                    .addOnSuccessListener {
                        itemList.clear()
                        for(document in it){
                            val item = Product(document["productShelf"] as String, document["productName"] as String, document["productQuantity"] as String)
                            itemList.add(item)
                            Log.d(TAG, "${item.shelf}, ${item.name}, ${item.quantity}")
                        }
                        adapter.notifyDataSetChanged()
                        Log.d(TAG, "Recyclerview success!")
                    }
                    .addOnFailureListener {
                        Log.d(TAG, "Recyclerview fail!")
                    }
            }
        }

        // Search product that user want to find
        val searchButton: Button = findViewById(R.id.managementSearchButton)
        searchButton.setOnClickListener {
            val product = binding.managementSearchInput.text.toString()
            if (userCompany != null) {
                db.collection("root").document("company")
                    .collection("companies").document(userCompany)
                    .collection("product").document(product)
                    .get().addOnSuccessListener {
                        val builder = AlertDialog.Builder(this)
                        builder.setTitle("재고 찾기")
                            .setMessage("재고 위치 : ${it.get("productShelf")}\n" +
                                    "재고 이름 : ${it.get("productName")}\n" +
                                    "재고 수량 : ${it.get("productQuantity")}")
                            .setPositiveButton("확인") { _, _ ->
                                // Underscore for unused variables
                            }
                        builder.create()
                        builder.show()
                        Log.d(TAG, "productQuantity : ${it.get("productQuantity")}, productName : ${it.get("productName")}")
                        // If there are no product such that name, return null
                        // Have to make null-checking part
                    }.addOnFailureListener {
                        Log.d(TAG, "Fail to read from database")
                    }
            }
        }

        // Re-order item list compared by its shelf
        val shelfText: TextView = findViewById(R.id.managementCategoryShelf)
        shelfText.setOnClickListener{
            // Set comparator of Product class compared by its shelf
            val comparator: Comparator<Product> = compareBy{
                it.shelf
            }
            // Re-order
            val newItemList = itemList.sortedWith(comparator)
            itemList.clear()
            for(item in newItemList){
                itemList.add(item)
            }
            adapter.notifyDataSetChanged()
            Log.d(TAG, "Re-set by order of shelf")
            Toast.makeText(this, "선반순으로 정렬하였습니다.", Toast.LENGTH_SHORT).show()
        }

        // Re-order item list compared by its name
        val nameText: TextView = findViewById(R.id.managementCategoryName)
        nameText.setOnClickListener{
            // Set comparator of Product class compared by its name
            val comparator: Comparator<Product> = compareBy {
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

        // Re-order item list compared by its quantity
        val quantityText: TextView = findViewById(R.id.managementCategoryQuantity)
        quantityText.setOnClickListener{
            // Set comparator of Product class compared by its name
            val comparator: Comparator<Product> = compareBy {
                it.quantity.toInt()
            }
            // Re-order
            val newItemList = itemList.sortedWith(comparator)
            itemList.clear()
            for(item in newItemList){
                itemList.add(item)
            }
            adapter.notifyDataSetChanged()
            Log.d(TAG, "Re-set by order of quantity")
            Toast.makeText(this, "재고수량순으로 정렬하였습니다.", Toast.LENGTH_SHORT).show()
        }

        // Show custom dialogue when click product in recycler view
        val recyclerView: RecyclerView = findViewById(R.id.managementRecyclerView)
        recyclerView.setOnClickListener{
            Log.d(TAG, "리사이클러 클릭 확인")
        }
    }
}