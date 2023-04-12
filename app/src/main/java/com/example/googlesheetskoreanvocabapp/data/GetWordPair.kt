package com.example.googlesheetskoreanvocabapp.data

import com.example.googlesheetskoreanvocabapp.db.VerbRepository
import com.example.googlesheetskoreanvocabapp.isOnline
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetWordPair @Inject constructor(
    private val sheetsHelper: SheetsHelper,
    private val verbRepository: VerbRepository
) {
    suspend operator fun invoke(wordType: SheetsHelper.WordType): Pair<List<String>, List<String>> =
        withContext(
            Dispatchers.IO
        ) {
            if (isOnline()) {
                when (wordType) {
                    SheetsHelper.WordType.VERBS -> {
                        val wordsFromSpreadsheet =
                            sheetsHelper.getWordsFromSpreadsheet(wordType = SheetsHelper.WordType.VERBS)
                        return@withContext Pair(
                            wordsFromSpreadsheet.first!!.map { it.toString().replace("[", "").replace("]", "") },
                            wordsFromSpreadsheet.second!!.map { it.toString().replace("[", "").replace("]", "") }
                        )
                    }
                    SheetsHelper.WordType.ADVERBS -> {
                        val wordsFromSpreadsheet =
                            sheetsHelper.getWordsFromSpreadsheet(wordType = SheetsHelper.WordType.ADVERBS)
                        return@withContext Pair(
                            wordsFromSpreadsheet.first!!.map { it.toString().replace("[", "").replace("]", "") },
                            wordsFromSpreadsheet.second!!.map { it.toString().replace("[", "").replace("]", "") }
                        )
                    }
                    SheetsHelper.WordType.COMPLEX_SENTENCES -> {
                        val wordsFromSpreadsheet =
                            sheetsHelper.getWordsFromSpreadsheet(wordType = SheetsHelper.WordType.COMPLEX_SENTENCES)
                        return@withContext Pair(
                            wordsFromSpreadsheet.first!!.map { it.toString().replace("[", "").replace("]", "") },
                            wordsFromSpreadsheet.second!!.map { it.toString().replace("[", "").replace("]", "") }
                        )
                    }
                    SheetsHelper.WordType.USEFUL_PHRASES -> {
                        val wordsFromSpreadsheet =
                            sheetsHelper.getWordsFromSpreadsheet(wordType = SheetsHelper.WordType.USEFUL_PHRASES)
                        return@withContext Pair(
                            wordsFromSpreadsheet.first!!.map { it.toString().replace("[", "").replace("]", "") },
                            wordsFromSpreadsheet.second!!.map { it.toString().replace("[", "").replace("]", "") }
                        )
                    }
                    SheetsHelper.WordType.NOUNS -> {
                        val wordsFromSpreadsheet =
                            sheetsHelper.getWordsFromSpreadsheet(wordType = SheetsHelper.WordType.NOUNS)
                        return@withContext Pair(
                            wordsFromSpreadsheet.first!!.map { it.toString().replace("[", "").replace("]", "") },
                            wordsFromSpreadsheet.second!!.map { it.toString().replace("[", "").replace("]", "") }
                        )
                    }
                    SheetsHelper.WordType.POSITIONS -> {
                        val wordsFromSpreadsheet =
                            sheetsHelper.getWordsFromSpreadsheet(wordType = SheetsHelper.WordType.POSITIONS)
                        return@withContext Pair(
                            wordsFromSpreadsheet.first!!.map { it.toString().replace("[", "").replace("]", "") },
                            wordsFromSpreadsheet.second!!.map { it.toString().replace("[", "").replace("]", "") }
                        )
                    }
                    SheetsHelper.WordType.SOME_SENTENCES -> {
                        //TODO not implemented
                        val wordsFromSpreadsheet =
                            sheetsHelper.getWordsFromSpreadsheet(wordType = SheetsHelper.WordType.NOUNS)
                        return@withContext Pair(
                            wordsFromSpreadsheet.first!!.map { it.toString().replace("[", "").replace("]", "") },
                            wordsFromSpreadsheet.second!!.map { it.toString().replace("[", "").replace("]", "") }
                        )
                    }
                }
            } else {
                when (wordType) {
                    SheetsHelper.WordType.VERBS -> return@withContext verbRepository.getVerbs()
                    SheetsHelper.WordType.ADVERBS -> return@withContext verbRepository.getAdverbs()
                    SheetsHelper.WordType.COMPLEX_SENTENCES -> return@withContext verbRepository.getSentences()
                    SheetsHelper.WordType.USEFUL_PHRASES -> return@withContext verbRepository.getPhrases()
                    SheetsHelper.WordType.NOUNS -> return@withContext verbRepository.getNouns()
                    SheetsHelper.WordType.POSITIONS -> return@withContext verbRepository.getPositions()
                    SheetsHelper.WordType.SOME_SENTENCES -> return@withContext verbRepository.getNouns()
                }
            }
        }
}