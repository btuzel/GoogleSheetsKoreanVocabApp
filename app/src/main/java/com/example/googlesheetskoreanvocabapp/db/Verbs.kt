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

@Entity(
    tableName = "adverbs", indices = [Index(value = ["englishWord"], unique = true)]
)
data class Adverbs(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val id: Int = 0,
    val englishWord: String,
    val koreanWord: String
)

@Entity(
    tableName = "nouns", indices = [Index(value = ["englishWord"], unique = true)]
)
data class Nouns(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val id: Int = 0,
    val englishWord: String,
    val koreanWord: String
)

@Entity(
    tableName = "phrases", indices = [Index(value = ["englishWord"], unique = true)]
)
data class Phrases(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val id: Int = 0,
    val englishWord: String,
    val koreanWord: String
)

@Entity(
    tableName = "positions", indices = [Index(value = ["englishWord"], unique = true)]
)
data class Positions(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val id: Int = 0,
    val englishWord: String,
    val koreanWord: String
)

@Entity(
    tableName = "sentences", indices = [Index(value = ["englishWord"], unique = true)]
)
data class Sentences(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val id: Int = 0,
    val englishWord: String,
    val koreanWord: String
)