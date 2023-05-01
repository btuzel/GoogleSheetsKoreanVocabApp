package com.example.googlesheetskoreanvocabapp.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.example.googlesheetskoreanvocabapp.db.VerbDao
import com.example.googlesheetskoreanvocabapp.db.VerbDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Named
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
    @Named("masterKey")
    internal fun provideMasterKey(): String {
        return MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)
    }

    @Provides
    @Singleton
    internal fun provideEncryptedSharedPreferences(
        @ApplicationContext context: Context,
        @Named("masterKey") masterKey: String
    ): EncryptedSharedPreferences {
        return EncryptedSharedPreferences.create(
            "settings_shared_prefs",
            masterKey,
            context,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        ) as EncryptedSharedPreferences
    }

    @Provides
    fun provideDispatcher(): CoroutineDispatcher {
        return Dispatchers.IO
    }
}