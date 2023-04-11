package com.example.googlesheetskoreanvocabapp.data

import android.content.Context
import com.example.googlesheetskoreanvocabapp.db.Adverbs
import com.example.googlesheetskoreanvocabapp.db.Nouns
import com.example.googlesheetskoreanvocabapp.db.Phrases
import com.example.googlesheetskoreanvocabapp.db.Positions
import com.example.googlesheetskoreanvocabapp.db.Sentences
import com.example.googlesheetskoreanvocabapp.db.VerbDatabase
import com.example.googlesheetskoreanvocabapp.db.Verbs
import com.google.api.client.googleapis.json.GoogleJsonResponseException
import com.google.api.client.http.HttpTransport
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.JsonFactory
import com.google.api.client.json.gson.GsonFactory
import com.google.api.services.sheets.v4.Sheets
import com.google.api.services.sheets.v4.SheetsScopes
import com.google.api.services.sheets.v4.model.ClearValuesRequest
import com.google.api.services.sheets.v4.model.ValueRange
import com.google.auth.http.HttpCredentialsAdapter
import com.google.auth.oauth2.GoogleCredentials
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.IOException
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber

const val SPREADSHEET_ID = "1OX5NhFXAiPXwjdW5g6AmEriWbqzRvAvT_uZOAeVQ1t8"

class SheetsHelper @Inject constructor(
    @ApplicationContext applicationContext: Context, private val verbDatabase: VerbDatabase
) {
    private val transport: HttpTransport = NetHttpTransport()
    private val jsonFactory: JsonFactory = GsonFactory.getDefaultInstance()

    private val credentials: GoogleCredentials = GoogleCredentials.fromStream(
        applicationContext.assets.open("my_creds.json")
    ).createScoped(listOf(SheetsScopes.SPREADSHEETS))

    private val service: Sheets = Sheets.Builder(transport, jsonFactory, HttpCredentialsAdapter(credentials))
        .setApplicationName("GoogleSheetsKoreanVocabApp")
        .build()

    suspend fun getWordsFromSpreadsheet(wordType: WordType): Pair<List<List<Any>>?, List<List<Any>>?> =
        withContext(Dispatchers.IO) {
            try {
                val sheetTitle = service.spreadsheets().get(SPREADSHEET_ID)
                    .execute().sheets[wordType.sheetIndex].properties.title
                val englishWordsRange = "$sheetTitle!A1:A"
                val koreanWordsRange = "$sheetTitle!B1:B"
                val englishWords2 =
                    service.spreadsheets().values().get(SPREADSHEET_ID, englishWordsRange).execute()
                        .getValues().map { it.toString().replace("[", "").replace("]", "") }
                val koreanWords2 =
                    service.spreadsheets().values().get(SPREADSHEET_ID, koreanWordsRange).execute()
                        .getValues().map { it.toString().replace("[", "").replace("]", "") }

                when (wordType) {
                    WordType.VERBS -> addVerbsToDB(englishWords2, koreanWords2)
                    WordType.ADVERBS -> addAdverbsToDB(englishWords2, koreanWords2)
                    WordType.COMPLEX_SENTENCES -> addSentenceToDB(englishWords2, koreanWords2)
                    WordType.USEFUL_PHRASES -> addPhrasestoDB(englishWords2, koreanWords2)
                    WordType.NOUNS -> addNounsToDB(englishWords2, koreanWords2)
                    WordType.POSITIONS -> addPositionsToDB(englishWords2, koreanWords2)
                    WordType.SOME_SENTENCES -> {}
                }
                return@withContext Pair(
                    service.spreadsheets().values().get(SPREADSHEET_ID, englishWordsRange).execute()
                        .getValues(),
                    service.spreadsheets().values().get(SPREADSHEET_ID, koreanWordsRange).execute()
                        .getValues()
                )
            } catch (e: GoogleJsonResponseException) {
                Timber.tag("Google Sheets API").e("Error: " + e.statusCode + " " + e.statusMessage)
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            }
            return@withContext Pair(null, null)
        }

    private suspend fun addVerbsToDB(
        englishWords: List<String>,
        koreanWords: List<String>
    ) {
        val listOfVerbs = mutableListOf<Verbs>()
        for (i in englishWords.indices) {
            val wordPairs = Verbs(englishWord = englishWords[i], koreanWord = koreanWords[i])
            listOfVerbs.add(wordPairs)
        }
        verbDatabase.verbDao().insertVerbs(
            listOfVerbs
        )
    }

    private suspend fun addPositionsToDB(
        englishWords: List<String>,
        koreanWords: List<String>
    ) {
        val listOfPositions = mutableListOf<Positions>()
        for (i in englishWords.indices) {
            val wordPairs =
                Positions(englishWord = englishWords[i], koreanWord = koreanWords[i])
            listOfPositions.add(wordPairs)
        }
        verbDatabase.verbDao().insertPositions(
            listOfPositions
        )
    }

    private suspend fun addPhrasestoDB(
        englishWords: List<String>,
        koreanWords: List<String>
    ) {
        val listOfPhrases = mutableListOf<Phrases>()
        for (i in englishWords.indices) {
            val wordPairs = Phrases(englishWord = englishWords[i], koreanWord = koreanWords[i])
            listOfPhrases.add(wordPairs)
        }
        verbDatabase.verbDao().insertPhrases(
            listOfPhrases
        )
    }

    private suspend fun addSentenceToDB(
        englishWords: List<String>,
        koreanWords: List<String>
    ) {
        val listOfSentences = mutableListOf<Sentences>()
        for (i in englishWords.indices) {
            val wordPairs =
                Sentences(englishWord = englishWords[i], koreanWord = koreanWords[i])
            listOfSentences.add(wordPairs)
        }
        verbDatabase.verbDao().insertSentences(
            listOfSentences
        )
    }

    private suspend fun addAdverbsToDB(
        englishWords: List<String>,
        koreanWords: List<String>
    ) {
        val listOfAdverbs = mutableListOf<Adverbs>()
        for (i in englishWords.indices) {
            val wordPairs = Adverbs(englishWord = englishWords[i], koreanWord = koreanWords[i])
            listOfAdverbs.add(wordPairs)
        }
        verbDatabase.verbDao().insertAdverbs(
            listOfAdverbs
        )
    }

    private suspend fun addNounsToDB(
        englishWords: List<String>,
        koreanWords: List<String>
    ) {
        val listOfNouns = mutableListOf<Nouns>()
        for (i in englishWords.indices) {
            val wordPairs = Nouns(englishWord = englishWords[i], koreanWord = koreanWords[i])
            listOfNouns.add(wordPairs)
        }
        verbDatabase.verbDao().insertNouns(
            listOfNouns
        )
    }


    suspend fun addData(wordType: WordType, pairToAdd: Pair<String, String>) =
        withContext(Dispatchers.IO) {
            try {
                val sheetTitle = service.spreadsheets().get(SPREADSHEET_ID)
                    .execute().sheets[wordType.sheetIndex].properties.title
                val valuesOnColumnOne =
                    (service.spreadsheets().values().get(SPREADSHEET_ID, "$sheetTitle!A1:A")
                        .execute()
                        .getValues().size) + 1
                val values = listOf(
                    listOf(pairToAdd.first, pairToAdd.second)
                )
                val body = ValueRange().apply {
                    setValues(values)
                }
                service.spreadsheets().values()
                    .update(SPREADSHEET_ID, "${wordType.name}!A$valuesOnColumnOne", body)
                    .setValueInputOption("USER_ENTERED")
                    .execute()
            } catch (e: GoogleJsonResponseException) {
                Timber.tag("Google Sheets API").e("Error: " + e.statusCode + " " + e.statusMessage)
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }!!

    suspend fun deleteData(wordType: WordType, pairToDelete: Pair<String, String>) =
        withContext(Dispatchers.IO) {
            try {
                val sheetTitle = service.spreadsheets().get(SPREADSHEET_ID)
                    .execute().sheets[wordType.sheetIndex].properties.title
                val range = "$sheetTitle!A1:B"
                val response = service.spreadsheets().values()
                    .get(SPREADSHEET_ID, range)
                    .execute()
                val values = response.getValues() ?: emptyList()
                val indexToDelete = values.indexOfFirst { it[0] == pairToDelete.first && it[1] == pairToDelete.second } + 1
                if (indexToDelete == 0) {
                    return@withContext
                }
                val rangeToDelete = "$sheetTitle!A${indexToDelete}:${indexToDelete}"
                service.spreadsheets().values()
                    .clear(SPREADSHEET_ID, rangeToDelete, ClearValuesRequest())
                    .execute()
            } catch (e: GoogleJsonResponseException) {
                Timber.tag("Google Sheets API").e("Error: " + e.statusCode + " " + e.statusMessage)
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }

    enum class WordType(val sheetIndex: Int) {
        VERBS(sheetIndex = 0),
        ADVERBS(sheetIndex = 1),
        COMPLEX_SENTENCES(sheetIndex = 2),
        USEFUL_PHRASES(sheetIndex = 3),
        NOUNS(sheetIndex = 4),
        POSITIONS(sheetIndex = 5),
        SOME_SENTENCES(sheetIndex = 6)
    }
}