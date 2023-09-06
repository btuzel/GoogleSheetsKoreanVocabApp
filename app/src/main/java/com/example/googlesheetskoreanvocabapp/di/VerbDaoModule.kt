package com.example.googlesheetskoreanvocabapp.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.example.googlesheetskoreanvocabapp.db.Daos
import com.example.googlesheetskoreanvocabapp.db.MainDatabase
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
    fun provideMainDao(mainDatabase: MainDatabase): Daos {
        return mainDatabase.mainDao()
    }

    @Provides
    fun provideDatabase(application: Application): MainDatabase {
        return Room.databaseBuilder(
            application,
            MainDatabase::class.java,
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