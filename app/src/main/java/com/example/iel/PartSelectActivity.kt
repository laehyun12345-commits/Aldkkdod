package com.example.iel

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton

class PartSelectActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_part_select)

        val part1Button: MaterialButton = findViewById(R.id.part1Button)
        val part2Button: MaterialButton = findViewById(R.id.part2Button)
        val part3Button: MaterialButton = findViewById(R.id.part3Button)
        val part4Button: MaterialButton = findViewById(R.id.part4Button)
        val favoriteButton: MaterialButton = findViewById(R.id.favoriteButton)

        part1Button.setOnClickListener { openPart(1) }
        part2Button.setOnClickListener { openPart(2) }
        part3Button.setOnClickListener { openPart(3) }
        part4Button.setOnClickListener { openPart(4) }

        // ⭐ 즐겨찾기 버튼 클릭 시 새로운 액티비티로 이동
        favoriteButton.setOnClickListener {
            val intent = Intent(this, FavoriteActivity::class.java)
            startActivity(intent)
        }
    }

    private fun openPart(partNumber: Int) {
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("partNumber", partNumber)
        startActivity(intent)
    }
}
