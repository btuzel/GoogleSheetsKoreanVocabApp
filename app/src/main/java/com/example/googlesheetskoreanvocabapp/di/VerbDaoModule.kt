package com.example.googlesheetskoreanvocabapp.di

import android.app.Application
import androidx.room.Room
import com.example.googlesheetskoreanvocabapp.db.VerbDatabase
import com.example.googlesheetskoreanvocabapp.db.VerbDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object VerbDaoModule {

    @Provides
    fun provideVerbDao(verbDatabase: VerbDatabase): VerbDao {
        return verbDatabase.verbDao()
    }

    @Provides
    fun provideDatabase(application: Application): VerbDatabase {
        return Room.databaseBuilder(
            application,
            VerbDatabase::class.java,
            "my-db"
        ).build()
    }
}