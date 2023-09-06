package com.example.googlesheetskoreanvocabapp.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [Yuun::class, Hyungseok::class, OldWords::class, Repeatables::class],
    version = 1
)
abstract class MainDatabase : RoomDatabase() {
    abstract fun mainDao(): Daos
}