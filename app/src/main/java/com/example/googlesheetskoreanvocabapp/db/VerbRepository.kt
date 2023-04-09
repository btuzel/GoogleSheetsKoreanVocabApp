package com.example.googlesheetskoreanvocabapp.db

import javax.inject.Inject

class VerbRepository @Inject constructor(private val verbDao: VerbDao) {
    suspend fun getWordPairs(): List<Verbs> {
        return verbDao.getVerbs()
    }

    suspend fun insertWordPairs(verbs: List<Verbs>) {
        verbDao.insertVerbs(verbs)
    }
}