package com.example.googlesheetskoreanvocabapp

import android.app.Application
import androidx.room.Room
import com.example.googlesheetskoreanvocabapp.db.MainDatabase
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class GSKVApplication : Application() {
    private lateinit var mainDatabase: MainDatabase
    override fun onCreate() {
        super.onCreate()
        mainDatabase =
            Room.databaseBuilder(applicationContext, MainDatabase::class.java, "my-db").build()
    }
}