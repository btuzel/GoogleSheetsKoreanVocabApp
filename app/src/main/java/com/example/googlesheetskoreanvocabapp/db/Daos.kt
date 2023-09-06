package com.example.googlesheetskoreanvocabapp.db

import androidx.room.*

@Dao
interface Daos {

    /////YUUN/////

    @Query("SELECT * FROM yuun")
    suspend fun getYuuns(): List<Yuun>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertYuuns(wordPairs: List<Yuun>)

    @Delete
    suspend fun deleteYuun(yuun: Yuun)

    @Transaction
    suspend fun deleteYuunByEnglishWord(yuun: Yuun) {
        deleteYuunByEnglishWord(yuun.englishWord)
    }

    @Query("DELETE FROM yuun WHERE englishWord = :englishWord")
    suspend fun deleteYuunByEnglishWord(englishWord: String)

    /////HYUNGSEOK/////

    @Query("SELECT * FROM hyungseok")
    suspend fun getHyungseoks(): List<Hyungseok>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHyungseoks(wordPairs: List<Hyungseok>)

    @Delete
    suspend fun deleteHyungseok(hyungseok: Hyungseok)

    @Transaction
    suspend fun deleteHyungseokByEnglishWord(hyungseok: Hyungseok) {
        deleteHyungseokByEnglishWord(hyungseok.englishWord)
    }

    @Query("DELETE FROM hyungseok WHERE englishWord = :englishWord")
    suspend fun deleteHyungseokByEnglishWord(englishWord: String)

    /////OLDWORDS/////

    @Query("SELECT * FROM oldwords")
    suspend fun getOldWords(): List<OldWords>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOldWords(wordPairs: List<OldWords>)

    @Delete
    suspend fun deleteOldWord(oldwords: OldWords)

    @Transaction
    suspend fun deleteOldWordByEnglishWord(oldwords: OldWords) {
        deleteOldWordByEnglishWord(oldwords.englishWord)
    }

    @Query("DELETE FROM oldwords WHERE englishWord = :englishWord")
    suspend fun deleteOldWordByEnglishWord(englishWord: String)

    /////REPEATABLES/////

    @Query("SELECT * FROM repeatables")
    suspend fun getRepeatables(): List<Repeatables>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRepeatables(wordPairs: List<Repeatables>)

    @Delete
    suspend fun deleteRepeatable(repeatables: Repeatables)

    @Transaction
    suspend fun deleteRepeatableByEnglishWord(repeatables: Repeatables) {
        deleteRepeatableByEnglishWord(repeatables.englishWord)
    }

    @Query("DELETE FROM repeatables WHERE englishWord = :englishWord")
    suspend fun deleteRepeatableByEnglishWord(englishWord: String)
}
