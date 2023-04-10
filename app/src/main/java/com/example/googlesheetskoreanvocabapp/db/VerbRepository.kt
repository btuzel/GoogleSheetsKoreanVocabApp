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
        verbDao.deleteVerb(verbs)
    }

    suspend fun getSentences(): Pair<List<String>, List<String>> {
        val sentences = verbDao.getSentences()
        val englishWords = sentences.map { it.englishWord }
        val koreanWords = sentences.map { it.koreanWord }
        return Pair(englishWords, koreanWords)
    }

    suspend fun insertSentences(sentences: List<Sentences>) {
        verbDao.insertSentences(sentences)
    }

    suspend fun deleteSentence(sentences: Sentences) {
        verbDao.deleteSentences(sentences)
    }

    suspend fun getNouns(): Pair<List<String>, List<String>> {
        val verbs = verbDao.getNouns()
        val englishWords = verbs.map { it.englishWord }
        val koreanWords = verbs.map { it.koreanWord }
        return Pair(englishWords, koreanWords)
    }

    suspend fun insertNouns(nouns: List<Nouns>) {
        verbDao.insertNouns(nouns)
    }

    suspend fun deleteNouns(nouns: Nouns) {
        verbDao.deleteNouns(nouns)
    }
    suspend fun getPhrases(): Pair<List<String>, List<String>> {
        val phrases = verbDao.getPhrases()
        val englishWords = phrases.map { it.englishWord }
        val koreanWords = phrases.map { it.koreanWord }
        return Pair(englishWords, koreanWords)
    }

    suspend fun insertPhrases(phrases: List<Phrases>) {
        verbDao.insertPhrases(phrases)
    }

    suspend fun deletePhrases(phrases: Phrases) {
        verbDao.deletePhrases(phrases)
    }

    suspend fun getPositions(): Pair<List<String>, List<String>> {
        val positions = verbDao.getPositions()
        val englishWords = positions.map { it.englishWord }
        val koreanWords = positions.map { it.koreanWord }
        return Pair(englishWords, koreanWords)
    }

    suspend fun insertPositions(positions: List<Positions>) {
        verbDao.insertPositions(positions)
    }

    suspend fun deletePositions(positions: Positions) {
        verbDao.deletePositions(positions)
    }

    suspend fun getAdverbs(): Pair<List<String>, List<String>> {
        val adverbs = verbDao.getAdverbs()
        val englishWords = adverbs.map { it.englishWord }
        val koreanWords = adverbs.map { it.koreanWord }
        return Pair(englishWords, koreanWords)
    }

    suspend fun insertAdverbs(adverbs: List<Adverbs>) {
        verbDao.insertAdverbs(adverbs)
    }

    suspend fun deleteAdverbs(adverbs: Adverbs) {
        verbDao.deleteAdverb(adverbs)
    }
}