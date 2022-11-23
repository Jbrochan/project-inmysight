package com.example.inmysight

import android.content.ContentValues
import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import org.w3c.dom.Text

class StockActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stock)

        // Variables
        val db = Firebase.firestore    // Firebase fire-store database
        val userCompany = intent.getStringExtra("userCompany")
        var productShelf: String = ""    // Product's location
        var productName: String = ""    // Product's name
        var productQuantity: String = ""    // Product's quantity
        var productStockDate: String = ""    // Product's stock date
        var productStockCustomer: String = ""    // Product's stock customer
        var productMemo: String = ""    // Product's memo
        var productAlert: String = ""    // Product's alert quantity

        // Stock to fire-store database when click stock button
        val stockButton: Button = findViewById(R.id.stockInputButton)
        stockButton.setOnClickListener {
            // Store in variables from plain text
            productShelf = findViewById<TextView>(R.id.stockShelfInput).text.toString()
            productName = findViewById<TextView>(R.id.stockNameInput).text.toString()
            productQuantity = findViewById<TextView>(R.id.stockQuantityInput).text.toString()
            productStockDate = findViewById<TextView>(R.id.stockDateInput).text.toString()
            productStockCustomer = findViewById<TextView>(R.id.stockCustomerInput).text.toString()
            productMemo = findViewById<TextView>(R.id.stockMemoInput).text.toString()
            productAlert = findViewById<TextView>(R.id.stockAlertInput).text.toString()

            // Change to hash map for storing in database
            val productData = hashMapOf(
                "productShelf" to productShelf,
                "productName" to productName,
                "productQuantity" to productQuantity,
                "productStockDate" to productStockDate,
                "productStockCustomer" to productStockCustomer,
                "productMemo" to productMemo,
                "productAlert" to productAlert
            )

            // Store in fire-store database from variables
            if (userCompany != null) {
                db.collection("root").document("company")
                    .collection("companies").document(userCompany)
                    .collection("product").document(productName).set(productData)
                    .addOnSuccessListener {
                        Toast.makeText(this, "입고에 성공했습니다", Toast.LENGTH_SHORT).show()
                        findViewById<TextView>(R.id.stockShelfInput).text = ""
                        findViewById<TextView>(R.id.stockNameInput).text = ""
                        findViewById<TextView>(R.id.stockQuantityInput).text = ""
                        findViewById<TextView>(R.id.stockDateInput).text = ""
                        findViewById<TextView>(R.id.stockCustomerInput).text = ""
                        findViewById<TextView>(R.id.stockMemoInput).text = ""
                        findViewById<TextView>(R.id.stockAlertInput).text = ""

                    }
                    .addOnFailureListener{
                        Toast.makeText(this, "입고에 실패하였습니다", Toast.LENGTH_SHORT).show()
                    }
            }
        }


        // Move to lobby activity when press close button
        val closeButton: Button = findViewById(R.id.stockCancelButton)
        closeButton.setOnClickListener{
            finish()
        }

    }
}