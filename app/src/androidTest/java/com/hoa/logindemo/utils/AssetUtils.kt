package com.hoa.logindemo.utils

import androidx.test.platform.app.InstrumentationRegistry
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader

object AssetUtils {

    fun getMockData(fileName: String): String {
        try {
            val context = InstrumentationRegistry.getInstrumentation().context
            val inputStream = context.assets.open(fileName)
            return inputStreamToString(inputStream)
        } catch (e: IOException) {
            throw RuntimeException(e)
        }
    }

    private fun inputStreamToString(inputStream: InputStream): String {
        val builder = StringBuilder()
        InputStreamReader(inputStream, "UTF-8")
            .readLines()
            .forEach {
                builder.append(it)
            }
        return builder.toString()
    }
}