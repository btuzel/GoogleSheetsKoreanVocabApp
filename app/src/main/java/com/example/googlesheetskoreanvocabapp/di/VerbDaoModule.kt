package com.example.googlesheetskoreanvocabapp.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.example.googlesheetskoreanvocabapp.db.VerbDao
import com.example.googlesheetskoreanvocabapp.db.VerbDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

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

    @Provides
    @Singleton
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences("MySharedPreferences", Context.MODE_PRIVATE)
    }

    @Provides
    fun provideDispatcher(): CoroutineDispatcher {
        return Dispatchers.IO
    }
}