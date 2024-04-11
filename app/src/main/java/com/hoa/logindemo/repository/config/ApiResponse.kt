package com.hoa.logindemo.repository.config

object NoInternet : Throwable()
object BadRequest : Throwable()
object ServerError : Throwable()

sealed class ApiResponse<out T> {
    data class Success<T>(val data: T) : ApiResponse<T>()
    data class Exception(val throwable: Throwable) : ApiResponse<Nothing>()
}