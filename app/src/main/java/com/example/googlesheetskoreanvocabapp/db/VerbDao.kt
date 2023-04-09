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
}