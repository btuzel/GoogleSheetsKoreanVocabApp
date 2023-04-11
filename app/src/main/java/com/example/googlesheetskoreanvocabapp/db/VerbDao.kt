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

    @Transaction
    suspend fun deleteVerbByEnglishWord(verb: Verbs) {
        deleteVerbByEnglishWord(verb.englishWord)
    }

    @Query("DELETE FROM verbs WHERE englishWord = :englishWord")
    suspend fun deleteVerbByEnglishWord(englishWord: String)

    @Query("SELECT * FROM adverbs")
    suspend fun getAdverbs(): List<Adverbs>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAdverbs(wordPairs: List<Adverbs>)

    @Delete
    suspend fun deleteAdverb(adverbs: Adverbs)

    @Transaction
    suspend fun deleteAdverbByEnglishWord(adverbs: Adverbs) {
        deleteAdverbByEnglishWord(adverbs.englishWord)
    }

    @Query("DELETE FROM adverbs WHERE englishWord = :englishWord")
    suspend fun deleteAdverbByEnglishWord(englishWord: String)

    @Query("SELECT * FROM nouns")
    suspend fun getNouns(): List<Nouns>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNouns(wordPairs: List<Nouns>)

    @Delete
    suspend fun deleteNouns(nouns: Nouns)

    @Transaction
    suspend fun deleteNounByEnglishWord(nouns: Nouns) {
        deleteNounByEnglishWord(nouns.englishWord)
    }

    @Query("DELETE FROM nouns WHERE englishWord = :englishWord")
    suspend fun deleteNounByEnglishWord(englishWord: String)

    @Query("SELECT * FROM positions")
    suspend fun getPositions(): List<Positions>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPositions(wordPairs: List<Positions>)

    @Delete
    suspend fun deletePositions(positions: Positions)

    @Transaction
    suspend fun deletePositionsByEnglishWord(positions: Positions) {
        deletePositionsByEnglishWord(positions.englishWord)
    }

    @Query("DELETE FROM positions WHERE englishWord = :englishWord")
    suspend fun deletePositionsByEnglishWord(englishWord: String)

    @Query("SELECT * FROM phrases")
    suspend fun getPhrases(): List<Phrases>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPhrases(wordPairs: List<Phrases>)

    @Delete
    suspend fun deletePhrases(phrases: Phrases)

    @Transaction
    suspend fun deletePhrasesByEnglishWord(phrases: Phrases) {
        deletePhrasesByEnglishWord(phrases.englishWord)
    }

    @Query("DELETE FROM phrases WHERE englishWord = :englishWord")
    suspend fun deletePhrasesByEnglishWord(englishWord: String)

    @Query("SELECT * FROM sentences")
    suspend fun getSentences(): List<Sentences>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSentences(wordPairs: List<Sentences>)

    @Delete
    suspend fun deleteSentences(sentences: Sentences)

    @Transaction
    suspend fun deleteSentencesByEnglishWord(sentences: Sentences) {
        deleteSentencesByEnglishWord(sentences.englishWord)
    }

    @Query("DELETE FROM sentences WHERE englishWord = :englishWord")
    suspend fun deleteSentencesByEnglishWord(englishWord: String)

}