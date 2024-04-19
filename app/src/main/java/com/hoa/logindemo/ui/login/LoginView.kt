package com.hoa.logindemo.ui.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hoa.logindemo.R
import com.hoa.logindemo.ui.component.ErrorMessageText
import com.hoa.logindemo.ui.component.PasswordInputField
import com.hoa.logindemo.ui.component.RoundedCornerButton
import com.hoa.logindemo.ui.component.SingleInputField
import com.hoa.logindemo.ui.theme.LoginDemoTheme
import com.hoa.logindemo.ui.theme.PrimaryColor

@Composable
@OptIn(ExperimentalComposeUiApi::class)
fun LoginView(
    errorMessageRes: MutableState<Int?>,
    onLoginClick: (String, String) -> Unit
) {
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val phoneNumber = rememberSaveable { mutableStateOf("") }
    val password = rememberSaveable { mutableStateOf("") }

    Surface(modifier = Modifier.background(Color.White)) {
        Column {
            Spacer(modifier = Modifier.weight(0.12F))
            Image(
                painter = painterResource(R.drawable.ic_profile),
                contentDescription = "icon profile",
                modifier = Modifier
                    .height(140.dp)
                    .align(Alignment.CenterHorizontally)
            )
            Text(
                text = stringResource(R.string.login_greeting),
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.weight(0.04F))
            SingleInputField(
                phoneNumber.value,
                stringResource(R.string.phone_number),
                Modifier
                    .padding(horizontal = 20.dp, vertical = 6.dp)
                    .semantics { contentDescription = "ipPhoneNumber" },
                onTextChanged = {
                    phoneNumber.value = it
                    errorMessageRes.value = null
                }
            )
            PasswordInputField(
                password.value,
                Modifier.padding(horizontal = 20.dp),
                onTextChanged = {
                    password.value = it
                    errorMessageRes.value = null
                }
            ) {
                onLoginClick(phoneNumber.value, password.value)
                keyboardController?.hide()
                focusManager.clearFocus()
            }
            ErrorMessageText(
                modifier = Modifier
                    .padding(20.dp, 8.dp, 20.dp, 0.dp)
                    .semantics { contentDescription = "txtErrorMessage" },
                description = "txtErrorMessage",
                messageRes = errorMessageRes.value
            )
            RoundedCornerButton(
                modifier = Modifier.padding(20.dp, 26.dp, 20.dp, 0.dp),
                textRes = R.string.login_text,
                enable = phoneNumber.value.isNotEmpty() && password.value.isNotEmpty(),
                description = "btnLoginIn"
            ) {
                onLoginClick(phoneNumber.value, password.value)
                keyboardController?.hide()
                focusManager.clearFocus()
            }
            ContactSupportBox(Modifier.align(Alignment.CenterHorizontally))

            Spacer(modifier = Modifier.weight(0.3F))
        }
    }
}

@Composable
private fun ContactSupportBox(
    modifier: Modifier,
    onContactSupportClicked: () -> Unit = {}
) {
    Row(
        modifier = modifier
            .padding(vertical = 18.dp)
            .semantics { contentDescription = "contactSupportBox" }
    ) {
        Text(
            fontSize = 15.sp,
            text = stringResource(R.string.login_no_account_support),
        )
        Text(
            fontSize = 15.sp,
            color = PrimaryColor,
            text = stringResource(R.string.contact_support),
            modifier = Modifier
                .padding(horizontal = 4.dp)
                .clickable {
                    onContactSupportClicked()
                }
        )
    }
}

@Preview
@Composable
private fun PreviewLoginView() {
    val errorMessage = remember { mutableStateOf<Int?>(null) }
    LoginDemoTheme {
        LoginView(errorMessage) { _, _ -> }
    }
}