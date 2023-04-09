package com.example.googlesheetskoreanvocabapp.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [WordPairs::class], version = 1)
abstract class Database : RoomDatabase() {
    abstract fun wordDao(): WordDao
}