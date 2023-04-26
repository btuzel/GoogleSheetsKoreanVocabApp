package com.example.googlesheetskoreanvocabapp

import android.app.Application
import androidx.room.Room
import com.example.googlesheetskoreanvocabapp.db.VerbDatabase
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class GSKVApplication : Application() {
    private lateinit var verbDatabase: VerbDatabase
    override fun onCreate() {
        super.onCreate()
        verbDatabase =
            Room.databaseBuilder(applicationContext, VerbDatabase::class.java, "my-db").build()
    }
}