package com.example.googlesheetskoreanvocabapp.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "yuun", indices = [Index(value = ["englishWord"], unique = true)])
data class Yuun(
    @PrimaryKey @ColumnInfo(name = "id") val id: String = UUID.randomUUID().toString(),
    val englishWord: String,
    val koreanWord: String
)

@Entity(tableName = "repeatables", indices = [Index(value = ["englishWord"], unique = true)])
data class Repeatables(
    @PrimaryKey @ColumnInfo(name = "id") val id: String = UUID.randomUUID().toString(),
    val englishWord: String,
    val koreanWord: String
)

@Entity(tableName = "oldwords", indices = [Index(value = ["englishWord"], unique = true)])
data class OldWords(
    @PrimaryKey @ColumnInfo(name = "id") val id: String = UUID.randomUUID().toString(),
    val englishWord: String,
    val koreanWord: String
)

@Entity(tableName = "hyungseok", indices = [Index(value = ["englishWord"], unique = true)])
data class Hyungseok(
    @PrimaryKey @ColumnInfo(name = "id") val id: String = UUID.randomUUID().toString(),
    val englishWord: String,
    val koreanWord: String
)
