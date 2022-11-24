package com.example.inmysight

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class SignInActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signin)
        Log.d(TAG, "SignInActivity is started successfully")

        // Variables
        val db = Firebase.firestore
        var userId: String    // User id
        var userPassword: String    // User password
        var userCompany: String    // User's company


        // Move to main activity when press sign-in button
        val signInButton: Button = findViewById(R.id.signInLogInButton)
        signInButton.setOnClickListener {
            // Store user id, password, company at variables from plain text
            userId = findViewById<TextView>(R.id.signInIdInput).text.toString()
            userPassword = findViewById<TextView>(R.id.signInPasswordInput).text.toString()
            userCompany = findViewById<TextView>(R.id.signInCompanyInput).text.toString()

            // Validate user's id, password, company from database
            db.collection("root").document("company")
                .collection("companies").document(userCompany)
                .collection("users").document(userId).get()
                .addOnSuccessListener {documentSnapshot ->
                    // Log-in success when validation passed
                    if(userPassword == documentSnapshot["userPassword"]){
                        intent = Intent(this, LobbyActivity::class.java)
                        intent.putExtra("userCompany", userCompany)
                        startActivity(intent)
                        Log.d(TAG, "Log in success!!")
                        Toast.makeText(this, "로그인에 성공하였습니다.", Toast.LENGTH_SHORT).show()
                    }
                    else {
                        Log.d(TAG, "Log in fail!!")
                        Toast.makeText(this, "정보를 다시 입력해주세요.", Toast.LENGTH_SHORT).show()
                    }
                }
                .addOnFailureListener{
                    Log.d(TAG, "Log in fail!!")
                    Toast.makeText(this, "정보를 다시 입력해주세요.", Toast.LENGTH_SHORT).show()
                }
        }


        // Move to main activity when press close button
        val closeButton: Button = findViewById(R.id.signInCancelButton)
        closeButton.setOnClickListener {
            finish()
            Log.d(TAG, "SignInActivity is canceled successfully")
        }
    }
}