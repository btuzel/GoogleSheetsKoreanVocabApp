package com.example.googlesheetskoreanvocabapp.data

import android.content.Context
import com.example.googlesheetskoreanvocabapp.common.fixStrings
import com.example.googlesheetskoreanvocabapp.db.VerbDatabase
import com.example.googlesheetskoreanvocabapp.db.Verbs
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
                    row.map { it.toString() }.map { cell ->
                        if (cell.isNotEmpty()) {
                            cell.fixStrings()
                        } else {
                            cell
                        }
                    }
                }
                val cleanedEngValues = engValues.filter { it.isNotEmpty() }.map { row ->
                    row.map { it.toString() }.map { cell ->
                        if (cell.isNotEmpty()) {
                            cell.fixStrings()
                        } else {
                            cell
                        }
                    }
                }
                when (wordType) {
                    WordType.VERBS -> addWordsToDB(
                        wordType = wordType,
                        cleanedEngValues.flatten(),
                        cleanedKorValues.flatten()
                    )
                }
                return@withContext Pair(
                    cleanedEngValues,
                    cleanedKorValues
                )
            } catch (e: GoogleJsonResponseException) {
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
        when (wordType) {
            WordType.VERBS -> {
                verbDatabase.verbDao().insertVerbs(
                    englishWords.mapIndexed { i, word ->
                        Verbs(
                            englishWord = word,
                            koreanWord = koreanWords[i]
                        )
                    }
                )
            }
        }
    }


    suspend fun addData(wordType: WordType, pairToAdd: Pair<String, String>): Boolean =
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
                return@withContext true
            } catch (e: GoogleJsonResponseException) {
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            }
            return@withContext false // Return false if the update was not successful
        }

    suspend fun deleteData(wordType: WordType, pairToDelete: Pair<String, String>): Boolean =
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
                    return@withContext false
                }
                val sheetId = when (wordType) {
                    WordType.VERBS -> SheetId.VERBS
                }
                val rangeToDelete = "$sheetTitle!A${indexToDelete}:${indexToDelete}"
                service.spreadsheets().values()
                    .clear(SPREADSHEET_ID, rangeToDelete, ClearValuesRequest())
                    .execute()
                deleteDataWithIndex(indexToDelete, sheetId.sheetId)
                return@withContext true
            } catch (e: GoogleJsonResponseException) {
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            }
            return@withContext false
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
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }

    enum class WordType(val sheetIndex: Int) {
        VERBS(sheetIndex = 0),
    }

    enum class SheetId(val sheetId: Int) {
        VERBS(sheetId = 0),
    }
}