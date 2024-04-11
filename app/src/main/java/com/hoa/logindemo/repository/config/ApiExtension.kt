package com.hoa.logindemo.repository.config

import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import java.net.HttpURLConnection.HTTP_INTERNAL_ERROR

fun <T> Throwable.toApiResponse(): ApiResponse<T> {
    return when (this) {
        is IOException -> {
            ApiResponse.Exception(NoInternet)
        }
        is HttpException -> {
            handleHttpError(response())
        }
        else -> {
            ApiResponse.Exception(Throwable("Something went wrong, please check your logic"))
        }
    }
}

private fun <T : Any> handleHttpError(response: Response<*>?): ApiResponse<T> {
    if (response == null) {
        return ApiResponse.Exception(Throwable("Something went wrong, please check your logic"))
    }
    if (response.code() >= HTTP_INTERNAL_ERROR) {
        return ApiResponse.Exception(ServerError)
    }
    return ApiResponse.Exception(BadRequest)
}
