package com.example.inmysight

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class StockActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stock)


        // Move to lobby activity when press close button
        val closeButton: Button = findViewById(R.id.stockCancelButton)
        closeButton.setOnClickListener{
            finish()
        }

    }
}