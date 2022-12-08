package com.example.inmysight

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Move to sign-in activity when press sign-in button
        val singInButton: Button = findViewById(R.id.mainSignInButton)
        singInButton.setOnClickListener{
            intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
        }
        // Move to sign-up activity when press sign-up button
        val signUpButton: Button = findViewById(R.id.mainSignUpButton)
        signUpButton.setOnClickListener {
            intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
    }
}
