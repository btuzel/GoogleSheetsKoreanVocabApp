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

}