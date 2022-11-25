package com.example.inmysight

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.inmysight.databinding.ActivityLobbyBinding
import com.google.firebase.firestore.FirebaseFirestore

class LobbyActivity : AppCompatActivity() {
    // Member variables
    private lateinit var binding : ActivityLobbyBinding
    val db = FirebaseFirestore.getInstance()
    val itemList = arrayListOf<Product>()
    val adapter = ListAdapter(itemList)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLobbyBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        Log.d(ContentValues.TAG, "LobbyActivity is started successfully")

        binding.lobbyRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.lobbyRecyclerView.adapter = adapter
        binding.lobbyRecyclerView.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))

        // Variables
        val userCompany = intent.getStringExtra("userCompany")
        Log.d(ContentValues.TAG, "Present userCompany in LobbyActivity is $userCompany")

        // Move to StockActivity when press stock button
        val stockButton: Button = findViewById(R.id.lobbyStockButton)
        stockButton.setOnClickListener {
            intent = Intent(this, StockActivity::class.java)
            intent.putExtra("userCompany", userCompany)
            startActivity(intent)
        }

        // Move to ReleaseActivity when press release button
        val releaseButton: Button = findViewById(R.id.lobbyReleaseButton)
        releaseButton.setOnClickListener {
            intent = Intent(this, ReleaseActivity::class.java)
            intent.putExtra("userCompany", userCompany)
            startActivity(intent)
        }

        val searchButton: Button = findViewById(R.id.lobbySeeButton)
        searchButton.setOnClickListener {
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
    }
}