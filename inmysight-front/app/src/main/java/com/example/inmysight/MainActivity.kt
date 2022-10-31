package com.example.inmysight

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val singInButton: Button = findViewById(R.id.signIn)

        singInButton.setOnClickListener{
            intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
        }
        val signUpButton: Button = findViewById(R.id.signUp)
        signUpButton.setOnClickListener {
            intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
    }
}