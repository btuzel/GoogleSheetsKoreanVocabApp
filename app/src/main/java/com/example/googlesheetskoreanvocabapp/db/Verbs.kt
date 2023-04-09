package com.example.googlesheetskoreanvocabapp.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "verbs", indices = [Index(value = ["englishWord"], unique = true)]
)
data class Verbs(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val id: Int = 0,
    val englishWord: String,
    val koreanWord: String
)