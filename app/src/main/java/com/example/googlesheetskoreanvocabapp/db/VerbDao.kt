package com.example.googlesheetskoreanvocabapp.db

import androidx.room.*

@Dao
interface VerbDao {
    @Query("SELECT * FROM verbs")
    suspend fun getVerbs(): List<Verbs>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVerbs(wordPairs: List<Verbs>)

    @Delete
    suspend fun deleteVerb(verb: Verbs)

    @Query("SELECT * FROM adverbs")
    suspend fun getAdverbs(): List<Adverbs>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAdverbs(wordPairs: List<Adverbs>)

    @Delete
    suspend fun deleteAdverb(adverbs: Adverbs)

    @Query("SELECT * FROM nouns")
    suspend fun getNouns(): List<Nouns>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNouns(wordPairs: List<Nouns>)

    @Delete
    suspend fun deleteNouns(nouns: Nouns)

    @Query("SELECT * FROM positions")
    suspend fun getPositions(): List<Positions>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPositions(wordPairs: List<Positions>)

    @Delete
    suspend fun deletePositions(positions: Positions)

    @Query("SELECT * FROM phrases")
    suspend fun getPhrases(): List<Phrases>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPhrases(wordPairs: List<Phrases>)

    @Delete
    suspend fun deletePhrases(phrases: Phrases)

    @Query("SELECT * FROM sentences")
    suspend fun getSentences(): List<Sentences>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSentences(wordPairs: List<Sentences>)

    @Delete
    suspend fun deleteSentences(sentences: Sentences)

}