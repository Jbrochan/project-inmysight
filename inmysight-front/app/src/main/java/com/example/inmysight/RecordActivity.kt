package com.example.inmysight

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class RecordActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_record)

        // Variables
        val userCompany = intent.getStringExtra("userCompany")
    }
}