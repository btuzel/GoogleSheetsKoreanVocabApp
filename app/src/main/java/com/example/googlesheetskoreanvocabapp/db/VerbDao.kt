package com.example.googlesheetskoreanvocabapp.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface VerbDao {
    @Query("SELECT * FROM verbs")
    suspend fun getVerbs(): List<Verbs>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVerbs(wordPairs: List<Verbs>)

    @Query("SELECT * FROM adverbs")
    suspend fun getAdverbs(): List<Adverbs>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAdverbs(wordPairs: List<Adverbs>)

    @Query("SELECT * FROM nouns")
    suspend fun getNouns(): List<Nouns>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNouns(wordPairs: List<Nouns>)

    @Query("SELECT * FROM positions")
    suspend fun getPositions(): List<Positions>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPositions(wordPairs: List<Positions>)

    @Query("SELECT * FROM phrases")
    suspend fun getPhrases(): List<Phrases>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPhrases(wordPairs: List<Phrases>)

    @Query("SELECT * FROM sentences")
    suspend fun getSentences(): List<Sentences>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSentences(wordPairs: List<Sentences>)

}