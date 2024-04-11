package com.hoa.logindemo.utils

import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader

object ResourceUtils {

    fun getMockData(fileName: String): String {
        try {
            val cl: ClassLoader = ClassLoader.getSystemClassLoader()
            val inputStream = cl.getResourceAsStream(fileName)
            return inputStreamToString(inputStream)
        } catch (e: IOException) {
            throw RuntimeException(e)
        }
    }

    private fun inputStreamToString(inputStream: InputStream?): String {
        val builder = StringBuilder()
        InputStreamReader(inputStream, "UTF-8")
            .readLines()
            .forEach {
                builder.append(it)
            }
        return builder.toString()
    }
}