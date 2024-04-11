package com.hoa.logindemo.ui.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.hoa.logindemo.R
import com.hoa.logindemo.ui.theme.Grey
import com.hoa.logindemo.ui.theme.LoginDemoTheme
import com.hoa.logindemo.ui.theme.PrimaryColor

@Composable
fun PasswordInputField(
    password: MutableState<String>,
    modifier: Modifier = Modifier,
    label: String = stringResource(R.string.password),
    onTextChanged: (() -> Unit)? = null,
    onKeyboardActionDone: (() -> Unit)? = null
) {
    var passwordIcon by remember { mutableIntStateOf(R.drawable.ic_hide_password) }
    var visualTransformation by remember { mutableStateOf<VisualTransformation>(PasswordVisualTransformation()) }

    ConstraintLayout(modifier = modifier) {
        val (input, icon) = createRefs()
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(input) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                }
                .semantics { contentDescription = "ipPassword" },
            value = password.value,
            onValueChange = {
                password.value = it
                onTextChanged?.invoke()
            },
            label = {
                Text(color = Grey, text = label)
            },
            visualTransformation = visualTransformation,
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Password
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    if (password.value.isNotEmpty()) {
                        onKeyboardActionDone?.invoke()
                    }
                }
            )
        )
        IconButton(
            modifier = Modifier
                .size(56.dp)
                .constrainAs(icon) {
                    top.linkTo(parent.top, margin = 8.dp)
                    bottom.linkTo(parent.bottom)
                    end.linkTo(input.end)
                },
            onClick = {
                passwordIcon = when (passwordIcon) {
                    R.drawable.ic_hide_password -> R.drawable.ic_show_password
                    else -> R.drawable.ic_hide_password
                }
                visualTransformation = when (passwordIcon) {
                    R.drawable.ic_hide_password -> PasswordVisualTransformation()
                    else -> VisualTransformation.None
                }
            }
        ) {
            Icon(
                tint = PrimaryColor,
                painter = painterResource(passwordIcon),
                contentDescription = "password icon"
            )
        }
    }
}

@Preview
@Composable
private fun PasswordInputFieldPreview() {
    LoginDemoTheme {
        val password = remember { mutableStateOf("afaffadf") }
        PasswordInputField(password = password)
    }
}