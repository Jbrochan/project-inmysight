package com.example.inmysight

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlin.math.sign

class SignUpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        // Variables
        // User id
        var userId: String = ""
        // User password
        var userPassword: String = ""
        // User's company
        var userCompany: String = ""


        // Sign up function
        val signUpButton: Button = findViewById(R.id.signUpLogInButton)
        signUpButton.setOnClickListener {
            // Store user id, password at variables from plain text
            userId = findViewById<TextView>(R.id.signUpIdInput).text.toString()
            userPassword = findViewById<TextView>(R.id.signUpPasswordInput).text.toString()
            userCompany = findViewById<TextView>(R.id.signUpCompanyInput).text.toString()


            // Send user's id, password, company to database
            // If sign up success, send toast
            Toast.makeText(applicationContext, "ID : $userId \nPassword : $userPassword \nCompany : $userCompany",Toast.LENGTH_SHORT).show()
        }

        // Move to main activity when press close button
        val closeButton: Button = findViewById(R.id.signUpCancelButton)
        closeButton.setOnClickListener{
            finish()
        }
    }
}
