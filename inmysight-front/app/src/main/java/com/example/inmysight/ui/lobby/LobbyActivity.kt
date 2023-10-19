package com.example.inmysight.ui.lobby

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.inmysight.R
import com.example.inmysight.ui.record.RecordActivity
import com.example.inmysight.ui.shelf.ShelfActivity
import com.example.inmysight.ui.management.ManagementActivity

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

        // Move to RecordActivity when press record button
        val recordButton: Button = findViewById(R.id.lobbyRecordButton)
        recordButton.setOnClickListener {
            intent = Intent(this, RecordActivity::class.java)
            intent.putExtra("userCompany", userCompany)
            startActivity(intent)
        }

        // Move to ShelfActivity when press record button
        val shelfButton: Button = findViewById(R.id.lobbyShelfButton)
        shelfButton.setOnClickListener {
            intent = Intent(this, ShelfActivity::class.java)
            intent.putExtra("userCompany", userCompany)
            startActivity(intent)
        }
    }
}