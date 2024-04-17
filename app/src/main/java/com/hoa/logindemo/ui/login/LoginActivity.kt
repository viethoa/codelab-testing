package com.hoa.logindemo.ui.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.hoa.logindemo.repository.UserManagerImpl
import com.hoa.logindemo.repository.fake.FakeUserRepository
import com.hoa.logindemo.ui.component.CircularLoadingProgress
import com.hoa.logindemo.ui.main.MainActivity
import com.hoa.logindemo.ui.theme.LoginDemoTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LoginScreen()
        }
    }
}

private fun navigateToHomeScreen(context: Context) {
    val intent = Intent(context, MainActivity::class.java)
    context.startActivity(intent)
}

@Composable
private fun LoginScreen(viewModel: LoginViewModel = viewModel()) {
    LoginDemoTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            val errorMessage = rememberSaveable { mutableStateOf<Int?>(null) }
            val uiState by viewModel.uiState.observeAsState()
            LoginView(errorMessage) { phoneNumber, password ->
                viewModel.login(phoneNumber, password)
            }

            when (uiState) {
                is LoginUiState.Loading -> CircularLoadingProgress()
                is LoginUiState.LoginSuccess -> navigateToHomeScreen(LocalContext.current)
                is LoginUiState.LoginError -> {
                    errorMessage.value = (uiState as LoginUiState.LoginError).message
                }
                else -> {
                    // No-operation
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun LoginScreenPreview() {
    LoginDemoTheme {
        LoginScreen(LoginViewModel({ UserManagerImpl() }, FakeUserRepository()))
    }
}