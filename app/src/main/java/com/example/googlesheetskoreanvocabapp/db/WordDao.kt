package com.example.googlesheetskoreanvocabapp.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface WordDao {
    @Query("SELECT * FROM words WHERE type = :wordType")
    fun getWordPairs(wordType: String): List<WordPairs>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWordPairs(wordPairs: List<WordPairs>)
}