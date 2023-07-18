package com.example.googlesheetskoreanvocabapp.db

import javax.inject.Inject

class VerbRepository @Inject constructor(private val verbDao: VerbDao) {
    suspend fun getVerbs(): Pair<List<String>, List<String>> {
        val verbs = verbDao.getVerbs()
        val englishWords = verbs.map { it.englishWord }
        val koreanWords = verbs.map { it.koreanWord }
        return Pair(englishWords, koreanWords)
    }

    suspend fun insertVerbs(verbs: List<Verbs>) {
        verbDao.insertVerbs(verbs)
    }

    suspend fun deleteVerb(verbs: Verbs) {
        verbDao.deleteVerbByEnglishWord(verbs)
    }
}