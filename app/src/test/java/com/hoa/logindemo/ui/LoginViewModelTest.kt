package com.hoa.logindemo.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.testing.TestLifecycleOwner
import com.hoa.logindemo.R
import com.hoa.logindemo.model.User
import com.hoa.logindemo.repository.UserManager
import com.hoa.logindemo.repository.UserRepository
import com.hoa.logindemo.repository.config.ApiResponse
import com.hoa.logindemo.repository.config.BadRequest
import com.hoa.logindemo.repository.config.NoInternet
import com.hoa.logindemo.repository.config.ServerError
import com.hoa.logindemo.ui.login.LoginUiState
import com.hoa.logindemo.ui.login.LoginViewModel
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

@OptIn(ExperimentalCoroutinesApi::class)
class LoginViewModelTest {
    private val userManager: UserManager = mockk(relaxed = true)
    private val userRepository: UserRepository = mockk(relaxed = true)
    private val dispatcher: TestDispatcher = UnconfinedTestDispatcher()
    private val testLifecycleOwner: TestLifecycleOwner = TestLifecycleOwner()

    private val viewModel = LoginViewModel({ userManager }, userRepository)

    @Rule
    @JvmField
    val rule: TestRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        clearAllMocks()
        Dispatchers.setMain(dispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `perform login successful`() {
        val expectedData = User("user-name", "access-token")
        val expectedUiState = listOf(LoginUiState.Loading, LoginUiState.LoginSuccess)

        val uiState = arrayListOf<LoginUiState>()
        viewModel.uiState.observe(testLifecycleOwner) { uiState.add(it) }
        coEvery { userRepository.login("1232", "password") } returns
            ApiResponse.Success(expectedData)

        viewModel.login("1232", "password")

        assertEquals(expectedUiState, uiState)
        coVerify(exactly = 1) { userRepository.login("1232", "password") }
        verify(exactly = 1) { userManager.storeUserAccessibility(expectedData) }
        confirmVerified(userRepository, userManager)
    }

    @Test
    fun `incorrect phone number or password`() {
        val expectedUiState = listOf(
            LoginUiState.Loading,
            LoginUiState.LoginError(R.string.login_failed)
        )
        val uiState = arrayListOf<LoginUiState>()
        viewModel.uiState.observe(testLifecycleOwner) { uiState.add(it) }

        coEvery { userRepository.login("1232", "password") } returns ApiResponse.Exception(BadRequest)
        viewModel.login("1232", "password")

        assertEquals(expectedUiState, uiState)
        coVerify(exactly = 1) { userRepository.login("1232", "password") }
        coVerify(exactly = 0) { userManager.storeUserAccessibility(any()) }
        confirmVerified(userRepository, userManager)
    }

    @Test
    fun `network exception`() {
        val expectedUiState = listOf(LoginUiState.Loading, LoginUiState.LoginError(R.string.no_internet_connection))
        val uiState = arrayListOf<LoginUiState>()
        viewModel.uiState.observe(testLifecycleOwner) { uiState.add(it) }

        coEvery { userRepository.login("1232", "password") } returns ApiResponse.Exception(NoInternet)
        viewModel.login("1232", "password")

        assertEquals(expectedUiState, uiState)
        coVerify(exactly = 1) { userRepository.login("1232", "password") }
        coVerify(exactly = 0) { userManager.storeUserAccessibility(any()) }
        confirmVerified(userRepository, userManager)
    }

    @Test
    fun `server is down or something wrong with it`() {
        val expectedUiState = listOf(LoginUiState.Loading, LoginUiState.LoginError(R.string.something_went_wrong))
        val uiState = arrayListOf<LoginUiState>()
        viewModel.uiState.observe(testLifecycleOwner) { uiState.add(it) }

        coEvery { userRepository.login("1232", "password") } returns ApiResponse.Exception(ServerError)
        viewModel.login("1232", "password")

        assertEquals(expectedUiState, uiState)
        coVerify(exactly = 1) { userRepository.login("1232", "password") }
        coVerify(exactly = 0) { userManager.storeUserAccessibility(any()) }
        confirmVerified(userRepository)
    }
}