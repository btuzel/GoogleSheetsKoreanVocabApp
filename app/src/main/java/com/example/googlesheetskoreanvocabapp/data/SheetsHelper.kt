package com.example.googlesheetskoreanvocabapp.data

import android.content.Context
import com.example.googlesheetskoreanvocabapp.db.Adverbs
import com.example.googlesheetskoreanvocabapp.db.Nouns
import com.example.googlesheetskoreanvocabapp.db.Phrases
import com.example.googlesheetskoreanvocabapp.db.Positions
import com.example.googlesheetskoreanvocabapp.db.Sentences
import com.example.googlesheetskoreanvocabapp.db.VerbDatabase
import com.example.googlesheetskoreanvocabapp.db.Verbs
import com.example.googlesheetskoreanvocabapp.fixStrings
import com.google.api.client.googleapis.json.GoogleJsonResponseException
import com.google.api.client.http.HttpTransport
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.JsonFactory
import com.google.api.client.json.gson.GsonFactory
import com.google.api.services.sheets.v4.Sheets
import com.google.api.services.sheets.v4.SheetsScopes
import com.google.api.services.sheets.v4.model.BatchUpdateSpreadsheetRequest
import com.google.api.services.sheets.v4.model.ClearValuesRequest
import com.google.api.services.sheets.v4.model.DeleteDimensionRequest
import com.google.api.services.sheets.v4.model.DimensionRange
import com.google.api.services.sheets.v4.model.Request
import com.google.api.services.sheets.v4.model.ValueRange
import com.google.auth.http.HttpCredentialsAdapter
import com.google.auth.oauth2.GoogleCredentials
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject

const val SPREADSHEET_ID = "1OX5NhFXAiPXwjdW5g6AmEriWbqzRvAvT_uZOAeVQ1t8"

class SheetsHelper @Inject constructor(
    @ApplicationContext applicationContext: Context, private val verbDatabase: VerbDatabase
) {
    private val transport: HttpTransport = NetHttpTransport()
    private val jsonFactory: JsonFactory = GsonFactory.getDefaultInstance()

    private val credentials: GoogleCredentials = GoogleCredentials.fromStream(
        applicationContext.assets.open("my_creds.json")
    ).createScoped(listOf(SheetsScopes.SPREADSHEETS))

    private val service: Sheets =
        Sheets.Builder(transport, jsonFactory, HttpCredentialsAdapter(credentials))
            .setApplicationName("GoogleSheetsKoreanVocabApp")
            .build()

    suspend fun getWordsFromSpreadsheet(wordType: WordType): Pair<List<List<Any>>?, List<List<Any>>?> =
        withContext(Dispatchers.IO) {
            try {
                val sheetTitle = service.spreadsheets().get(SPREADSHEET_ID)
                    .execute().sheets[wordType.sheetIndex].properties.title
                val engValues =
                    service.spreadsheets().values()
                        .get(SPREADSHEET_ID, "$sheetTitle!A1:A").execute()
                        .getValues()
                val emptyListengValuesIndices = engValues.indices.filter { engValues[it].size == 0 }
                val sheetId = when (wordType) {
                    WordType.VERBS -> SheetId.VERBS
                    WordType.ADVERBS -> SheetId.ADVERBS
                    WordType.COMPLEX_SENTENCES -> SheetId.COMPLEX_SENTENCES
                    WordType.USEFUL_PHRASES -> SheetId.USEFUL_PHRASES
                    WordType.NOUNS -> SheetId.NOUNS
                    WordType.POSITIONS -> SheetId.POSITIONS
                }
                emptyListengValuesIndices.forEach { deleteDataWithIndex(it, sheetId.sheetId) }
                val koreanValues =
                    service.spreadsheets().values()
                        .get(
                            SPREADSHEET_ID, "$sheetTitle!B1:B"
                        ).execute()
                        .getValues()
                val emptyListkoreanValuesIndices =
                    koreanValues.indices.filter { koreanValues[it].size == 0 }
                emptyListkoreanValuesIndices.forEach { deleteDataWithIndex(it, sheetId.sheetId) }
                val cleanedKorValues = koreanValues.filter { it.isNotEmpty() }.map { row ->
                    row.map { cell ->
                        if (cell is String && cell.isNotEmpty()) {
                            cell.fixStrings()
                        } else {
                            cell
                        }
                    }
                }
                val cleanedEngValues = engValues.filter { it.isNotEmpty() }.map { row ->
                    row.map { cell ->
                        if (cell is String && cell.isNotEmpty()) {
                            cell.fixStrings()
                        } else {
                            cell
                        }
                    }
                }
                when (wordType) {
                    WordType.VERBS -> addWordsToDB(
                        wordType = wordType,
                        cleanedEngValues.flatten() as List<String>,
                        cleanedKorValues.flatten() as List<String>
                    )

                    WordType.ADVERBS -> addWordsToDB(
                        wordType = wordType,
                        cleanedEngValues.flatten() as List<String>,
                        cleanedKorValues.flatten() as List<String>
                    )

                    WordType.COMPLEX_SENTENCES -> addWordsToDB(
                        wordType = wordType,
                        cleanedEngValues.flatten() as List<String>,
                        cleanedKorValues.flatten() as List<String>
                    )

                    WordType.USEFUL_PHRASES -> addWordsToDB(
                        wordType = wordType,
                        cleanedEngValues.flatten() as List<String>,
                        cleanedKorValues.flatten() as List<String>
                    )

                    WordType.NOUNS -> addWordsToDB(
                        wordType = wordType,
                        cleanedEngValues.flatten() as List<String>,
                        cleanedKorValues.flatten() as List<String>
                    )

                    WordType.POSITIONS -> addWordsToDB(
                        wordType = wordType,
                        cleanedEngValues.flatten() as List<String>,
                        cleanedKorValues.flatten() as List<String>
                    )
                }
                return@withContext Pair(
                    cleanedEngValues,
                    cleanedKorValues
                )
            } catch (e: GoogleJsonResponseException) {
                Timber.tag("Google Sheets API")
                    .e("Error: " + e.statusCode + " " + e.statusMessage)
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            }
            return@withContext Pair(null, null)
        }

    private suspend fun addWordsToDB(
        wordType: WordType,
        englishWords: List<String>,
        koreanWords: List<String>
    ) {
        val wordList = when (wordType) {
            WordType.VERBS -> englishWords.mapIndexed { i, word ->
                Verbs(
                    englishWord = word,
                    koreanWord = koreanWords[i]
                )
            }

            WordType.ADVERBS -> englishWords.mapIndexed { i, word ->
                Adverbs(
                    englishWord = word,
                    koreanWord = koreanWords[i]
                )
            }

            WordType.COMPLEX_SENTENCES -> englishWords.mapIndexed { i, word ->
                Sentences(
                    englishWord = word,
                    koreanWord = koreanWords[i]
                )
            }

            WordType.USEFUL_PHRASES -> englishWords.mapIndexed { i, word ->
                Phrases(
                    englishWord = word,
                    koreanWord = koreanWords[i]
                )
            }

            WordType.NOUNS -> englishWords.mapIndexed { i, word ->
                Nouns(
                    englishWord = word,
                    koreanWord = koreanWords[i]
                )
            }

            WordType.POSITIONS -> englishWords.mapIndexed { i, word ->
                Positions(
                    englishWord = word,
                    koreanWord = koreanWords[i]
                )
            }

        }
        when (wordType) {
            WordType.VERBS -> {
                verbDatabase.verbDao().insertVerbs(
                    wordList as List<Verbs>
                )
            }

            WordType.ADVERBS -> {
                verbDatabase.verbDao().insertAdverbs(
                    wordList as List<Adverbs>
                )
            }

            WordType.COMPLEX_SENTENCES -> {
                verbDatabase.verbDao().insertSentences(
                    wordList as List<Sentences>
                )
            }

            WordType.USEFUL_PHRASES -> {
                verbDatabase.verbDao().insertPhrases(
                    wordList as List<Phrases>
                )
            }

            WordType.NOUNS -> {
                verbDatabase.verbDao().insertNouns(
                    wordList as List<Nouns>
                )
            }

            WordType.POSITIONS -> {
                verbDatabase.verbDao().insertPositions(
                    wordList as List<Positions>
                )
            }
        }
    }

    suspend fun addData(wordType: WordType, pairToAdd: Pair<String, String>) =
        withContext(Dispatchers.IO) {
            try {
                val sheetTitle = service.spreadsheets().get(SPREADSHEET_ID)
                    .execute().sheets[wordType.sheetIndex].properties.title
                val valuesOnColumnOne =
                    (service.spreadsheets().values()
                        .get(SPREADSHEET_ID, "$sheetTitle!A1:A")
                        .execute()
                        .getValues().size) + 1
                val values = listOf(
                    listOf(pairToAdd.first, pairToAdd.second)
                )
                val body = ValueRange().apply {
                    setValues(values)
                }
                service.spreadsheets().values()
                    .update(
                        SPREADSHEET_ID,
                        "${wordType.name}!A$valuesOnColumnOne",
                        body
                    )
                    .setValueInputOption("USER_ENTERED")
                    .execute()
            } catch (e: GoogleJsonResponseException) {
                Timber.tag("Google Sheets API")
                    .e("Error: " + e.statusCode + " " + e.statusMessage)
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
                val indexToDelete =
                    values.indexOfFirst { it[0] == pairToDelete.first && it[1] == pairToDelete.second } + 1
                if (indexToDelete == 0) {
                    return@withContext
                }
                val sheetId = when (wordType) {
                    WordType.VERBS -> SheetId.VERBS
                    WordType.ADVERBS -> SheetId.ADVERBS
                    WordType.COMPLEX_SENTENCES -> SheetId.COMPLEX_SENTENCES
                    WordType.USEFUL_PHRASES -> SheetId.USEFUL_PHRASES
                    WordType.NOUNS -> SheetId.NOUNS
                    WordType.POSITIONS -> SheetId.POSITIONS
                }
                val rangeToDelete = "$sheetTitle!A${indexToDelete}:${indexToDelete}"
                service.spreadsheets().values()
                    .clear(SPREADSHEET_ID, rangeToDelete, ClearValuesRequest())
                    .execute()
                deleteDataWithIndex(indexToDelete, sheetId.sheetId)
            } catch (e: GoogleJsonResponseException) {
                Timber.tag("Google Sheets API")
                    .e("Error: " + e.statusCode + " " + e.statusMessage)
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }


    private suspend fun deleteDataWithIndex(indexToDelete: Int, sheetId: Int) =
        withContext(Dispatchers.IO) {
            try {
                if (indexToDelete == 0) {
                    return@withContext
                }
                val request = BatchUpdateSpreadsheetRequest()
                val deleteRequest = DeleteDimensionRequest()
                val dimensionRange = DimensionRange()
                dimensionRange.sheetId = sheetId
                dimensionRange.dimension = "ROWS"
                dimensionRange.startIndex = indexToDelete - 1
                dimensionRange.endIndex = indexToDelete
                deleteRequest.range = dimensionRange
                request.requests = listOf(Request().setDeleteDimension(deleteRequest))
                service.spreadsheets().batchUpdate(SPREADSHEET_ID, request).execute()
            } catch (e: GoogleJsonResponseException) {
                Timber.tag("Google Sheets API")
                    .e("Error: " + e.statusCode + " " + e.statusMessage)
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
    }

    enum class SheetId(val sheetId: Int) {
        VERBS(sheetId = 0),
        ADVERBS(sheetId = 1607057609),
        COMPLEX_SENTENCES(sheetId = 1322873134),
        USEFUL_PHRASES(sheetId = 1557476308),
        NOUNS(sheetId = 1951405031),
        POSITIONS(sheetId = 378086967),
    }
}