package com.example.inmysight

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

class ReleaseActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_release)

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

        // Set releaseDateInput's text to today's date
        // now() method require min API level 26
        findViewById<TextView>(R.id.releaseDateInput).text = LocalDate.now().toString()

        // Release from fire-store database when click release button
        val releaseButton: Button = findViewById(R.id.releaseInputButton)
        releaseButton.setOnClickListener {
            // Release from variables from plain text
            productShelf = findViewById<TextView>(R.id.releaseShelfInput).text.toString()
            productName = findViewById<TextView>(R.id.releaseNameInput).text.toString()
            productQuantity = findViewById<TextView>(R.id.releaseQuantityInput).text.toString()
            productStockDate = findViewById<TextView>(R.id.releaseDateInput).text.toString()
            productStockCustomer = findViewById<TextView>(R.id.releaseCustomerInput).text.toString()
            productMemo = findViewById<TextView>(R.id.releaseMemoInput).text.toString()
            productAlert = findViewById<TextView>(R.id.releaseAlertInput).text.toString()

            // Release from fire-store database from variables
            if (userCompany != null) {
                // Get data from fire-store database
                db.collection("root").document("company")
                    .collection("companies").document(userCompany)
                    .collection("product").document(productName).get()
                    .addOnSuccessListener {
                        // Calculate change of quantity
                        var presentQuantity: Int = it.get("productQuantity").toString().toInt()
                        var finalQuantity: String = (presentQuantity - productQuantity.toInt()).toString()
                        Log.d(TAG, "presentQuantity : $presentQuantity")
                        Log.d(TAG, "finalQuantity : $finalQuantity")

                        // Release product if quantity of product is not zero
                        if(finalQuantity.toInt() > 0){
                            // Change to hash map for storing in database
                            val productData = hashMapOf(
                                "productShelf" to productShelf,
                                "productName" to productName,
                                "productQuantity" to finalQuantity,
                                "productReleaseDate" to productStockDate,
                                "productReleaseCustomer" to productStockCustomer,
                                "productMemo" to productMemo,
                                "productAlert" to productAlert
                            )
                            db.collection("root").document("company")
                                .collection("companies").document(userCompany)
                                .collection("product").document(productName).set(productData)
                                .addOnSuccessListener {
                                    Log.d(TAG, "${productName}, ${productQuantity}개 출고 성공")
                                    Toast.makeText(this, "출고에 성공했습니다", Toast.LENGTH_SHORT).show()
                                }
                                .addOnFailureListener{
                                    Log.d(TAG, "${productName}, ${productQuantity}개 출고 실패")
                                    Toast.makeText(this, "출고에 실패하였습니다", Toast.LENGTH_SHORT).show()
                                }

                        }
                        // If quantity of product become 0, delete product in data base
                        else if(finalQuantity.toInt() ==0){
                            db.collection("root").document("company")
                                .collection("companies").document(userCompany)
                                .collection("product").document(productName).delete()
                        }
                        // Require of releasing quantity is
                        else{
                            Toast.makeText(this, "잔여 수량을 초과하였습니다\n다시 작성 해주세요", Toast.LENGTH_LONG).show()
                        }

                        // Send Alarm to user that product is re-ordered
                        if(finalQuantity.toInt() <= it.get("productAlarm").toString().toInt()){
                            Toast.makeText(this, "출고 후, 상품의 잔여량이 부족합니다\n재주문 해주세요", Toast.LENGTH_LONG).show()
                        }

                        // Set input space to initial value
                        findViewById<TextView>(R.id.releaseShelfInput).text = ""
                        findViewById<TextView>(R.id.releaseNameInput).text = ""
                        findViewById<TextView>(R.id.releaseQuantityInput).text = ""
                        findViewById<TextView>(R.id.releaseDateInput).text = LocalDate.now().toString()
                        findViewById<TextView>(R.id.releaseCustomerInput).text = ""
                        findViewById<TextView>(R.id.releaseMemoInput).text = ""
                        findViewById<TextView>(R.id.releaseAlertInput).text = ""
                    }
                    .addOnFailureListener{
                        Toast.makeText(this, "출고에 실패하였습니다", Toast.LENGTH_SHORT).show()
                    }
            }
        }

        // Move to lobby activity when press close button
        val closeButton: Button = findViewById(R.id.releaseCancelButton)
        closeButton.setOnClickListener{
            finish()
        }
    }
}