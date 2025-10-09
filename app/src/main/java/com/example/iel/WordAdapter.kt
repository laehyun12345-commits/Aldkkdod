package com.example.iel

import android.speech.tts.TextToSpeech
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.util.*

class WordAdapter(private val wordList: List<Word>) :
    RecyclerView.Adapter<WordAdapter.WordViewHolder>() {

    private var tts: TextToSpeech? = null

    class WordViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val wordNumber: TextView = itemView.findViewById(R.id.wordNumber)
        val wordText: TextView = itemView.findViewById(R.id.wordText)
        val meaningText: TextView = itemView.findViewById(R.id.meaningText)
        val favoriteButton: ImageButton = itemView.findViewById(R.id.favoriteButton)
        val soundButton: ImageButton = itemView.findViewById(R.id.soundButton)
        val expandButton: ImageButton = itemView.findViewById(R.id.expandButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_word, parent, false)
        return WordViewHolder(view)
    }

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        val word = wordList[position]
        holder.wordNumber.text = "단어 ${position + 1}"
        holder.wordText.text = word.word
        holder.meaningText.text = word.meaning
        holder.meaningText.visibility = View.GONE // 기본은 뜻 숨김

        // 펼치기/접기 버튼
        holder.itemView.setOnClickListener {
            if (holder.meaningText.visibility == View.GONE) {
                holder.meaningText.visibility = View.VISIBLE
                holder.expandButton.setImageResource(R.drawable.ic_arrow_up)
            } else {
                holder.meaningText.visibility = View.GONE
                holder.expandButton.setImageResource(R.drawable.ic_arrow_down)
            }
        }

        // ✅ 즐겨찾기 상태에 따라 아이콘 설정 (기본 Android 아이콘 사용)
        if (FavoriteManager.isFavorite(word)) {
            holder.favoriteButton.setImageResource(android.R.drawable.btn_star_big_on) // 채운 별
        } else {
            holder.favoriteButton.setImageResource(android.R.drawable.btn_star_big_off) // 빈 별
        }

        // ✅ 즐겨찾기 버튼 클릭 이벤트
        holder.favoriteButton.setOnClickListener {
            if (FavoriteManager.isFavorite(word)) {
                FavoriteManager.removeFavorite(word)
                holder.favoriteButton.setImageResource(android.R.drawable.btn_star_big_off)
            } else {
                FavoriteManager.addFavorite(word)
                holder.favoriteButton.setImageResource(android.R.drawable.btn_star_big_on)
            }
        }

        // 발음 버튼
        holder.soundButton.setOnClickListener {
            val textToSpeak = word.word
            tts?.speak(textToSpeak, TextToSpeech.QUEUE_FLUSH, null, null)
        }
    }

    override fun getItemCount(): Int = wordList.size

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        tts = TextToSpeech(recyclerView.context) { status ->
            if (status == TextToSpeech.SUCCESS) {
                tts?.language = Locale.US
            }
        }
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        tts?.stop()
        tts?.shutdown()
    }
}
