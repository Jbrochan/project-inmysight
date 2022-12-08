package com.example.inmysight

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.time.LocalDate

class StockActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stock)
        Log.d(ContentValues.TAG, "StockActivity is started successfully")

        // Variables
        val db = Firebase.firestore    // Firebase fire-store database
        val userCompany = intent.getStringExtra("userCompany")
        var productShelf: String    // Product's location
        var productName: String    // Product's name
        var productQuantity: String    // Product's quantity
        var productStockDate: String    // Product's stock date
        var productStockCustomer: String    // Product's stock customer
        var productMemo: String    // Product's memo
        var productAlert: String    // Product's alert quantity
        Log.d(ContentValues.TAG, "Present userCompany in StockActivity is $userCompany")

        // Set stockDateInput's text to today's date
        // now() method require min API level 26
        findViewById<TextView>(R.id.stockDateInput).text = LocalDate.now().toString()

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
            var productData = hashMapOf(
                "productShelf" to productShelf,
                "productName" to productName,
                "productQuantity" to productQuantity,
                "productStockDate" to productStockDate,
                "productStockCustomer" to productStockCustomer,
                "productMemo" to productMemo,
                "productAlert" to productAlert
            )
            val stockRecordData = hashMapOf(
                "productTrade" to "입고",
                "productName" to productName,
                "productCustomer" to productStockCustomer,
                "productQuantity" to productQuantity,
                "productDate" to productStockDate
            )
            val shelfData = hashMapOf(
                "productShelf" to productShelf
            )
            var productNameCheck = true
            var presentQuantity: Int
            lateinit var finalQuantity: String

            // Store in fire-store database from variables
            if (userCompany != null) {
                // Checking duplication
                db.collection("root").document("company")
                    .collection("companies").document(userCompany)
                    .collection("product").get()
                    .addOnSuccessListener {
                        for(document in it){
                            if(productName == document.get("productName")){
                                presentQuantity = document.get("productQuantity").toString().toInt()
                                finalQuantity = (presentQuantity + productQuantity.toInt()).toString()
                                productNameCheck = false
                                Log.d(TAG, "입고시 중복 제품 발견, 최종수량 : $presentQuantity + ${productQuantity.toInt()} = $finalQuantity")
                                Log.d(TAG, "입고시 중복 제품 발견, 제품이름 ${document.get("productName")}")
                            }
                        }

                        // Store product
                        // If there are no duplication
                        if(productNameCheck){
                            db.collection("root").document("company")
                                .collection("companies").document(userCompany)
                                .collection("product").document(productName).set(productData)
                                .addOnSuccessListener {
                                    Toast.makeText(this, "입고에 성공했습니다.", Toast.LENGTH_LONG).show()
                                    findViewById<TextView>(R.id.stockShelfInput).text = ""
                                    findViewById<TextView>(R.id.stockNameInput).text = ""
                                    findViewById<TextView>(R.id.stockQuantityInput).text = ""
                                    findViewById<TextView>(R.id.stockDateInput).text = LocalDate.now().toString()
                                    findViewById<TextView>(R.id.stockCustomerInput).text = ""
                                    findViewById<TextView>(R.id.stockMemoInput).text = ""
                                    findViewById<TextView>(R.id.stockAlertInput).text = ""
                                }
                                .addOnFailureListener{
                                    Toast.makeText(this, "입고에 실패하였습니다.", Toast.LENGTH_LONG).show()
                                }
                        }
                        // If there are duplication
                        else{
                            productData = hashMapOf(
                                "productShelf" to productShelf,
                                "productName" to productName,
                                "productQuantity" to finalQuantity,
                                "productStockDate" to productStockDate,
                                "productStockCustomer" to productStockCustomer,
                                "productMemo" to productMemo,
                                "productAlert" to productAlert
                            )
                            db.collection("root").document("company")
                                .collection("companies").document(userCompany)
                                .collection("product").document(productName).set(productData)
                                .addOnSuccessListener {
                                    Toast.makeText(this, "입고에 성공했습니다.", Toast.LENGTH_LONG).show()
                                    findViewById<TextView>(R.id.stockShelfInput).text = ""
                                    findViewById<TextView>(R.id.stockNameInput).text = ""
                                    findViewById<TextView>(R.id.stockQuantityInput).text = ""
                                    findViewById<TextView>(R.id.stockDateInput).text = LocalDate.now().toString()
                                    findViewById<TextView>(R.id.stockCustomerInput).text = ""
                                    findViewById<TextView>(R.id.stockMemoInput).text = ""
                                    findViewById<TextView>(R.id.stockAlertInput).text = ""
                                }
                                .addOnFailureListener{
                                    Toast.makeText(this, "입고에 실패하였습니다.", Toast.LENGTH_LONG).show()
                                }
                        }

                    }


                // Store record
                db.collection("root").document("company")
                    .collection("companies").document(userCompany)
                    .collection("records").add(stockRecordData).addOnSuccessListener {
                        Log.d(TAG, "입고 내역 기록 완료")
                    }
                    .addOnFailureListener {
                        Log.d(TAG, "입고 내역 기록 실패")
                    }

                // Store shelf
                db.collection("root").document("company")
                    .collection("companies").document(userCompany)
                    .collection("shelf").document(productShelf).set(shelfData)
                    .addOnSuccessListener {
                        Log.d(TAG, "선반 기록 완료")
                    }
            }
        }

        // Move to lobby activity when press close button
        val closeButton: Button = findViewById(R.id.stockCancelButton)
        closeButton.setOnClickListener{
            finish()
            Log.d(ContentValues.TAG, "StockActivity is canceled successfully")
        }
    }
}