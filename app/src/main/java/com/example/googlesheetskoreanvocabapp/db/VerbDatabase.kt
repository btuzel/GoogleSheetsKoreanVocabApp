package com.example.googlesheetskoreanvocabapp.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [Verbs::class],
    version = 1
)
abstract class VerbDatabase : RoomDatabase() {
    abstract fun verbDao(): VerbDao
}