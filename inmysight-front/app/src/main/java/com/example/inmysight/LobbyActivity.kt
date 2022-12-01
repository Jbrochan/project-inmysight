package com.example.inmysight

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class LobbyActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lobby)

        // Variables
        val userCompany = intent.getStringExtra("userCompany")

        // Move to ManagementActivity when press management button
        val managementButton: Button = findViewById(R.id.lobbyManagementButton)
        managementButton.setOnClickListener {
            intent = Intent(this, ManagementActivity::class.java)
            intent.putExtra("userCompany", userCompany)
            startActivity(intent)
        }
    }
}