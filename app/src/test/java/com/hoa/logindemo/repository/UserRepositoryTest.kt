package com.hoa.logindemo.repository

import com.hoa.logindemo.BaseRepositoryTest
import com.hoa.logindemo.model.User
import com.hoa.logindemo.repository.config.ApiResponse
import com.hoa.logindemo.repository.config.BadRequest
import com.hoa.logindemo.repository.config.NoInternet
import com.hoa.logindemo.repository.config.ServerError
import com.hoa.logindemo.utils.ResourceUtils
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest
import okhttp3.mockwebserver.SocketPolicy
import org.junit.Assert.assertEquals
import org.junit.Test
import java.net.HttpURLConnection

class UserRepositoryTest : BaseRepositoryTest() {

    private val repository = UserRepositoryImpl(userApiService)

    @Test
    fun `request login api successful`() = runTest {
        val mockResponse = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody(ResourceUtils.getMockData("login_success.json"))
        mockServer.enqueue(mockResponse)

        val expectedRequest = "{\"phone_number\":\"phone\",\"password\":\"password\"}"
        val expectedData = User("viet hoa", "my access token")
        val expectedResponse = ApiResponse.Success(expectedData)
        val response = repository.login("phone", "password")

        val recordedRequest = mockServer.takeRequest()
        assertEquals("/driver/v1/login", recordedRequest.path)
        assertEquals(expectedRequest, recordedRequest.body.readUtf8())
        assertEquals(expectedResponse, response)
    }

    @Test
    fun `request login api with wrong account info`() = runTest {
        val mockResponse = MockResponse().setResponseCode(HttpURLConnection.HTTP_BAD_REQUEST)
        mockServer.enqueue(mockResponse)

        val expectedResponse = ApiResponse.Exception(BadRequest)
        val response = repository.login("phone", "password")
        val request: RecordedRequest = mockServer.takeRequest()

        assertEquals("/driver/v1/login", request.path)
        assertEquals(response, expectedResponse)
    }

    @Test
    fun `request login api when no internet connection`() = runTest {
        val mockResponse = MockResponse().setSocketPolicy(SocketPolicy.DISCONNECT_AT_START)
        mockServer.enqueue(mockResponse)

        val expectedResponse = ApiResponse.Exception(NoInternet)
        val response = repository.login("phone", "password")

        assertEquals(response, expectedResponse)
    }

    @Test
    fun `request login api when system not working right`() = runTest {
        val mockResponse = MockResponse().setResponseCode(HttpURLConnection.HTTP_GATEWAY_TIMEOUT)
        mockServer.enqueue(mockResponse)

        val expectedResponse = ApiResponse.Exception(ServerError)
        val response = repository.login("phone", "password")
        val request: RecordedRequest = mockServer.takeRequest()

        assertEquals("/driver/v1/login", request.path)
        assertEquals(response, expectedResponse)
    }
}