package com.example.googlesheetskoreanvocabapp.data

import android.content.Context
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential
import com.google.api.client.googleapis.json.GoogleJsonResponseException
import com.google.api.client.http.HttpTransport
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.JsonFactory
import com.google.api.client.json.gson.GsonFactory
import com.google.api.services.sheets.v4.Sheets
import com.google.api.services.sheets.v4.SheetsScopes
import com.google.api.services.sheets.v4.model.ValueRange
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.IOException
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber

const val SPREADSHEET_ID = "1OX5NhFXAiPXwjdW5g6AmEriWbqzRvAvT_uZOAeVQ1t8"

class SheetsHelper @Inject constructor(@ApplicationContext applicationContext: Context) {
    private val transport: HttpTransport = NetHttpTransport()
    private val jsonFactory: JsonFactory = GsonFactory.getDefaultInstance()

    private val credentials: GoogleCredential = GoogleCredential.fromStream(
        applicationContext.assets.open("my_creds.json")
    ).createScoped(listOf(SheetsScopes.SPREADSHEETS))

    private val service: Sheets = Sheets.Builder(transport, jsonFactory, credentials)
        .setApplicationName("GoogleSheetsKoreanVocabApp")
        .build()

    //TODO: add offline mode
    suspend fun getWordsFromSpreadsheet(wordType: WordType): Pair<List<List<Any>>?, List<List<Any>>?> =
        withContext(Dispatchers.IO) {
            try {
                val sheetTitle = service.spreadsheets().get(SPREADSHEET_ID)
                    .execute().sheets[wordType.sheetIndex].properties.title
                val englishWordsRange = "$sheetTitle!A1:A"
                val koreanWordsRange = "$sheetTitle!B1:B"
                val englishWords =
                    service.spreadsheets().values().get(SPREADSHEET_ID, englishWordsRange).execute()
                        .getValues()
                val koreanWords =
                    service.spreadsheets().values().get(SPREADSHEET_ID, koreanWordsRange).execute()
                        .getValues()
                return@withContext Pair(englishWords, koreanWords)
            } catch (e: GoogleJsonResponseException) {
                Timber.tag("Google Sheets API").e("Error: " + e.statusCode + " " + e.statusMessage)
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            }
            return@withContext Pair(null, null)
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