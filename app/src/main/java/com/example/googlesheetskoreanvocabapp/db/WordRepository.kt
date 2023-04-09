package com.example.googlesheetskoreanvocabapp.db

class WordRepository(private val wordDao: WordDao) {
    fun getWordPairs(wordType: String): List<WordPairs> {
        return wordDao.getWordPairs(wordType)
    }

    suspend fun insertWordPairs(wordPairs: List<WordPairs>) {
        wordDao.insertWordPairs(wordPairs)
    }
}