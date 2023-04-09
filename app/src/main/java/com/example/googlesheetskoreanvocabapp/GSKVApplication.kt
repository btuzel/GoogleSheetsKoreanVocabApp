package com.example.googlesheetskoreanvocabapp

import android.app.Application
import androidx.room.Room
import com.example.googlesheetskoreanvocabapp.db.Database
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class GSKVApplication : Application() {
    private lateinit var database: Database

    override fun onCreate() {
        super.onCreate()
        database = Room.databaseBuilder(applicationContext, Database::class.java, "my-db").build()
    }
}