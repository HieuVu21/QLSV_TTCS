
package com.map.vuminhhieu.btl_ttcs

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity

class MenuActivity : ComponentActivity() {
    private var token: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_menu)

        // Get token from intent
        token = intent.getStringExtra("token")

        // Find the "Kết quả học tập" button
        val scoreButton = findViewById<Button>(R.id.ketquahoctap)

        // Set click listener for the score button
        scoreButton.setOnClickListener {
            // Navigate to ScoreActivity with token
            val intent = Intent(this, ScoreActivity::class.java)
            intent.putExtra("token", token)
            startActivity(intent)
        }


    }


}