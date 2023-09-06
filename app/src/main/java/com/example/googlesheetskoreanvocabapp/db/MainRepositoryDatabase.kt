package com.example.googlesheetskoreanvocabapp.db

import javax.inject.Inject

class MainRepositoryDatabase @Inject constructor(private val daos: Daos) {
    suspend fun getYuuns(): Pair<List<String>, List<String>> {
        val yuuns = daos.getYuuns()
        val englishWords = yuuns.map { it.englishWord }
        val koreanWords = yuuns.map { it.koreanWord }
        return Pair(englishWords, koreanWords)
    }

    suspend fun insertYuuns(yuuns: List<Yuun>) {
        daos.insertYuuns(yuuns)
    }

    suspend fun deleteYuuns(yuuns: Yuun) {
        daos.deleteYuunByEnglishWord(yuuns)
    }

    suspend fun getHyungseoks(): Pair<List<String>, List<String>> {
        val hyungseoks = daos.getHyungseoks()
        val englishWords = hyungseoks.map { it.englishWord }
        val koreanWords = hyungseoks.map { it.koreanWord }
        return Pair(englishWords, koreanWords)
    }

    suspend fun insertHyungseoks(hyungseoks: List<Hyungseok>) {
        daos.insertHyungseoks(hyungseoks)
    }

    suspend fun deleteHyungseoks(hyungseoks: Hyungseok) {
        daos.deleteHyungseokByEnglishWord(hyungseoks)
    }

    suspend fun getOldWords(): Pair<List<String>, List<String>> {
        val oldwords = daos.getOldWords()
        val englishWords = oldwords.map { it.englishWord }
        val koreanWords = oldwords.map { it.koreanWord }
        return Pair(englishWords, koreanWords)
    }

    suspend fun insertOldWords(oldWords: List<OldWords>) {
        daos.insertOldWords(oldWords)
    }

    suspend fun deleteOldWords(oldWords: OldWords) {
        daos.deleteOldWordByEnglishWord(oldWords)
    }

    suspend fun getRepeatables(): Pair<List<String>, List<String>> {
        val repeatables = daos.getRepeatables()
        val englishWords = repeatables.map { it.englishWord }
        val koreanWords = repeatables.map { it.koreanWord }
        return Pair(englishWords, koreanWords)
    }

    suspend fun insertRepeatables(repeatables: List<Repeatables>) {
        daos.insertRepeatables(repeatables)
    }

    suspend fun deleteRepeatables(repeatables: Repeatables) {
        daos.deleteRepeatableByEnglishWord(repeatables)
    }
}