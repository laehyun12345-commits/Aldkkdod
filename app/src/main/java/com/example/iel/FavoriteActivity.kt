package com.example.iel

import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class FavoriteActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)

        // 뒤로가기 버튼 기능 구현
        val backButton: ImageButton = findViewById(R.id.backButton)
        backButton.setOnClickListener {
            finish() // 현재 액티비티 종료 → 이전 화면으로 돌아감
        }

        val recyclerView: RecyclerView = findViewById(R.id.favoriteRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val favoriteWords = FavoriteManager.getFavorites()
        recyclerView.adapter = WordAdapter(favoriteWords)
    }
}
