package com.example.inmysight

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class SignInActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signin)

        // Sign in function

        // Move to main activity when press close button
        val closeButton: Button = findViewById(R.id.cancelButton)
        closeButton.setOnClickListener {
            finish()
        }
    }
}