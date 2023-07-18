package com.example.googlesheetskoreanvocabapp.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "verbs", indices = [Index(value = ["englishWord"], unique = true)])
data class Verbs(
    @PrimaryKey @ColumnInfo(name = "id") val id: String = UUID.randomUUID().toString(),
    val englishWord: String,
    val koreanWord: String
)