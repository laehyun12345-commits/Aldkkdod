package com.example.iel

object FavoriteManager {
    private val favorites = mutableListOf<Word>()

    fun addFavorite(word: Word) {
        if (!favorites.contains(word)) {
            favorites.add(word)
        }
    }

    fun removeFavorite(word: Word) {
        favorites.remove(word)
    }

    fun getFavorites(): List<Word> {
        return favorites
    }

    fun isFavorite(word: Word): Boolean {
        return favorites.contains(word)
    }
}
