package com.example.googlesheetskoreanvocabapp

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException
import java.net.InetSocketAddress
import java.net.Socket
import java.net.SocketAddress

suspend fun isOnline(): Boolean {
    return try {
        val timeoutMs = 2_000
        val sock = Socket()
        val socketAddress: SocketAddress = InetSocketAddress("8.8.8.8", 53)
        withContext(Dispatchers.IO) {
            sock.connect(socketAddress, timeoutMs)
            sock.close()
        }
        true
    } catch (e: IOException) {
        false
    }
}

fun String.fixStrings(): String {
    return removeSurrounding(prefix = "[", suffix = "]")
}