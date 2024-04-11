package com.hoa.logindemo.repository

import com.hoa.logindemo.model.User
import com.hoa.logindemo.repository.config.ApiResponse
import com.hoa.logindemo.repository.config.toApiResponse
import com.hoa.logindemo.repository.service.UserApiService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

interface UserRepository {
    /**
     * @param phoneNumber is the account username
     * @param password
     */
    suspend fun login(phoneNumber: String, password: String): ApiResponse<User>
}

class UserRepositoryImpl(
    private val userApiService: UserApiService,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : UserRepository {

    override suspend fun login(phoneNumber: String, password: String): ApiResponse<User> {
        return withContext(ioDispatcher) {
            try {
                val requestBody = mapOf(
                    "phone_number" to phoneNumber,
                    "password" to password
                )
                val user = userApiService.login(requestBody)
                ApiResponse.Success(user)
            } catch (throwable: Throwable) {
                throwable.toApiResponse()
            }
        }
    }
}