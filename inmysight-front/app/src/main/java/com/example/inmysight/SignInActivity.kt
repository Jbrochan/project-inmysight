package com.example.inmysight

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class SignInActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signin)

        // Move to main activity when press sign-in button
        val signInButton: Button = findViewById(R.id.signInLogInButton)
        signInButton.setOnClickListener {
            intent = Intent(this, LobbyActivity::class.java)
            startActivity(intent)
        }


        // Move to main activity when press close button
        val closeButton: Button = findViewById(R.id.signInCancelButton)
        closeButton.setOnClickListener {
            finish()
        }
    }
}