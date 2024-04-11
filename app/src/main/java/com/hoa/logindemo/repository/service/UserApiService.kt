package com.hoa.logindemo.repository.service

import com.hoa.logindemo.model.User
import retrofit2.http.Body
import retrofit2.http.POST

interface UserApiService {

    /**
     * @param params: Map<String, String> contains phone-number and password
     * @return [User]
     */
    @POST("/driver/v1/login")
    suspend fun login(@Body params: Map<String, String>): User
}