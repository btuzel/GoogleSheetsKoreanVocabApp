package com.example.googlesheetskoreanvocabapp.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "verbs", indices = [Index(value = ["englishWord"], unique = true)])
data class Verbs(
    @PrimaryKey @ColumnInfo(name = "id") val id: String = UUID.randomUUID().toString(),
    val englishWord: String,
    val koreanWord: String
)

@Entity(tableName = "adverbs", indices = [Index(value = ["englishWord"], unique = true)])
data class Adverbs(
    @PrimaryKey @ColumnInfo(name = "id") val id: String = UUID.randomUUID().toString(),
    val englishWord: String,
    val koreanWord: String
)

@Entity(tableName = "nouns", indices = [Index(value = ["englishWord"], unique = true)])
data class Nouns(
    @PrimaryKey @ColumnInfo(name = "id") val id: String = UUID.randomUUID().toString(),
    val englishWord: String,
    val koreanWord: String
)

@Entity(tableName = "phrases", indices = [Index(value = ["englishWord"], unique = true)])
data class Phrases(
    @PrimaryKey @ColumnInfo(name = "id") val id: String = UUID.randomUUID().toString(),
    val englishWord: String,
    val koreanWord: String
)

@Entity(tableName = "positions", indices = [Index(value = ["englishWord"], unique = true)])
data class Positions(
    @PrimaryKey @ColumnInfo(name = "id") val id: String = UUID.randomUUID().toString(),
    val englishWord: String,
    val koreanWord: String
)

@Entity(tableName = "sentences", indices = [Index(value = ["englishWord"], unique = true)])
data class Sentences(
    @PrimaryKey @ColumnInfo(name = "id") val id: String = UUID.randomUUID().toString(),
    val englishWord: String,
    val koreanWord: String
)
