package com.hoa.logindemo.ui.login

import androidx.annotation.StringRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hoa.logindemo.R
import com.hoa.logindemo.repository.UserManager
import com.hoa.logindemo.repository.UserRepository
import com.hoa.logindemo.repository.config.ApiResponse
import com.hoa.logindemo.repository.config.BadRequest
import com.hoa.logindemo.repository.config.NoInternet
import dagger.Lazy
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userManager: Lazy<UserManager>,
    private val userRepository: UserRepository
) : ViewModel() {

    private val _uiState = MutableLiveData<LoginUiState>()
    val uiState: LiveData<LoginUiState> = _uiState

    fun login(phoneNumber: String, password: String) {
        viewModelScope.launch {
            _uiState.value = LoginUiState.Loading
            when (val response = userRepository.login(phoneNumber, password)) {
                is ApiResponse.Success -> {
                    userManager.get().storeUserAccessibility(response.data)
                    _uiState.value = LoginUiState.LoginSuccess
                }
                is ApiResponse.Exception -> {
                    val errorStringRes = response.getErrorMessage()
                    _uiState.value = LoginUiState.LoginError(errorStringRes)
                }
            }
        }
    }
}

sealed class LoginUiState {
    data object Loading : LoginUiState()
    data class LoginError(@StringRes val message: Int) : LoginUiState()
    data object LoginSuccess : LoginUiState()
}

@StringRes
private fun ApiResponse.Exception.getErrorMessage(): Int {
    return when (this.throwable) {
        is NoInternet -> R.string.no_internet_connection
        is BadRequest -> R.string.login_failed
        else -> R.string.something_went_wrong
    }
}

