package com.dev.newsapp.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import java.io.IOException
import java.io.InputStream
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.Locale

fun inputStreamToString(inputStream: InputStream): String? {
    return try {
        val bytes = ByteArray(inputStream.available())
        inputStream.read(bytes, 0, bytes.size)
        String(bytes)
    } catch (e: IOException) {
        null
    }
}

fun convertDate(dateTime: String): String {
    val instant = Instant.parse(dateTime)
    val localDateTime = LocalDateTime.ofInstant(instant, ZoneOffset.UTC)
    val formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy", Locale.ENGLISH)
    return localDateTime.format(formatter)
}

fun String.capitalizeFirstChar(): String {
    return if (isNotEmpty()) {
        this[0].uppercase() + substring(1)
    } else {
        this
    }
}

fun checkInternet(mContext: Context): Boolean {
    val connectivityManager =
        mContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    val network = connectivityManager.activeNetwork
    if (network != null) {
        val networkCapabilities = connectivityManager.getNetworkCapabilities(network)
        return networkCapabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true
    }

    return false
}