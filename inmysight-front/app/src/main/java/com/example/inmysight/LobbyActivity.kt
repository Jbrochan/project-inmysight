package com.example.inmysight

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class LobbyActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lobby)

        // Move to StockActivity when press stock button
        val stockButton: Button = findViewById(R.id.lobbyStockButton)
        intent = Intent(this, StockActivity::class.java)
        startActivity(intent)
    }
}