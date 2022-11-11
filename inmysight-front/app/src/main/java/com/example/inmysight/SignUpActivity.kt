package com.example.inmysight

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class SignUpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        // Sign up function

        // Move to main activity when press close button
        val closeButton: Button = findViewById(R.id.signUpCancelButton)
        closeButton.setOnClickListener{
            finish()
        }
    }
}
