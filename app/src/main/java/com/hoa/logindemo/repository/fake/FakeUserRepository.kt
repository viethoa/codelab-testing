package com.hoa.logindemo.repository.fake

import com.hoa.logindemo.model.User
import com.hoa.logindemo.repository.UserRepository
import com.hoa.logindemo.repository.config.ApiResponse

class FakeUserRepository : UserRepository {

    override suspend fun login(phoneNumber: String, password: String): ApiResponse<User> = ApiResponse.Success(User())
}