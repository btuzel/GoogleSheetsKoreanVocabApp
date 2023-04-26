package com.example.googlesheetskoreanvocabapp.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [Verbs::class, Nouns::class, Adverbs::class, Phrases::class, Positions::class, Sentences::class],
    version = 1
)
abstract class VerbDatabase : RoomDatabase() {
    abstract fun verbDao(): VerbDao
}