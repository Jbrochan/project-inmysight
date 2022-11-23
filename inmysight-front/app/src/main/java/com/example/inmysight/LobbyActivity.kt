package com.example.inmysight

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast

class LobbyActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lobby)

        // Variables
        var userCompany = intent.getStringExtra("userCompany")

        // Move to StockActivity when press stock button
        val stockButton: Button = findViewById(R.id.lobbyStockButton)
        stockButton.setOnClickListener {
            intent = Intent(this, StockActivity::class.java)
            intent.putExtra("userCompany", userCompany)
            startActivity(intent)
        }

        // Move to ReleaseActivity when press release button
        val releaseButton: Button = findViewById(R.id.lobbyReleaseButton)
        releaseButton.setOnClickListener {
            intent = Intent(this, ReleaseActivity::class.java)
            intent.putExtra("userCompany", userCompany)
            startActivity(intent)
        }
    }
}