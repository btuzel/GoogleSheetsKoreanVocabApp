package com.example.googlesheetskoreanvocabapp.di

import android.app.Application
import androidx.room.Room
import com.example.googlesheetskoreanvocabapp.db.Database
import com.example.googlesheetskoreanvocabapp.db.VerbDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object VerbDaoModule {

    @Provides
    fun provideVerbDao(database: Database): VerbDao {
        return database.verbDao()
    }

    @Provides
    fun provideDatabase(application: Application): Database {
        return Room.databaseBuilder(
            application,
            Database::class.java,
            "my-db"
        ).build()
    }
}