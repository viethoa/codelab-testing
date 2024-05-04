package com.hoa.logindemo.ui

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.hasContentDescription
import androidx.compose.ui.test.hasTextExactly
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.hoa.logindemo.R
import com.hoa.logindemo.di.TestRepositoryViewModule.Companion.MOCK_SERVER_PORT
import com.hoa.logindemo.ui.login.LoginActivity
import com.hoa.logindemo.utils.AssetUtils
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.SocketPolicy
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.net.HttpURLConnection
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltAndroidTest
@OptIn(ExperimentalTestApi::class)
@RunWith(AndroidJUnit4::class)
class LoginScreenTest {

    @Inject
    lateinit var mockServer: MockWebServer

    @get:Rule
    val hiltRule by lazy { HiltAndroidRule(this) }

    @get:Rule
    val composeTestRule by lazy { createAndroidComposeRule<LoginActivity>() }

    @Before
    fun setUp() {
        hiltRule.inject()
        mockServer.start(MOCK_SERVER_PORT)
    }

    @After
    fun teardown() {
        mockServer.shutdown()
    }

    @Test
    fun verifyDefaultUIWhenScreenJustOpen() {
        composeTestRule
            .onNodeWithContentDescription("ipPhoneNumber")
            .assertIsDisplayed()
        composeTestRule
            .onNodeWithContentDescription("ipPassword")
            .assertIsDisplayed()
        composeTestRule
            .onNodeWithContentDescription("txtErrorMessage")
            .assertDoesNotExist()
        composeTestRule
            .onNodeWithContentDescription("btnLoginIn")
            .assertIsDisplayed()
            .assertIsNotEnabled()
        composeTestRule
            .onNodeWithContentDescription("contactSupportBox")
            .assertIsDisplayed()
    }

    @Test
    fun verifyUXForLoginButton() {
        // Not enable as default
        composeTestRule
            .onNodeWithContentDescription("btnLoginIn")
            .assertIsNotEnabled()

        // Enable Login Button after fulfill phone-number and password
        composeTestRule
            .onNodeWithContentDescription("ipPhoneNumber")
            .performTextInput("32455")
        composeTestRule
            .onNodeWithContentDescription("ipPassword")
            .performTextInput("password")
        composeTestRule
            .onNodeWithContentDescription("btnLoginIn")
            .assertIsEnabled()
    }

    @Test
    fun verifyLoginSuccessBehaviour() {
        mockServer.enqueue(
            MockResponse()
                .setResponseCode(HttpURLConnection.HTTP_OK)
                .setBodyDelay(400, TimeUnit.MILLISECONDS)
                .setBody("{\"name\":\"user-name\",\"access_token\":\"access-token\"}")
        )

        composeTestRule
            .onNodeWithContentDescription("ipPhoneNumber")
            .performTextInput("32455")
        composeTestRule
            .onNodeWithContentDescription("ipPassword")
            .performTextInput("my password")
        composeTestRule
            .onNodeWithContentDescription("btnLoginIn")
            .performClick()

        composeTestRule.waitUntilExactlyOneExists(
            hasContentDescription("loadingProcess")
        )
        composeTestRule.waitUntilExactlyOneExists(
            hasContentDescription("mainScreen")
        )
    }

    @Test
    fun verifyUIWhenLoginWithWrongAccount() {
        mockServer.enqueue(
            MockResponse()
                .setResponseCode(HttpURLConnection.HTTP_BAD_REQUEST)
                .setBodyDelay(400, TimeUnit.MILLISECONDS)
                .setBody(AssetUtils.getMockData("login_error.json"))
        )

        composeTestRule
            .onNodeWithContentDescription("ipPhoneNumber")
            .performTextInput("32455")
        composeTestRule
            .onNodeWithContentDescription("ipPassword")
            .performTextInput("my password")
        composeTestRule
            .onNodeWithContentDescription("btnLoginIn")
            .performClick()

        composeTestRule.waitUntilExactlyOneExists(
            hasContentDescription("loadingProcess")
        )
        composeTestRule.waitUntilExactlyOneExists(
            hasTextExactly(composeTestRule.activity.getString(R.string.login_failed))
        )
    }

    @Test
    fun verifyUIWhenLoginWithNoNetworkConnection() {
        val expectedError = composeTestRule.activity.getString(R.string.no_internet_connection)
        mockServer.enqueue(
            MockResponse().setSocketPolicy(SocketPolicy.DISCONNECT_AT_START)
        )

        composeTestRule
            .onNodeWithContentDescription("ipPhoneNumber")
            .performTextInput("32455")
        composeTestRule
            .onNodeWithContentDescription("ipPassword")
            .performTextInput("my password")
        composeTestRule
            .onNodeWithContentDescription("btnLoginIn")
            .performClick()
        composeTestRule
            .onNodeWithContentDescription("txtErrorMessage")
            .assertTextEquals(expectedError)
            .assertIsDisplayed()
    }

    @Test
    fun verifyUIWhenSystemIsNotWorkingRight() {
        val expectedError = composeTestRule.activity.getString(R.string.something_went_wrong)
        mockServer.enqueue(
            MockResponse().setResponseCode(HttpURLConnection.HTTP_INTERNAL_ERROR)
        )

        composeTestRule
            .onNodeWithContentDescription("ipPhoneNumber")
            .performTextInput("32455")
        composeTestRule
            .onNodeWithContentDescription("ipPassword")
            .performTextInput("my password")
        composeTestRule
            .onNodeWithContentDescription("btnLoginIn")
            .performClick()
        composeTestRule
            .onNodeWithContentDescription("txtErrorMessage")
            .assertTextEquals(expectedError)
            .assertIsDisplayed()
    }
}